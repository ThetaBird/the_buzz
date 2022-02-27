package edu.lehigh.cse216.group4.backend;

import java.util.ArrayList;


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

    private Database db;

    /**
     * Construct the DataStore by resetting its counter and creating the
     * ArrayList for the rows of data.
     */
    DataStore() {

    }

    public synchronized int attachDB(Database db){
        if(db == null)
            return -1;
        this.db = db;
        return 0;
    }
    /*
        FUNCTIONS FOR CREATING IDEAS, REACTIONS, AND USERS
    */
    public synchronized int createIdea(int userId, String subject, String content, String attachment, Short[] allowedRoles){
        if(subject == null || content == null){return -1;}
        int ret = db.insertIdea(userId, subject, content, attachment, allowedRoles);
        return ret;
    }

    public synchronized int createReaction(int ideaId){
        if(ideaId == 0){return -1;}
        int ret = db.insertReaction(ideaId);
        return ret;
    }

    public synchronized int createUser(String avatar, String name, String passwordHash, Short companyRole){
        if(name == null || passwordHash == null){return -1;}
        int ret = db.insertUser(avatar, name, passwordHash, companyRole);
        return ret;
    }

    /*
        FUNCTIONS FOR GETTING IDEAS, REACTIONS, AND USERS
    */
    public synchronized Database.IdeaRowData readIdea(int ideaId){
        Database.IdeaRowData idea = db.selectIdea(ideaId);
        if(idea == null){return null;}
        return new Database.IdeaRowData(idea);
    }

    public synchronized ArrayList<Database.IdeaRowData> readAllIdeas(){
        ArrayList<Database.IdeaRowData> allIdeas = db.selectAllIdeas();
        ArrayList<Database.IdeaRowData> data = new ArrayList<Database.IdeaRowData>();
        for(Database.IdeaRowData idea: allIdeas){
            if(idea != null){data.add(new Database.IdeaRowData(idea));}
        }
        return data;
    }

    public synchronized Database.ReactionRowData readReaction(int ideaId){
        Database.ReactionRowData reaction = db.selectReaction(ideaId);
        if(reaction == null){return null;}
        return new Database.ReactionRowData(reaction);
    }

    public synchronized Database.UserRowData readUser(int userId){
        Database.UserRowData user = db.selectUser(userId);
        if(user == null){return null;}
        return new Database.UserRowData(user);
    }

    /*
        FUNCTIONS FOR EDITING IDEAS, REACTIONS, AND USERS
    */
    public synchronized Database.IdeaRowData updateIdea(int ideaId, String subject, String content, String attachment, Short[] allowedRoles){
        Database.IdeaRowData idea = readIdea(ideaId);
        if(idea == null || subject == null || content == null){return null;}
        
        idea.subject = subject;
        idea.content = content;
        idea.attachment = attachment;
        idea.allowedRoles = allowedRoles;

        int res = db.updateIdea(ideaId, subject, content, attachment, allowedRoles);
        if(res == -1){return null;}

        return new Database.IdeaRowData(idea);
    }

    public synchronized Database.ReactionRowData updateReaction(int ideaId, int userId, int reactionType){
        Database.IdeaRowData idea = readIdea(ideaId);
        if(idea == null){return null;}
        Database.ReactionRowData reaction = readReaction(ideaId);
        if(reaction == null){
            System.out.println("No Reaction");
            int newId = createReaction(ideaId);
            System.out.println(newId);
            reaction = readReaction(newId);
            System.out.println(reaction.ideaId);
        }
        System.out.println("Reaction cont'd");
        ArrayList<Integer> likes = reactionArrayList(reaction.likes);
        ArrayList<Integer> dislikes = reactionArrayList(reaction.dislikes);
        Integer user = Integer.valueOf(userId);
        switch(reactionType){
            case -1: //dislike toggle
                if(dislikes.indexOf(user) == -1){dislikes.add(user);}
                else{dislikes.remove(user);}
                break;
            case 0: //remove either
                if(likes.indexOf(user) != -1){likes.remove(user);}
                if(dislikes.indexOf(user) != -1){dislikes.remove(user);}
                break;
            case 1: //like toggle
                if(likes.indexOf(user) == -1){likes.add(user);}
                else{likes.remove(user);}
                break;  
        }

        Integer[] likesArr = reactionIntegerArray(likes);
        Integer[] dislikesArr = reactionIntegerArray(dislikes);
        int res = db.updateReaction(ideaId, likesArr, dislikesArr);
        if(res == -1){return null;}

        return new Database.ReactionRowData(reaction);
    }

    public synchronized Database.UserRowData updateUser(int userId, String avatar, String name, Short companyRole){
        Database.UserRowData user = readUser(userId);
        if(user == null || name == null){return null;}

        user.avatar = avatar;
        user.name = name;
        user.companyRole = companyRole;

        int res = db.updateUser(userId, avatar, name, companyRole);
        if(res == -1){return null;}

        return new Database.UserRowData(user);
    }

    /*
        FUNCTION FOR DELETING IDEAS
    */
    public synchronized boolean deleteIdea(int ideaId){
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
