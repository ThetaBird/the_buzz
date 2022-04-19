package edu.lehigh.cse216.group4.admin;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;

import java.util.ArrayList;

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
   public static void main(String[] argv) {
        // get the Postgres configuration from the environment
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
    }
}