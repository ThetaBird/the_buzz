package edu.lehigh.cse216.group4.admin;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.ArrayList;

public class Database {
    /**
     * The connection to the database.  When there is no connection, it should
     * be null.  Otherwise, there is a valid open connection
     */
    private Connection mConnection;

    /**
     * A prepared statement for getting all data in the database
     */
    private PreparedStatement mSelectAll;

    /**
     * A prepared statement for getting one row from the database
     */
    private PreparedStatement mSelectOne;

    /**
     * A prepared statement for getting the number of likes in one row (for one idea)
     * in the database 
     */
    private PreparedStatement mSelectLikes;

    /**
     * A prepared statement for getting the number of dislikes in one row (for one idea)
     * in the database 
     */
    private PreparedStatement mSelectDislikes;

    /**
     * A prepared statement for getting the number of comments in one row (for one idea)
     * in the database
     */
    private PreparedStatement mSelectComments;

    /**
     * A prepared statement for deleting a row from the database
     */
    private PreparedStatement mDeleteOne;

    /**
     * A prepared statement for inserting into the database
     */
    private PreparedStatement mInsertOne;

    /**
     * A prepared statement for updating a single row in the database
     */
    private PreparedStatement mUpdateOne;

    /**
     * A prepared statement for creating the table in our database
     */
    private PreparedStatement mCreateTable;

    /**
     * A prepared statement for dropping the table in our database
     */
    private PreparedStatement mDropTable;

    /**
     * RowData is like a struct in C: we use it to hold data, and we allow 
     * direct access to its fields.  In the context of this Database, RowData 
     * represents the data we'd see in a row.
     * 
     * We make RowData a static class of Database because we don't really want
     * to encourage users to think of RowData as being anything other than an
     * abstract representation of a row of the database.  RowData and the 
     * Database are tightly coupled: if one changes, the other should too.
     */
    //idea row data
    public static class RowData {
        /**
         * The ID of this row of the database
         */
        int mId;

        /**
         * The User ID of this row of the database
         */
        int mUid;

        /**
         * The subject stored in this row
         */
        String mSubject;

        /**
         * The message stored in this row
         */
        String mMessage;

        /**
         * The number of comments stored in this row
         */
        int mComments;

        /**
         * The number of likes stored in this row
         */
        int mLikes;

        /**
         * The number of dislikes stored in this row
         */
        int mDislikes;

        /**
         * Construct a RowData object by providing values for its fields
         */
        public RowData(int id, int user_id, String subject, String content, int comments, int likes, int dislikes) {
            this.mId = id;
            this.mUid = user_id;
            this.mSubject = subject;
            this.mMessage = content;
            this.mComments = comments;
            this.mLikes = likes;
            this.mDislikes = dislikes;
        }
    }

    /**
     * The Database constructor is private: we only create Database objects 
     * through the getDatabase() method.
     */
    private Database() {
    }

