package edu.lehigh.cse216.group4.backend;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Array;

import java.util.ArrayList;
import java.util.Arrays;

//import com.heroku.api.User;

import java.net.URI;
import java.net.URISyntaxException;
import java.time.*;

public class Database {
    /**
     * The connection to the database.  When there is no connection, it should
     * be null.  Otherwise, there is a valid open connection
     */
    private Connection mConnection;
    private static Clock clock;

    private PreparedStatement mCreateUserTable;
    private PreparedStatement mDropUserTable;
    private PreparedStatement mCreateIdeaTable;
    private PreparedStatement mDropIdeaTable;
    private PreparedStatement mCreateReactionTable;
    private PreparedStatement mDropReactionTable;

    private PreparedStatement mDeleteIdea;
    private PreparedStatement mInsertIdea;
    private PreparedStatement mSelectIdea;
    private PreparedStatement mSelectAllIdeas;
    private PreparedStatement mUpdateIdea;

    private PreparedStatement mInsertReactions;
    private PreparedStatement mSelectReactions;
    private PreparedStatement mUpdateReactions;

    private PreparedStatement mInsertUser;
    private PreparedStatement mSelectUser;
    private PreparedStatement mUpdateUser;

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
    public static class IdeaRowData{
        int ideaId;
        int userId;
        long timestamp;
        String subject;
        String content;
        String attachment;
        //Integer[] allowedRoles;
        public IdeaRowData(int ideaId, int userId, long timestamp, String subject, String content, String attachment, Array allowedRoles){
            this.ideaId = ideaId;
            this.userId = userId;
            this.timestamp = timestamp;
            this.subject = subject;
            this.content = content;
            this.attachment = attachment;
            //try{this.allowedRoles = (Integer[])allowedRoles.getArray();
           // }catch(SQLException e){this.allowedRoles = null;}
            //System.out.println(Arrays.toString(this.allowedRoles));
        }
        public IdeaRowData(IdeaRowData idea){
            this.ideaId = idea.ideaId;
            this.userId = idea.userId;
            this.timestamp = idea.timestamp;
            this.subject = idea.subject;
            this.content = idea.content;
            this.attachment = idea.attachment;
            //this.allowedRoles = idea.allowedRoles;
        }
    }
    public static class UserRowData{
        int userId;
        String avatar;
        String name;
        String passwordHash;
        Integer companyRole;
        public UserRowData(int userId, String avatar, String name, String passwordHash, Integer companyRole){
            this.userId = userId;
            this.avatar = avatar;
            this.name = name;
            this.passwordHash = passwordHash;
            this.companyRole = companyRole;
        }
        public UserRowData(UserRowData user){
            this.userId = user.userId;
            this.avatar = user.avatar;
            this.name = user.name;
            this.passwordHash = user.passwordHash;
            this.companyRole = user.companyRole;
        }
    }
    public static class ReactionRowData{
        int ideaId;
        Integer[] likes;
        Integer[] dislikes;
        public ReactionRowData(int ideaId, Array likes, Array dislikes){
            this.ideaId = ideaId;

            try{this.likes = (Integer[])likes.getArray();
            }catch(SQLException e){this.likes = null;}

            try{this.dislikes = (Integer[])dislikes.getArray();
            }catch(SQLException e){this.dislikes = null;}
            
        }
        public ReactionRowData(ReactionRowData reaction){
            this.ideaId = reaction.ideaId;
            this.likes = reaction.likes;
            this.dislikes = reaction.dislikes;
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
    static Database getDatabase(String db_url) {
        // Create an un-configured Database object
        Database db = new Database();
        // create a Zone Id for Europe/Paris
        ZoneId zoneId = ZoneId.of("-05:00");
        // create Clock Object by passing zoneID
        clock = Clock.system(zoneId);
        // Give the Database object a connection, fail if we cannot get one
        try {
            Class.forName("org.postgresql.Driver");
            URI dbUri = new URI(db_url);
            String username = dbUri.getUserInfo().split(":")[0];
            String password = dbUri.getUserInfo().split(":")[1];
            String dbUrl = "jdbc:postgresql://" + dbUri.getHost() + ':' + dbUri.getPort() + dbUri.getPath();
            Connection conn = DriverManager.getConnection(dbUrl, username, password);
            if (conn == null) {
                System.err.println("Error: DriverManager.getConnection() returned a null object");
                return null;
            }
            db.mConnection = conn;
        } catch (SQLException e) {
            System.err.println("Error: DriverManager.getConnection() threw a SQLException");
            e.printStackTrace();
            return null;
        } catch (ClassNotFoundException cnfe) {
            System.out.println("Unable to find postgresql driver");
            return null;
        } catch (URISyntaxException s) {
            System.out.println("URI Syntax Error");
            return null;
        }

        // Attempt to create all of our prepared statements.  If any of these 
        // fail, the whole getDatabase() call should fail
        try {
            // NB: we can easily get ourselves in trouble here by typing the
            //     SQL incorrectly.  We really should have things like "tblData"
            //     as constants, and then build the strings for the statements
            //     from those constants.

            // Note: no "IF NOT EXISTS" or "IF EXISTS" checks on table 
            // creation/deletion, so multiple executions will cause an exception
            
            db.mCreateUserTable = db.mConnection.prepareStatement(
                "CREATE TABLE IF NOT EXISTS users (" + 
                    "id SERIAL PRIMARY KEY, " + //id of user (TechDebt: turn into sha-1 id)
                    "avatar VARCHAR, " + //file path to avatar of user (TechDebt: actually implement this)
                    "name VARCHAR(50) NOT NULL, " + //Displayed name of user
                    "passwordHash VARCHAR(64) NOT NULL, " + //encrypted hash string of google password (TechDebt: actually implement this)
                    "companyRole SMALLINT) "); //position of user in company hierarchy
            db.mDropUserTable = db.mConnection.prepareStatement("DROP TABLE users");

            db.mCreateIdeaTable = db.mConnection.prepareStatement(
                "CREATE TABLE IF NOT EXISTS ideas (" + 
                    "id SERIAL PRIMARY KEY, " + //id of idea (TechDebt: turn into sha-1 id)
                    "userId INTEGER NOT NULL, " + //id of user who posted the id
                    "timestamp BIGINT NOT NULL, " + //time of creation in milliseconds
                    "subject VARCHAR(64) NOT NULL, " + //subject of idea
                    "content VARCHAR(500) NOT NULL, " + //more descriptive content of idea
                    "attachment VARCHAR(50)," + //file path to any attachment (image, spreadsheet, etc.) for the idea. (TechDebt: actually implement this)
                    "allowedRoles SMALLINT[] ) "); //array of company roles who can view this message
            db.mDropIdeaTable = db.mConnection.prepareStatement("DROP TABLE ideas");

            db.mCreateReactionTable = db.mConnection.prepareStatement(
                "CREATE TABLE IF NOT EXISTS reactions (" + 
                    "ideaId INTEGER NOT NULL, " + //id of idea for which reactions are reacted
                    "likes INTEGER[], " + //ids of users who liked
                    "dislikes INTEGER[]) "); //ids of users who disliked
            db.mDropReactionTable = db.mConnection.prepareStatement("DROP TABLE reactions");

            

            // Standard CRUD operations
            db.mDeleteIdea = db.mConnection.prepareStatement("DELETE FROM ideas WHERE id = ?");
            db.mInsertIdea = db.mConnection.prepareStatement("INSERT INTO ideas VALUES (default, ?, ?, ?, ?, ?, ?)");
            db.mSelectIdea = db.mConnection.prepareStatement("SELECT * from ideas WHERE id = ?");
            db.mSelectAllIdeas = db.mConnection.prepareStatement("SELECT * FROM ideas"); //TechDebt: Implement LIMIT and limit offset for lazy message loading
            db.mUpdateIdea = db.mConnection.prepareStatement("UPDATE ideas SET subject = ?, content = ?, attachment = ?, allowedRoles = ? WHERE id = ?");

            db.mInsertReactions =  db.mConnection.prepareStatement("INSERT INTO reactions VALUES (?, ?, ?)");
            db.mSelectReactions = db.mConnection.prepareStatement("SELECT * from reactions WHERE idea = ?");
            db.mUpdateReactions = db.mConnection.prepareStatement("UPDATE reactions SET likes = ?, dislikes = ? WHERE id = ?");

            db.mInsertUser = db.mConnection.prepareStatement("INSERT INTO users VALUES (default, ?, ?, ?, ?)");
            db.mSelectUser = db.mConnection.prepareStatement("SELECT * from users WHERE id = ?");
            db.mUpdateUser = db.mConnection.prepareStatement("UPDATE users SET avatar = ?, name = ?, companyRole = ? WHERE id = ?");

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

    /*
        FUNCTIONS FOR INSERTING ROWS (IDEA, REACTION, USER)
    */
    int insertIdea(int userId, String subject, String content, String attachment, Integer[] allowedRoles){
        int count = 0;
        try {
            long millis = clock.millis(); //timestamp

            mInsertIdea.setInt(1, userId);
            mInsertIdea.setLong(2, millis);
            mInsertIdea.setString(3, subject);
            mInsertIdea.setString(4, content);
            mInsertIdea.setString(5, attachment);
            Array roles = mConnection.createArrayOf("INTEGER",allowedRoles);
            mInsertIdea.setArray(6, roles);
            count += mInsertIdea.executeUpdate();
        } catch (SQLException e){e.printStackTrace();}

        return count;
    }
    int insertReaction(int ideaId, Integer[] likes, Integer[] dislikes){
        int count = 0;
        try {
            mInsertReactions.setInt(1,ideaId);

            Array likesArr = mConnection.createArrayOf("INTEGER",likes);
            Array dislikesArr = mConnection.createArrayOf("INTEGER",dislikes);
            mInsertIdea.setArray(2, likesArr);
            mInsertIdea.setArray(3, dislikesArr);
            count += mInsertReactions.executeUpdate();
        } catch (SQLException e) {e.printStackTrace();}

        return count;
    }
    int insertUser(String avatar, String name, String passwordHash, int companyRole){
        int count = 0;
        try {
            mInsertUser.setString(1, avatar);
            mInsertUser.setString(2, name);
            mInsertUser.setString(3, passwordHash);
            mInsertUser.setInt(4, companyRole);
            count += mInsertUser.executeUpdate();
        } catch (SQLException e){e.printStackTrace();}

        return count;
    }

    /*
        FUNCTIONS FOR SELECTING ROWS (IDEA, REACTION, USER)
    */
    ArrayList<IdeaRowData> selectAllIdeas(){
        ArrayList<IdeaRowData> res = new ArrayList<IdeaRowData>();
        try {
            ResultSet rs = mSelectAllIdeas.executeQuery();
            while (rs.next()) {
                
                IdeaRowData idea = new IdeaRowData(
                    rs.getInt("id"), 
                    rs.getInt("userId"),
                    rs.getLong("timestamp"), 
                    rs.getString("subject"), 
                    rs.getString("content"), 
                    rs.getString("attachment"),
                    rs.getArray("allowedRoles"));

                res.add(idea);
            }
            rs.close();
            return res;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    IdeaRowData selectIdea(int ideaId){
        IdeaRowData res = null;
        try {
            mSelectIdea.setInt(1, ideaId);
            ResultSet rs = mSelectIdea.executeQuery();
            if (rs.next()) {
                res = new IdeaRowData(
                    rs.getInt("id"), 
                    rs.getInt("userId"),
                    rs.getLong("timestamp"), 
                    rs.getString("subject"), 
                    rs.getString("content"), 
                    rs.getString("attachment"),
                    rs.getArray("allowedRoles"));
            }
        } catch (SQLException e){e.printStackTrace();}
        return res;
    }

    ReactionRowData selectReaction(int ideaId){
        ReactionRowData res = null;
        try {
            mSelectIdea.setInt(1, ideaId);
            ResultSet rs = mSelectReactions.executeQuery();
            if (rs.next()) {
                res = new ReactionRowData(
                    rs.getInt("idea"), 
                    rs.getArray("likes"),
                    rs.getArray("dislikes"));
            }
        } catch (SQLException e){e.printStackTrace();}
        return res;
    }

    UserRowData selectUser(int userId){
        UserRowData res = null;
        try {
            mSelectUser.setInt(1, userId);
            ResultSet rs = mSelectUser.executeQuery();
            if (rs.next()) {
                res = new UserRowData(
                    rs.getInt("id"), 
                    rs.getString("avatar"),
                    rs.getString("name"),
                    rs.getString("passwordHash"),
                    rs.getInt("companyRole"));
            }
        } catch (SQLException e){e.printStackTrace();}
        return res;
    }

    /*
        FUNCTION FOR DELETING ROWS (IDEA)
        ("Deleting" reactions is be handled by the update functions)
    */
    int deleteIdea(int ideaId){
        int res = -1;
        try{
            mDeleteIdea.setInt(1, ideaId);
            res = mDeleteIdea.executeUpdate();
        }catch(SQLException e){e.printStackTrace();}
        return res;
    }

    /*
        FUNCTION FOR UPDATING ROWS (IDEA, REACTION, USER)
    */
    int updateIdea(int ideaId, String subject, String content, String attachment, Integer[] allowedRoles){
        int res = -1;
        try{
            mUpdateIdea.setString(1, subject);
            mUpdateIdea.setString(2, content);
            mUpdateIdea.setString(3, attachment);
            Array roles = mConnection.createArrayOf("INTEGER",allowedRoles);
            mUpdateIdea.setArray(4, roles);

            mUpdateIdea.setInt(5, ideaId);
            res = mUpdateIdea.executeUpdate();
        }catch(SQLException e){e.printStackTrace();}
        return res;
    }

    int updateReaction(int ideaId, Integer[] likes, Integer[] dislikes){
        int res = -1;
        try{
            Array mLikes = mConnection.createArrayOf("INTEGER",likes);
            Array mDislikes = mConnection.createArrayOf("INTEGER",dislikes);
            mUpdateReactions.setArray(1, mLikes);
            mUpdateReactions.setArray(2, mDislikes);

            mUpdateReactions.setInt(3, ideaId);
            res = mUpdateReactions.executeUpdate();
        }catch(SQLException e){e.printStackTrace();}
        return res;
    }

    int updateUser(int userId, String avatar, String name, int companyRole){
        int res = -1;
        try{
            mUpdateUser.setString(1, avatar);
            mUpdateUser.setString(2, name);
            mUpdateUser.setInt(3, companyRole);

            mUpdateUser.setInt(4, userId);
            res = mUpdateUser.executeUpdate();
        }catch(SQLException e){e.printStackTrace();}
        return res;
    }

    /*
        FUNCTIONs FOR CREATING TABLES (IDEA, REACTION, USER)
    */
    void createIdeaTable(){
        try {mCreateIdeaTable.execute();
        }catch(SQLException e) {e.printStackTrace();}
    }
    
    void createReactionTable(){
        try {mCreateReactionTable.execute();
        }catch(SQLException e) {e.printStackTrace();}
    }

    void createUserTable(){
        try {mCreateUserTable.execute();
        }catch(SQLException e) {e.printStackTrace();}
    }

     /*
        FUNCTIONs FOR DROPPING TABLES (IDEA, REACTION, USER)
    */
    void dropIdeaTable(){
        try {mDropIdeaTable.execute();
        }catch(SQLException e) {e.printStackTrace();}
    }
    
    void dropReactionTable(){
        try {mDropReactionTable.execute();
        }catch(SQLException e) {e.printStackTrace();}
    }

    void dropUserTable(){
        try {mDropUserTable.execute();
        }catch(SQLException e) {e.printStackTrace();}
    }
}