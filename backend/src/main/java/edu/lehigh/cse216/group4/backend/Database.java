package edu.lehigh.cse216.group4.backend;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Array;

import java.util.ArrayList;

//import com.heroku.api.User;

import java.net.URI;
import java.net.URISyntaxException;
import java.time.*;

public class Database {
    /**
     * The connection to the database. When there is no connection, it should
     * be null. Otherwise, there is a valid open connection
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
    private PreparedStatement mSelectComments;
    private PreparedStatement mUpdateIdea;

    private PreparedStatement mInsertReactions;
    private PreparedStatement mSelectReactions;
    private PreparedStatement mUpdateReactions;

    private PreparedStatement mInsertUser;
    private PreparedStatement mSelectUser;
    private PreparedStatement mUpdateUser;

    /**
     * RowData is like a struct in C: we use it to hold data, and we allow
     * direct access to its fields. In the context of this Database, RowData
     * represents the data we'd see in a row.
     * 
     * We make RowData a static class of Database because we don't really want
     * to encourage users to think of RowData as being anything other than an
     * abstract representation of a row of the database. RowData and the
     * Database are tightly coupled: if one changes, the other should too.
     */

    public static class IdeaRowData {
        long ideaId;
        long replyTo;

        String userId; // email

        int numLikes = 0, numDislikes = 0;
        String userName, userAvatar;

        long timestamp;
        String subject, content;
        IdeaRowData[] comments = null;
        String attachment;
        Short[] allowedRoles = {};