    /**
     * Get a fully-configured connection to the database
     * 
     * @param ip   The IP address of the database server
     * @param port The port on the database server to which connection requests
     *             should be sent
     * @param user The user ID to use when connecting
     * @param pass The password to use when connecting
     * 
     * @return A Database object, or null if we cannot connect properly
     */
    static Database getDatabase(String ip, String port, String user, String pass) {
        // Create an un-configured Database object
        Database db = new Database();

        // Give the Database object a connection, fail if we cannot get one
        try {
            Connection conn = DriverManager.getConnection("jdbc:postgresql://" + ip + ":" + port + "/", user, pass);
            if (conn == null) {
                System.err.println("Error: DriverManager.getConnection() returned a null object");
                return null;
            }
            db.mConnection = conn;
        } catch (SQLException e) {
            System.err.println("Error: DriverManager.getConnection() threw a SQLException");
            e.printStackTrace();
            return null;
        }

        // Attempt to create all of our prepared statements.  If any of these 
        // fail, the whole getDatabase() call should fail
        try {
            // NB: we can easily get ourselves in trouble here by typing the
            //     SQL incorrectly.  We really should have things like "tblData"
            //     as constants, and then build the strings for the statements
            //     from those constants.
            
            db.mDropTable = db.mConnection.prepareStatement("DROP TABLE users");
            db.dropTable();     //execute the prepared statement
            db.mDropTable = db.mConnection.prepareStatement("DROP TABLE ideas");
            db.dropTable();     //execute the prepared statement
            db.mDropTable = db.mConnection.prepareStatement("DROP TABLE likes"); 
            db.dropTable();     //execute the prepared statement

            // Note: no "IF NOT EXISTS" or "IF EXISTS" checks on table 
            // creation/deletion, so multiple executions will cause an exception
            db.mCreateTable = db.mConnection.prepareStatement(
                "CREATE TABLE IF NOT EXISTS users (id SERIAL PRIMARY KEY,"
                + "note VARCHAR(128)," //notes are like an "about me" section
                + "email VARCHAR,"
                + "name VARCHAR(50) NOT NULL,"
                + "avatar VARCHAR NOT NULL,"
                + "password_hash VARCHAR(64) NOT NULL,"
                + "company_role SMALLINT NOT NULL)");
            db.createTable();   //execute the prepared statement

            db.mCreateTable = db.mConnection.prepareStatement(
                "CREATE TABLE IF NOT EXISTS ideas (id SERIAL PRIMARY KEY,"
                + "user_id INTEGER NOT NULL,"
                + "time_stamp BIGINT NOT NULL,"
                + "subject VARCHAR(64) NOT NULL,"
                + "content VARCHAR(500) NOT NULL,"
                + "attachment VARCHAR(50) NOT NULL,"
                + "allowed_roles INTEGER[] NOT NULL,"   //roles in the company that are allowed to see the idea
                + "comment_ids INTEGER[]," //a table of comment id's. comments are treated the same as an idea
                + "FOREIGN KEY(user_id) REFERENCES users(id))");
            db.createTable();   //execute the prepared statement

            /*db.mCreateTable = db.mConnection.prepareStatement(
                "CREATE TABLE IF NOT EXISTS likes (post_user_id SERIAL PRIMARY KEY,"
                + "post_num INTEGER NOT NULL,"
                + "likes_users_id INTEGER[] NOT NULL,"
                + "disliked_users_id INTEGER[] NOT NULL");*/

            //redid likes and added dislikes, which is more or less identical, the old version is above ^
            db.mCreateTable = db.mConnection.prepareStatement( 
                "CREATE TABLE IF NOT EXISTS likes ("
                + "idea_user_id INTEGER NOT NULL,"
                + "idea_id SERIAL NOT NULL,"
                + "liked_users_id INTEGER[],"
                + "PRIMARY KEY (idea_user_id, idea_id),"
                + "FOREIGN KEY(idea_user_id) REFERENCES ideas(user_id),"
                + "FOREIGN KEY(idea_id) REFERENCES ideas(id))"
            );
            db.createTable();   //execute the prepared statement

            db.mCreateTable = db.mConnection.prepareStatement(
                "CREATE TABLE IF NOT EXISTS dislikes ("
                + "idea_user_id INTEGER NOT NULL,"
                + "idea_id SERIAL NOT NULL,"
                + "disliked_users_id INTEGER[],"
                + "PRIMARY KEY (idea_user_id, idea_id),"
                + "FOREIGN KEY(idea_user_id) REFERENCES ideas(user_id),"
                + "FOREIGN KEY(idea_id) REFERENCES ideas(id))"
            );
            db.createTable();   //execute the prepared statement

            // Standard CRUD operations
            /*db.mDeleteOne = db.mConnection.prepareStatement("DELETE FROM tblData WHERE id = ?");
            db.mInsertOne = db.mConnection.prepareStatement("INSERT INTO tblData VALUES (default, ?, ?)");
            db.mSelectAll = db.mConnection.prepareStatement("SELECT id, subject FROM tblData");
            db.mSelectOne = db.mConnection.prepareStatement("SELECT * from tblData WHERE id=?");
            db.mUpdateOne = db.mConnection.prepareStatement("UPDATE tblData SET message = ? WHERE id = ?"); */
        } catch (SQLException e) {
            System.err.println("Error creating prepared statement");
            e.printStackTrace();
            db.disconnect();
            return null;
        }
        return db;
    }

    /**
     * Close the current connection to the database, if one exists.
     * 
     * NB: The connection will always be null after this call, even if an 
     *     error occurred during the closing operation.
     * 
     * @return True if the connection was cleanly closed, false otherwise
     */
    boolean disconnect() {
        if (mConnection == null) {
            System.err.println("Unable to close connection: Connection was null");
            return false;
        }
        try {
            mConnection.close();
        } catch (SQLException e) {
            System.err.println("Error: Connection.close() threw a SQLException");
            e.printStackTrace();
            mConnection = null;
            return false;
        }
        mConnection = null;
        return true;
    }

