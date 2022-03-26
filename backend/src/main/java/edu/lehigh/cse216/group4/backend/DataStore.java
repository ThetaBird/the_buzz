package edu.lehigh.cse216.group4.backend;
import java.util.HashMap;
import java.util.ArrayList;

import com.google.common.hash.Hashing;

import edu.lehigh.cse216.group4.backend.Database.UserRowData;

import java.nio.charset.StandardCharsets;

/**
 * DataStore provides access to a set of objects, and makes sure that each has
 * a unique identifier that remains unique even after the object is deleted.
 * 
 * We follow the convention that member fields of a class have names that start
 * with a lowercase 'm' character, and are in camelCase.
 * 
 * NB: The methods of DataStore are synchronized, since they will be used from a 
 * web framework and there may be multiple concurrent accesses to the DataStore.
 */
public class DataStore {
    private HashMap<String, String> userSessionKeys = new HashMap<String, String>();
    private Database db;

    /**
     * Construct the DataStore by resetting its counter and creating the
     * ArrayList for the rows of data.
     */
    DataStore() {}

    /** 
     * Set the private db variable in DataStore to allow for Database manipulation
     * @param db The Database object to perform actions on.
     * 
     * @return An integer (-1 or 0) describing the success of the operation.
     */
    public synchronized int attachDB(Database db){
        if(db == null)
            return -1;
        this.db = db;
        return 0;
    }


    /** 
     * check if the token is in local hashmap
     * @param the token to be verified
     * 
     * @return An integer (1 or 0) describing the success of the operation.
     */
    public synchronized int verifyToken(String token){
        String sessionKey = Hashing.sha256().hashString(token, StandardCharsets.UTF_8).toString(); //hash the token
        if(!userSessionKeys.containsValue(sessionKey)){ //check if hashed token in hashmap
            return 0;
        }
        return 1;
    }


    /** 
     * add session key to the hashamap
     * @param the email and sessionKey to be added
     * 
     * @return no return
     */
    public synchronized void addSessionKey(String email, String sessionKey){
        if(!email.substring(6).equals("@lehigh.edu")){ //check if email ends in @lehigh.edu
            //reject user
            return;
        }
        checkUser(email);
        userSessionKeys.put(email,sessionKey); //Add (email, sessionkey) to userSessionKeys
    }

     /** 
     * check if the user is lehigh user
     * @param the email of the user
     * 
     * @return no return
     */
    public synchronized void checkUser(String email){
        email = email.substring(0,6); //get lehigh username from email 
        UserRowData user = readUser(email);
        if(user == null){
            createUser(email);
        }
    }

    /*
        FUNCTIONS FOR CREATING IDEAS, REACTIONS, AND USERS
    */

    /**
     * Insert an idea into the database, given certain parameters.
     * 
     * @param userId The ID of the user that posted the idea
     * @param subject The subject of the idea
     * @param content The idea content, builds off subject in more detail
     * @param attachment Filepath to any image or other file that the user wants to attach to the idea
     * @param allowedRoles Company roles that are allowed to view this idea
     * @return Integer; -1 if not enough information to create idea, 0 if insertion fail, and 1 if insertion success.
     */
    public synchronized int createIdea(long replyTo , String  userId, String userAvatar, String subject, String content, String attachment, Short[] allowedRoles){
        if(subject == null || content == null){return -1;}
        int ret = db.insertIdea(replyTo, userId, userAvatar , subject, content, attachment, allowedRoles);
        return ret;
    }

    /**
     * Insert a reaction row  for an idea into the database
     * 
     * @param ideaId ID of the idea that the reaction row is for.
     * @return Integer; -1 if not enough information to create idea, 0 if insertion fail, and 1 if insertion success.
     */
    public synchronized int createReaction(long ideaId){
        if(ideaId == 0){return -1;}
        int ret = db.insertReaction(ideaId);
        return ret;
    }

