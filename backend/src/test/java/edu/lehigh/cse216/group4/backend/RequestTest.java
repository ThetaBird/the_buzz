package edu.lehigh.cse216.group4.backend;

import java.sql.Array;

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
        int ideaId = 0;
        int userId = 1;
        long timestamp = 0;
        String subject = "Test Title";
        String content = "Test Content";
        String attachment = "Test Attachment";
        Array allowedRoles = null;
        

        IdeaRowData d = new IdeaRowData(ideaId, userId, timestamp, subject, content, attachment, allowedRoles);

        assertTrue(d.userId == userId);
        assertTrue(d.subject.equals(subject));
        assertTrue(d.content.equals(content));
        assertTrue(d.attachment.equals(attachment));
    }
    public void testIdeaCopyConstructor(){
        int ideaId = 0;
        int userId = 1;
        long timestamp = 0;
        String subject = "Test Title";
        String content = "Test Content";
        String attachment = "Test Attachment";
        Array allowedRoles = null;
        

        IdeaRowData d = new IdeaRowData(ideaId, userId, timestamp, subject, content, attachment, allowedRoles);
        IdeaRowData d2 = new IdeaRowData(d);

        assertTrue(d.userId == d2.userId);
        assertTrue(d.subject.equals(d2.subject));
        assertTrue(d.content.equals(d2.content));
        assertTrue(d.attachment.equals(d2.attachment));
    }
    public void testUserConstructor(){
        int userId = 1;
        String note = "Test note";
        String avatar = "TestAvatar";
        String name = "Test Name";
        String passwordHash = "Test Hash";
        Short companyRole = 1;

        UserRowData d = new UserRowData(userId,note, avatar, name, passwordHash, companyRole);

        assertTrue(d.userId == userId);
        assertTrue(d.avatar.equals(avatar));
        assertTrue(d.name.equals(name));
        assertTrue(d.passwordHash.equals(passwordHash));
        assertTrue(d.companyRole.equals(companyRole));
    }
    public void testUserCopyConstructor(){
        int userId = 1;
        String note = "Test note";
        String avatar = "TestAvatar";
        String name = "Test Name";
        String passwordHash = "Test Hash";
        Short companyRole = 1;

        UserRowData d = new UserRowData(userId, note , avatar, name, passwordHash, companyRole);
        UserRowData d2 = new UserRowData(d);

        assertTrue(d.userId == d2.userId);
        assertTrue(d.avatar.equals(d2.avatar));
        assertTrue(d.name.equals(d2.name));
        assertTrue(d.passwordHash.equals(d2.passwordHash));
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