    /**
     * Insert a row into the database
     * 
     * @param subject The subject for this new row
     * @param message The message body for this new row
     * 
     * @return The number of rows that were inserted
     */
    int insertRow(String subject, String content) {
        int count = 0;
        try {
            if(subject == null || content == null){
                return count;
            }
            else{
                mInsertOne.setString(1, subject);
                mInsertOne.setString(2, content);
                count += mInsertOne.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return count;
    }

    /**
     * Query the database for a list of all subjects, IDs, original owners,
     * and current reaction and comment information
     * 
     * @return All rows, as an ArrayList
     */
    ArrayList<RowData> selectAll() {
        ArrayList<RowData> res = new ArrayList<RowData>();
        try {
            ResultSet rs = mSelectAll.executeQuery();
            int currId;
            ResultSet c, r, w;
            Long time;
            String parseTime, sub, cont;
            while (rs.next()) {
                currId = rs.getInt("id");
                time = rs.getLong("time_stamp");
                parseTime = String.valueOf(time);
                char[] year = new char[4];
                for(int k = 0; k < 4; k++){
                    year[k] = parseTime.charAt(k);
                    //if the third number is not 2
                    //so the year is not 2020, 2021, 2022
                    if(k == 2 && year[k] != 2){
                        //delete it
                        deleteRow(currId);
                    }
                }
                sub = rs.getString("subject");
                cont = rs.getString("content");
                //checking if the subject or content are null or incomplete uploads
                if(sub == null || cont == null){
                    deleteRow(currId);
                }
                //select the number of comments
                c = mSelectComments.executeQuery("SELECT COUNT(comment_ids) AS comments FROM ideas WHERE id = "+currId+" ");
                //select the number of likes
                r = mSelectLikes.executeQuery("SELECT COUNT(liked_users_id) AS Likes FROM likes WHERE idea_id = "+currId+" ");
                //select the number of dislikes
                w = mSelectDislikes.executeQuery("SELECT COUNT(disliked_users_id) AS Dislikes FROM dislikes WHERE idea_id = "+currId+" ");
                res.add(new RowData(rs.getInt("id"), rs.getInt("user_id"), rs.getString("subject"), null, c.getInt("comments"), r.getInt("Likes"), w.getInt("Dislikes")));
            }
            rs.close();
            return res;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Get all data (original owner, subject, message) for a specific idea, by ID
     * 
     * @param id The id of the row being requested
     * 
     * @return The data for the requested row, or null if the ID was invalid
     */
    RowData selectOne(int id) {
        RowData res = null;
        try {
            mSelectOne.setInt(1, id);
            ResultSet rs = mSelectOne.executeQuery();
            ResultSet c, r, w;
            if (rs.next()) {
                int currId = rs.getInt("id");
                c = mSelectComments.executeQuery("SELECT COUNT(comment_ids) AS comments FROM ideas WHERE id = "+currId+" ");
                r = mSelectLikes.executeQuery("SELECT COUNT(liked_users_id) AS Likes FROM likes WHERE idea_id = "+currId+" ");
                w = mSelectDislikes.executeQuery("SELECT COUNT(disliked_users_id) AS Dislikes FROM dislikes WHERE idea_id = "+currId+" ");
                res = new RowData(rs.getInt("id"), rs.getInt("user_id"), rs.getString("subject"), rs.getString("content"), c.getInt("comments"), r.getInt("Likes"), w.getInt("Dislikes"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return res;
    }

    /**
     * Delete a row by ID
     * 
     * @param id The id of the row to delete
     * 
     * @return The number of rows that were deleted.  -1 indicates an error.
     */
    int deleteRow(int id) {
        int res = -1;
        try {
            mDeleteOne.setInt(1, id);
            res = mDeleteOne.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return res;
    }

    /**
     * Update the message for a row in the database
     * 
     * @param id The id of the row to update
     * @param message The new message contents
     * 
     * @return The number of rows that were updated.  -1 indicates an error.
     */
    int updateOne(int id, String message) {
        int res = -1;
        try {
            if(message == null){
                return res;
            }
            else{
                mUpdateOne.setString(1, message);
                mUpdateOne.setInt(2, id);
                res = mUpdateOne.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return res;
    }

    /**
     * Create tblData.  If it already exists, this will print an error
     */
    void createTable() {
        try {
            mCreateTable.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Remove tblData from the database.  If it does not exist, this will print
     * an error.
     */
    void dropTable() {
        try {
            mDropTable.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}