    /**
     * Insert a user row into the database
     * 
     * 
     * @param email filepath to email image for user
     * @param name display name of user
     * @param passwordHash encrypted string of user password
     * @param companyRole role in the company
     * @return Integer; -1 if not enough information to create idea, 0 if insertion fail, and 1 if insertion success.
     */
    public synchronized int createUser(String email){
        if(email == null || email == ""){return -1;}
        int ret = db.insertUser( email.substring(0,6) ,"", email,"", "",(short)1);
        return ret;
    }

    /*
        FUNCTIONS FOR GETTING IDEAS, REACTIONS, AND USERS
    */

    /**
     * Read a single idea row given an ID.
     * 
     * @param ideaId ID of the idea that you want to find in the DB
     * @return null if no idea found, IdeaRowData if idea found.
     */
    public synchronized Database.IdeaRowData readIdea(long ideaId){        
        Database.IdeaRowData idea = db.selectIdea(ideaId);
        if(idea == null){return null;}
        return new Database.IdeaRowData(idea);
    }

    /**
     * Read all ideas from the DB.
     * 
     * @return an ArrayList of IdeaRowData containing all valid idea rows.
     */
    public synchronized ArrayList<Database.IdeaRowData> readAllIdeas(){
        ArrayList<Database.IdeaRowData> allIdeas = db.selectAllIdeas();
        ArrayList<Database.IdeaRowData> data = new ArrayList<Database.IdeaRowData>();
        for(Database.IdeaRowData idea: allIdeas){
            if(idea != null){data.add(new Database.IdeaRowData(idea));} //??//
        }
        return data;
    }

      /**
     * Read all comments of an idea from the DB.
     * 
     * @return an ArrayList of IdeaRowData containing all valid idea rows.
     */
    public synchronized ArrayList<Database.IdeaRowData> readComments(long ideaId){
        ArrayList<Database.IdeaRowData> allComments = db.selectComments(ideaId);
        ArrayList<Database.IdeaRowData> data = new ArrayList<Database.IdeaRowData>();
        for(Database.IdeaRowData idea: allComments){
            if(idea != null){data.add(new Database.IdeaRowData(idea));} //??//
        }
        return data;
    }

    /**
     * Read a reaction row for an idea given its idea ID
     * 
     * @param ideaId the ID of the idea that you want to get a reaction from.
     * @return null if no reaction found, ReactionRowData if reaction fouund.
     */
    public synchronized Database.ReactionRowData readReaction(long ideaId){
        Database.ReactionRowData reaction = db.selectReaction(ideaId);
        if(reaction == null){return null;}
        return new Database.ReactionRowData(reaction);
    }

    /**
     * Read a single user from the DB given a user ID
     * 
     * @param userId the ID of the user you want to pull information from
     * @return null if no user found, UserRowData if user found.
     */
    public synchronized Database.UserRowData readUser(String userId){
        Database.UserRowData user = db.selectUser(userId);
        if(user == null){return null;}
        return new Database.UserRowData(user);
    }

    /*
        FUNCTIONS FOR EDITING IDEAS, REACTIONS, AND USERS
    */
    /**
     * Update the idea row given an existing idea id and changeable parameters
     * 
     * @param ideaId id of the idea to update
     * @param subject current or updated subject of idea
     * @param content current or updated content of idea
     * @param attachment current or updated attachment of idea
     * @param allowedRoles current or updated allowed roles of idea
     * @return Updated idea object, containing latest values.
     */
    public synchronized Database.IdeaRowData updateIdea(long ideaId, String subject, String content, String attachment, Short[] allowedRoles){
        Database.IdeaRowData idea = readIdea(ideaId);
        if(idea == null || subject == null || content == null){return null;}
        
        idea.subject = subject;
        idea.content = content;
        idea.attachment = attachment;
        idea.allowedRoles = allowedRoles;

        int res = db.updateIdea(ideaId, subject, content, attachment, allowedRoles);
        if(res == -1){return null;}

        return new Database.IdeaRowData(idea);  //??//
    }

