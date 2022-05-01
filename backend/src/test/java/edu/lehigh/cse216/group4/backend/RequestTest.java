package edu.lehigh.cse216.group4.backend;

import java.sql.Array;
import java.util.ArrayList;

import edu.lehigh.cse216.group4.backend.Database.IdeaRowData;
import edu.lehigh.cse216.group4.backend.Database.ReactionRowData;
import edu.lehigh.cse216.group4.backend.Database.UserRowData;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;





public class RequestTest extends TestCase{
    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public RequestTest(String testName) {super(testName);}

    /**
     * @return the suite of tests being tested
     */
    public static Test suite() {return new TestSuite(RequestTest.class);}

    /**
     * Ensure that the constructor populates every relevant field of the object it
     * creates
     */
    
    public void testIdeaConstructor() {
        long ideaId = 0;
        long replyTo = 1;
        String userId = "Test UserID";
        String userAvatar = "Test userAvatar";
        long timestamp = 0;
        String subject = "Test Title";
        String content = "Test Content";
        String attachment = "Test Attachment";
        Array allowedRoles = null;
        int numLikes = 0 ;
        int numDislikes = 0 ; 
        String userName ="Test UserName"; 
       IdeaRowData[] comment = null;
        

        IdeaRowData d = new IdeaRowData(ideaId, replyTo, userId, userAvatar, timestamp, subject, content, attachment, allowedRoles, numLikes , numDislikes , userName ,comment);

        assertTrue(d.userId == userId);
        assertTrue(d.subject.equals(subject));
        assertTrue(d.content.equals(content));
        assertTrue(d.attachment.equals(attachment));
    }
    public void testIdeaCopyConstructor(){
        long ideaId = 0;
        long replyTo = 1;
        String userId = "Test UserID";
        String userAvatar = "Test userAvatar";
        long timestamp = 0;
        String subject = "Test Title";
        String content = "Test Content";
        String attachment = "Test Attachment";
        Array allowedRoles = null;
        int numLikes = 0 ;
        int numDislikes = 0 ; 
        String userName ="Test UserName"; 
        IdeaRowData[] comment = null;
        

        IdeaRowData d = new IdeaRowData(ideaId, replyTo, userId, userAvatar, timestamp, subject, content, attachment, allowedRoles, numLikes , numDislikes , userName ,comment);
       IdeaRowData d2 = new IdeaRowData(d);

        assertTrue(d.userId == d2.userId);
        assertTrue(d.subject.equals(d2.subject));
        assertTrue(d.content.equals(d2.content));
        assertTrue(d.attachment.equals(d2.attachment));
    }
    public void testUserConstructor(){
        String userId = "Test user";
        String note = "Test note";
        String email = "Testemail";
        String name = "Test Name";
        String avatar = "Test avatar";
        Short companyRole = 1;

        UserRowData d = new UserRowData(userId,note, email, name, avatar, companyRole);

        assertTrue(d.userId == userId);
        assertTrue(d.email.equals(email));
        assertTrue(d.name.equals(name));
        assertTrue(d.avatar.equals(avatar));
        assertTrue(d.companyRole.equals(companyRole));
    }

    public void testUserCopyConstructor(){
        String userId = "Test user";
        String note = "Test note";
        String email = "Testemail";
        String name = "Test Name";
        String avatar = "Test avatar";
        Short companyRole = 1;

        UserRowData d = new UserRowData(userId,note, email, name, avatar, companyRole);
        UserRowData d2 = new UserRowData(d);

        assertTrue(d.userId == d2.userId);
        assertTrue(d.email.equals(d2.email));
        assertTrue(d.name.equals(d2.name));
        assertTrue(d.avatar.equals(d2.avatar));
        assertTrue(d.companyRole.equals(d2.companyRole));
    }
    public void testReactionConstructor(){
        int ideaId = 1;
        Array likes = null;
        Array dislikes = null;

        ReactionRowData d = new ReactionRowData(ideaId, likes, dislikes);

        assertTrue(d.ideaId == ideaId);
       
    }
    public void testReactionCopyConstructor(){
        int ideaId = 1;
        Array likes = null;
        Array dislikes = null;

        ReactionRowData d = new ReactionRowData(ideaId, likes, dislikes);
        ReactionRowData d2 = new ReactionRowData(d);

        assertTrue(d.ideaId == d2.ideaId);
        assertTrue(d.likes.equals(d2.likes));
        assertTrue(d.dislikes.equals(d2.dislikes));
    }
    
}
