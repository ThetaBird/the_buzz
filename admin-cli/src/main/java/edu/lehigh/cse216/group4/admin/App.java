package edu.lehigh.cse216.group4.admin;

import java.io.InputStreamReader;
import java.io.IOException;

import java.util.ArrayList;

import java.util.Scanner;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


/**
 * App is our basic admin app.  For now, all it does is connect to the database
 * and then disconnect
 */
public class App {
    /**
     * The main routine reads arguments from the environment and then uses those
     * arguments to connect to the database.
     */
    public static void main(String[] argv) throws IOException, GeneralSecurityException {

        String db_url = System.getenv("DATABASE_URL");
        String port = System.getenv("POSTGRES_PORT");
        String user = System.getenv("POSTGRES_USER");
        String pass = System.getenv("POSTGRES_PASS");

        // Get a fully-configured connection to the database, or exit
        // immediately
        Database db = Database.getDatabase(db_url, port, user, pass);
        if (db == null)
            return;
            //Retrieve data for logged in user

        // get the Postgres configuration from the environment

        // Start our basic command-line interpreter:

        menu();
        while (true) {
            // Get the user's request, and do it
            //
            // NB: for better testability, each action should be a separate
            // function call
            Scanner scan = new Scanner(System.in);
             char action = scan.next().charAt(0);
            if(action == '?'){
                menu();
            }
            
            else if (action == 'Q') {// ad more else if statement for "likes"
                break;
            } else if (action == 'C') { // create buzzusers table
                db.createTable();
            } else if (action == '1') { // query for a specific user in buzzusers
                String username = scan.next();
                Database.RowData row = new db.RowData();;
                if (res != null) {
                    ;
                    System.out.println("  Idea: [" + res.note + "] " + " email: [" + res.email + "] " + " name: [" + res.name + "] "
                            + " avatar: [" + res.avatar + "] " + " password_hash: [" + res.password_hash + "] " + " company_role: [" + res.company_role + "] /n");
                
                }
            } else if (action == '2') { // query for a specific idea
                int id = getInt(in, "\nEnter the id");
                Database.RowData res = new db.RowData();
                if (res != null) {
                   
                    System.out.println(
                            "  Id: [" + res.userId + "] " + " username: [" + res.name + "] " + " comment [" + res.comment + "] ");
                     
                }
            } else if (action == '3') { // query for a specific like
                int idea = getInt(in, "Enter the idea of the like");
                String username = getString(in, "Enter the username associated with the like");
                Database.RowData res = new db.RowData();
                if (res != null) {
                    System.out.println(" idea: [" + res.idea + "] " + " commetn: [" + res.comment + "] \n");
                }
            
            } else if (action == 'd') { 
                String sub = scan.next();
                int res = db.deleteRow();
                System.out.println("  " + res + " rows deleted\n");
            } else if (action == 'o') { // delete row in idea
                int id = scan.nextInt();
                if (id == -1)
                    continue;
                int res = db.deleteRow(id);
                if (res == -1)
                    continue;
                System.out.println("  " + res + " rows deleted\n");
            } else if (action == 'i') { // delete row in likes
                int idea = scan.nextInt();
                String username = scan.next();
                if (idea == -1) {
                    System.out.println("The indicated idea id not found, please check and try again\n");
                    continue;
                }
                int res = db.deleteRow(idea);
                System.out.println("  " + res + " rows deleted\n");

            } else if (action == 'u') { // delete row in dislikes
                int idea = scan.nextInt();
                String username = scan.next();
                if (idea == -1) {
                    System.out.println("The indicated idea was not found, please check and try again\n");
                }
                int res = db.deleteRow( username);
                System.out.println(" " + res + " rows deleted\n");

            } else if (action == 'y') { // delete row in comments
                int id = scan.nextInt();
                if (id == -1) {
                    System.out.println("The indicated comment was not found, please check and try again\n");
                }
                int res = db.deleteRow(id);
                System.out.println(" " + res + " rows deleted\n");
            } else if (action == 'l') { // insert new row in buzzusers
                String sub = scan.next();
                String email = scan.next();
                String name = scan.next();
                String sessionKey =  scan.next();
                if (sub.equals("") || email.equals("") || name.equals("") 
                        || sessionKey.equals("")) {
                    System.out.println(
                            "missing infomation \n");
                    continue;
                }
                int res = db.insertRow(sub, email, name, sessionKey);
                System.out.println(res + " rows added\n");

            } else if (action == 'k') { // insert new row in idea
                int id =  scan.nextInt();
                String username =  scan.next();
                String comment =  scan.next();
                int res = db.insertRow(id, username, comment);
            } else if (action == 'j') { // insert new row in likes
                int idea = 0;
                idea =  scan.nextInt();
                String username =  scan.next();
                if (idea == 0 || username.equals("")) {
                    System.out.println("input userName\n");
                    continue;
                }
                // CHECK THAT THE USER HASN'T ALREADY DISLIKED THE POST.
                Database.RowData res = db.select(ideaId);
                if (res == null) { // if the user hasn't disliked the post, proceed to insert row normally
                    int result = db.insertRow(idea);
                    System.out.println("added\n");
                } else { // then user disliked the post first, so have to remove the dislike and add the
                         // like after
                    int resultDislike = db.deleteRow(idea);
                    int result = db.insertRow(idea);
          
            
                }

        db.disconnect();}

    public static void menu() {
        System.out.println(" Menu \n" );
        System.out.println( "Create tables");
        System.out.println( "Create users  Drop users");
        System.out.println( "Create idea  Drop idea");
        System.out.println( "Create like  Drop like");
        System.out.println("Create dislike  Drop dislike");
        System.out.println("Create comments   Drop comments");

        System.out.println("find information ");
        System.out.println("find user");
        System.out.println("find idea");
        System.out.println("find like");
        System.out.println( "find dislike");
        System.out.println("find comments ");

        System.out.println(  "Delete a row in a specfic table");
        System.out.println(  "Delete buzzuser");
        System.out.println(  "Delete a row in idea");
        System.out.println(  "Delete a row in likes");
        System.out.println(  "Delete a row in dislikes");
        System.out.println(  "Delete a row in comments");
        System.out.println( "Update table");
        System.out.println("Update User");
        System.out.println("Update a row in idea");
        System.out.println("Update a row in commments");

        System.out.println("Quit");
    }
}