    /**
     * Add/remove a like or dislike from the reaction row of an idea, creating a reaction row
     * in the process if one doesn't exist already
     * 
     * @param ideaId id of the idea for which the reaction is for
     * @param userId id of the user posting a reaction
     * @param reactionType -1 for toggling a dislike, 0 for clearing reactions, 1 for toggling a like
     * @return Updated reaction row for idea, or a new reaction row if one didn't exist
     */
    public synchronized Database.ReactionRowData updateReaction(long ideaId, int userId, int reactionType){
        Database.IdeaRowData idea = readIdea(ideaId);
        if(idea == null){return null;}
        Database.ReactionRowData reaction = readReaction(ideaId);
        if(reaction == null){
            int newId = createReaction(ideaId);
            if(newId == 0){return null;}
            reaction = readReaction(ideaId);
        }
        ArrayList<Integer> likes = reactionArrayList(reaction.likes);
        ArrayList<Integer> dislikes = reactionArrayList(reaction.dislikes);
        Integer user = Integer.valueOf(userId);
        switch(reactionType){
            case -1: //dislike toggle
                if(dislikes.indexOf(user) == -1){dislikes.add(user);}
                else{dislikes.remove(user);}
                if(likes.indexOf(user)!= -1){likes.remove(user);}
                break;
            case 0: //remove either
                if(likes.indexOf(user) != -1){likes.remove(user);}
                if(dislikes.indexOf(user) != -1){dislikes.remove(user);}
                break;
            case 1: //like toggle
                if(likes.indexOf(user) == -1){likes.add(user);}
                else{likes.remove(user);}
                if(dislikes.indexOf(user)!= -1){dislikes.remove(user);}
                break;  
        }

        Integer[] likesArr = reactionIntegerArray(likes);
        Integer[] dislikesArr = reactionIntegerArray(dislikes);
        reaction.likes = likesArr;
        reaction.dislikes = dislikesArr;

        int res = db.updateReaction(ideaId, likesArr, dislikesArr);
        if(res == -1){return null;}

        return new Database.ReactionRowData(reaction);
    }

    /**
     * Update a user row in the DB
     * 
     * @param userId user ID of the user you want to update
     * @param email current or updated email filepath
     * @param name current or updated display name
     * @param companyRole current or updated role in the company
     * @return a UserRowData containing the new user information
     */
    public synchronized Database.UserRowData updateUser(String userId, String note , String email, String name, Short companyRole){
        Database.UserRowData user = readUser(userId);
        if(user == null || name == null){return null;}

        user.email = email;
        user.name = name;
        user.companyRole = companyRole;

        int res = db.updateUser(userId, note , email, name, companyRole);
        if(res == -1){return null;}

        return new Database.UserRowData(user);
    }

    /*
        FUNCTION FOR DELETING IDEAS
    */
    /**
     * Remove an idea row from the DB.
     * 
     * @param ideaId ID of the idea row you want to remove
     * @return false if if operatin fail, true if operation success.
     */
    public synchronized boolean deleteIdea(long ideaId){
        int res = db.deleteIdea(ideaId);
        if(res == -1){return false;}
        return true;
    }

    /*
        CONVERT INTEGER[] TO ARRAYLIST<INTEGER> AND VICE VERSA, for convenience
    */
    static ArrayList<Integer> reactionArrayList(Integer[] arr){
        ArrayList<Integer> toRet = new ArrayList<Integer>();
        for(Integer i: arr){
            toRet.add(i);
        }
        return toRet;
    }

    static Integer[] reactionIntegerArray(ArrayList<Integer> arrlist){
        Integer[] toRet = new Integer[arrlist.size()];
        for(int i = 0; i < arrlist.size(); i++){
            toRet[i] = arrlist.get(i);
        }
        return toRet;
    }
}