        public IdeaRowData(long ideaId, long replyTo, String userId, String userAvatar, long timestamp, String subject,
                String content, String attachment, Array allowedRoles, int numLikes, int numDislikes, String userName,
                IdeaRowData[] comments) {
            this.ideaId = ideaId;
            this.replyTo = replyTo;
            this.userId = userId;
            this.userName = userName;
            this.userAvatar = userAvatar;
            this.timestamp = timestamp;
            this.subject = subject;
            this.content = content;
            this.attachment = attachment;
            this.numLikes = numLikes;
            this.numDislikes = numDislikes;
            this.comments = comments;
            try {
                // Short[] roles = (Short[])allowedRoles.getArray();
                if (allowedRoles != null) {
                    this.allowedRoles = (Short[]) allowedRoles.getArray();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            System.out.println(this.allowedRoles);
        }

        public IdeaRowData(IdeaRowData idea) {
            this.ideaId = idea.ideaId;
            this.replyTo = idea.replyTo;
            this.userId = idea.userId;
            this.userName = idea.userName;
            this.userAvatar = idea.userAvatar;
            this.timestamp = idea.timestamp;
            this.subject = idea.subject;
            this.content = idea.content;
            this.attachment = idea.attachment;
            this.allowedRoles = idea.allowedRoles;
            this.numLikes = idea.numLikes;
            this.numDislikes = idea.numDislikes;
            this.comments = idea.comments;
        }
    }

    public static class UserRowData {
        String userId;
        String note;
        String email;
        String name;
        String avatar;
        Short companyRole;

        public UserRowData(String userId, String note, String email, String name, String avatar, Short companyRole) {
            this.userId = userId;
            this.note = note;
            this.email = email;
            this.name = name;
            this.avatar = avatar;
            this.companyRole = companyRole;
        }

        public UserRowData(UserRowData user) {
            this.userId = user.userId;
            this.note = user.note;
            this.email = user.email;
            this.name = user.name;
            this.avatar = user.avatar;
            this.companyRole = user.companyRole;
        }
    }

    public static class ReactionRowData {
        long ideaId;
        String[] likes = new String[] {};
        String[] dislikes = new String[] {};

        public ReactionRowData(long ideaId, Array likes, Array dislikes) {
            this.ideaId = ideaId;

            try {
                if (likes != null) {
                    this.likes = (String[]) likes.getArray();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }

            try {
                if (dislikes != null) {
                    this.dislikes = (String[]) dislikes.getArray();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }

        }

        public ReactionRowData(ReactionRowData reaction) {
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
     * @param db_url The URL address of the database server
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

        // Attempt to create all of our prepared statements. If any of these
        // fail, the whole getDatabase() call should fail
        try {
            // NB: we can easily get ourselves in trouble here by typing the
            // SQL incorrectly. We really should have things like "tblData"
            // as constants, and then build the strings for the statements
            // from those constants.

            // Note: no "IF NOT EXISTS" or "IF EXISTS" checks on table
            // creation/deletion, so multiple executions will cause an exception

            db.mCreateUserTable = db.mConnection.prepareStatement(
                    "CREATE TABLE IF NOT EXISTS users (" +
                            "userId VARCHAR(6) PRIMARY KEY, " + // id of user (TechDebt: turn into timestamp-based id)
                            "note VARCHAR," + // note of the user
                            "email VARCHAR, " + // file path to email of user (TechDebt: actually implement this)
                            "name VARCHAR(50) NOT NULL," + // Displayed name of user
                            "avatar VARCHAR," +
                            "companyRole SMALLINT) "); // position of user in company hierarchy
            db.mDropUserTable = db.mConnection.prepareStatement("DROP TABLE users");

            db.mCreateIdeaTable = db.mConnection.prepareStatement(
                    "CREATE TABLE IF NOT EXISTS ideas (" +
                            "ideaId BIGINT PRIMARY KEY," + // id of idea (TechDebt: turn into timestamp-based id)
                            "replyTo BIGINT DEFAULT 0," + // id of idea that the comment is for (if it is a comment)
                            "userId VARCHAR NOT NULL," + // id of user who posted the id
                            "timestamp BIGINT NOT NULL," + // time of creation in milliseconds
                            "subject VARCHAR(64) NOT NULL," + // subject of idea
                            "content VARCHAR(500) NOT NULL," + // more descriptive content of idea
                            "attachment VARCHAR(50)," + // file path to any attachment (image, spreadsheet, etc.) for
                                                        // the idea. (TechDebt: actually implement this)
                            "allowedRoles SMALLINT[]" + // array of company roles who can view this message
                            " ) "

            );
            db.mDropIdeaTable = db.mConnection.prepareStatement("DROP TABLE ideas");

            db.mCreateReactionTable = db.mConnection.prepareStatement(
                    "CREATE TABLE IF NOT EXISTS reactions (" +
                            "ideaId BIGINT NOT NULL, " + // id of idea for which reactions are reacted
                            "likes VARCHAR[], " + // ids of users who liked
                            "dislikes VARCHAR[]) "); // ids of users who disliked
            db.mDropReactionTable = db.mConnection.prepareStatement("DROP TABLE reactions");

            // Standard CRUD operations
            db.mDeleteIdea = db.mConnection.prepareStatement("DELETE FROM ideas WHERE ideaId = ?");
            db.mInsertIdea = db.mConnection.prepareStatement("INSERT INTO ideas VALUES (?,?,?,?,?,?,?,?)"); // ??//
            db.mSelectIdea = db.mConnection.prepareStatement("SELECT * from ideas WHERE ideaId = ?");
            db.mSelectAllIdeas = db.mConnection.prepareStatement("SELECT * FROM ideas WHERE replyTo = 0"); // TechDebt:
                                                                                                           // Implement
                                                                                                           // LIMIT and
                                                                                                           // limit
                                                                                                           // offset for
                                                                                                           // lazy
                                                                                                           // message
                                                                                                           // loading
            db.mUpdateIdea = db.mConnection.prepareStatement(
                    "UPDATE ideas SET subject = ?, content = ?, attachment = ?, allowedRoles = ? WHERE ideaId = ?");
            db.mSelectComments = db.mConnection.prepareStatement("SELECT * FROM ideas WHERE replyTo = ?");

            db.mInsertReactions = db.mConnection.prepareStatement("INSERT INTO reactions VALUES (?, ?, ?)");
            db.mSelectReactions = db.mConnection.prepareStatement("SELECT * from reactions WHERE ideaId = ?");
            db.mUpdateReactions = db.mConnection
                    .prepareStatement("UPDATE reactions SET likes = ?, dislikes = ? WHERE ideaId = ?");

            db.mInsertUser = db.mConnection.prepareStatement("INSERT INTO users VALUES (?, ?, ?, ?, ?, ?)"); // ??//
            db.mSelectUser = db.mConnection.prepareStatement("SELECT * from users WHERE userId = ?");
            db.mUpdateUser = db.mConnection.prepareStatement(
                    "UPDATE users SET note = ? WHERE ideaId = ?");
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
     * error occurred during the closing operation.
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
     * FUNCTIONS FOR INSERTING ROWS (IDEA, REACTION, USER)
     */

    /**
     * Insert an idea row into POSTGRESQL
     * 
     * @param userId       The ID of the user that posted the idea
     * @param subject      The subject of the idea
     * @param content      The idea content, builds off subject in more detail
     * @param attachment   Filepath to any image or other file that the user wants
     *                     to attach to the idea
     * @param allowedRoles Company roles that are allowed to view this idea
     * @return integer; status of operation
     */
    int insertIdea(long replyTo, String userId, String subject, String content, String attachment,
            Short[] allowedRoles) {
        int count = 0;
        try {

            String userName = "";
            UserRowData user = selectUser(userId);
            if (user != null) {
                userName = user.name;
            }

            long millis = clock.millis(); // timestamp

            mInsertIdea.setLong(1, millis);
            mInsertIdea.setLong(2, replyTo);
            mInsertIdea.setString(3, userId);
            mInsertIdea.setLong(4, millis);
            mInsertIdea.setString(5, subject);
            mInsertIdea.setString(6, content);
            mInsertIdea.setString(7, attachment);
            Array roles = mConnection.createArrayOf("INTEGER", allowedRoles);
            mInsertIdea.setArray(8, roles);

            count += mInsertIdea.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return count;
    }

    /**
     * Insert a reaction row into POSTGRESQL
     * 
     * @param ideaId ID of idea for reaction row
     * @return integer; status of operation
     */
    int insertReaction(long ideaId) {
        int count = 0;
        String[] emptyArr = new String[] {};
        try {
            Array reactions = mConnection.createArrayOf("VARCHAR", emptyArr);
            mInsertReactions.setLong(1, ideaId);
            mInsertReactions.setArray(2, reactions);
            mInsertReactions.setArray(3, reactions);
            count += mInsertReactions.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return count;

    }

    /**
     * Insert a user row into POSTGRESQL
     * 
     * @param email        filepath to email image for user
     * @param name         display name of user
     * @param passwordHash encrypted string of user password
     * @param companyRole  role in the company
     * @return int, status of operation
     */
    int insertUser(String userId, String note, String email, String name, String avatar, Short companyRole) {
        int count = 0;
        try {
            mInsertUser.setString(1, userId);
            mInsertUser.setString(2, note);
            mInsertUser.setString(3, email);
            mInsertUser.setString(4, name);
            mInsertUser.setString(5, avatar);
            mInsertUser.setShort(6, companyRole);
            count += mInsertUser.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return count;
    }

    /*
     * FUNCTIONS FOR SELECTING ROWS (IDEA, REACTION, USER)
     */

    /**
     * Read all ideas from POSTGRESQL.
     * 
     * @return an ArrayList of IdeaRowData containing all valid idea rows.
     */
    ArrayList<IdeaRowData> selectAllIdeas() {
        ArrayList<IdeaRowData> res = new ArrayList<IdeaRowData>();
        try {
            ResultSet rs = mSelectAllIdeas.executeQuery();
            while (rs.next()) {
                long ideaId = rs.getLong("ideaid");
                IdeaRowData idea = selectIdea(ideaId);
                res.add(idea);
            }
            rs.close();
            return res;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Read a single idea row from POSTGRESQL given an ID.
     * 
     * @param ideaId ID of the idea that you want to find in the DB
     * @return null if no idea found, IdeaRowData if idea found.
     */
    IdeaRowData selectIdea(long ideaId) {
        IdeaRowData res = null;
        try {
            mSelectIdea.setLong(1, ideaId);
            ResultSet rs = mSelectIdea.executeQuery();
            if (rs.next()) {
                int numLikes = 0, numDislikes = 0;
                ReactionRowData reactions = selectReaction(ideaId);
                if (reactions != null) {
                    numLikes = reactions.likes.length;
                    numDislikes = reactions.dislikes.length;
                }
                String userName = "";
                String userAvatar = "";
                UserRowData user = selectUser(rs.getString("userId"));
                if (user != null) {
                    userName = user.name;
                    userAvatar = user.avatar;
                }
                IdeaRowData[] comments = new IdeaRowData[]{};
                
                System.out.println(comments.length);
                res = new IdeaRowData(
                    rs.getLong("ideaId"),
                    rs.getLong("replyTo"),
                    rs.getString("userId"),
                    userAvatar,
                    rs.getLong("timestamp"),
                    rs.getString("subject"),
                    rs.getString("content"),
                    rs.getString("attachment"),
                    rs.getArray("allowedRoles"),
                    numLikes,
                    numDislikes,
                    userName,
                    comments
                );

                if(rs.getLong("replyTo") == 0){ //if row is not a comment
                    ArrayList<IdeaRowData> commentList = null;
                    commentList = selectComments(ideaId);
                    if(commentList.size() != 0){
                        comments = commentArray(commentList);
                        res.comments = comments;
                    }
                }

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return res;
    }

    /**
     * Read all comments from POSTGRESQL.
     * 
     * @return an ArrayList of IdeaRowData containing all valid comments rows.
     */
    ArrayList<IdeaRowData> selectComments(long ideaId){
        ArrayList<IdeaRowData> res = new ArrayList<IdeaRowData>();
        try{
            mSelectComments.setLong(1,ideaId);
            ResultSet rs = mSelectComments.executeQuery();
            while(rs.next()){ //comments exist
                IdeaRowData comment = selectIdea(rs.getLong("ideaId"));
                res.add(new IdeaRowData(comment));
            }
        }catch(SQLException e){e.printStackTrace();}
        System.out.println("comment size");
        System.out.println(res.size());
        return res;
    }

    /**
     * Read an idea row for an idea from POSTGRESQL given its ID
     * 
     * @param ideaId the ID of the idea that you want to get a reaction from.
     * @return null if no reaction found, ReactionRowData if reaction fouund.
     */
    ReactionRowData selectReaction(long ideaId) {
        ReactionRowData res = null;
        try {
            mSelectReactions.setLong(1, ideaId);
            ResultSet rs = mSelectReactions.executeQuery();
            if (rs.next()) {
                res = new ReactionRowData(
                        rs.getLong("ideaId"),
                        rs.getArray("likes"),
                        rs.getArray("dislikes"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return res;
    }

    /**
     * Read a single user from POSTGRESQL given a user ID
     * 
     * @param userId the ID of the user you want to pull information from
     * @return null if no user found, UserRowData if user found.
     */
    UserRowData selectUser(String userId) {
        UserRowData res = null;
        try {
            mSelectUser.setString(1, userId);
            ResultSet rs = mSelectUser.executeQuery();
            if (rs.next()) {
                res = new UserRowData(
                        rs.getString("userId"),
                        rs.getString("note"),
                        rs.getString("email"),
                        rs.getString("name"),
                        rs.getString("avatar"),
                        rs.getShort("companyRole"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return res;
    }

    /*
     * FUNCTION FOR DELETING ROWS (IDEA)
     * ("Deleting" reactions is be handled by the update functions)
     */
    int deleteIdea(long ideaId) {
        int res = -1;
        try {
            mDeleteIdea.setLong(1, ideaId);
            res = mDeleteIdea.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return res;
    }

    /*
     * FUNCTION FOR UPDATING ROWS (IDEA, REACTION, USER)
     */

    /**
     * Update the idea row in POSTGRESQL given an existing idea id and changeable
     * parameters
     * 
     * @param ideaId       id of the idea to update
     * @param subject      current or updated subject of idea
     * @param content      current or updated content of idea
     * @param attachment   current or updated attachment of idea
     * @param allowedRoles current or updated allowed roles of idea
     * @return Updated idea object, containing latest values.
     */
    int updateIdea(long ideaId, String subject, String content, String attachment, Short[] allowedRoles) {
        int res = -1;
        try {
            mUpdateIdea.setString(1, subject);
            mUpdateIdea.setString(2, content);
            mUpdateIdea.setString(3, attachment);
            Array roles = mConnection.createArrayOf("SMALLINT", allowedRoles);
            mUpdateIdea.setArray(4, roles);

            mUpdateIdea.setLong(5, ideaId);
            res = mUpdateIdea.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return res;
    }

    /**
     * Update a reaction row in POSTGRESQL
     * 
     * @param ideaId   id of the idea for which the reaction is for
     * @param likes    integer array of user IDs who liked the idea
     * @param dislikes integer array of user IDs who disliked the idea
     * @return
     */
    int updateReaction(long ideaId, String[] likes, String[] dislikes) {
        int res = -1;
        try {
            Array mLikes = mConnection.createArrayOf("VARCHAR", likes);
            Array mDislikes = mConnection.createArrayOf("VARCHAR", dislikes);
            mUpdateReactions.setArray(1, mLikes);
            mUpdateReactions.setArray(2, mDislikes);

            mUpdateReactions.setLong(3, ideaId);
            res = mUpdateReactions.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return res;
    }

    /**
     * Update a user row in POSTGRESQL
     * 
     * @param userId      user ID of the user you want to update
     * @param note        current or updated user note
     * @param email       current or updated email filepath
     * @param name        current or updated display name
     * @param companyRole current or updated role in the company
     * @return a UserRowData containing the new user information
     */
    int updateUser(String userId, String note) {
        int res = -1;
        try {
            mUpdateUser.setString(1, note);
            

            mUpdateUser.setString(2, userId);
            res = mUpdateUser.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return res;
    }

    /*
     * FUNCTIONs FOR CREATING TABLES (IDEA, REACTION, USER)
     */
    void createIdeaTable() {
        try {
            mCreateIdeaTable.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    void createReactionTable() {
        try {
            mCreateReactionTable.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    void createUserTable() {
        try {
            mCreateUserTable.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /*
     * FUNCTIONs FOR DROPPING TABLES (IDEA, REACTION, USER)
     */
    void dropIdeaTable() {
        try {
            mDropIdeaTable.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    void dropReactionTable() {
        try {
            mDropReactionTable.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    void dropUserTable() {
        try {
            mDropUserTable.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    static IdeaRowData[] commentArray(ArrayList<IdeaRowData> arrlist){
        IdeaRowData[] toRet = new IdeaRowData[arrlist.size()];
        for(int i = 0; i < arrlist.size(); i++){
            toRet[i] = arrlist.get(i);
        }
        return toRet;
    }
}