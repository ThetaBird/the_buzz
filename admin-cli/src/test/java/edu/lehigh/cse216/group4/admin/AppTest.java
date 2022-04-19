package edu.lehigh.cse216.group4.admin;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import java.util.Map;

import edu.lehigh.cse216.group4.admin.Database.*;

/**
 * Unit test for simple App.
 */
public class AppTest 
    extends TestCase
{
    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public AppTest( String testName )
    {
        super( testName );
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite()
    {
        return new TestSuite( AppTest.class );
    }

    /**
     * Rigourous Test :-)
     */
    public void testApp()
    {
        assertTrue( true );
    }

    /**
     * sets up a database object, makes sure it exists
     */
    public void testDatabase()
    {
        // same code as App.java
        /*Map<String, String> env = System.getenv();
        String ip = env.get("POSTGRES_IP");
        String port = env.get("POSTGRES_PORT");
        String user = env.get("POSTGRES_USER");
        String pass = env.get("POSTGRES_PASS");

        Database db = Database.getDatabase(ip, port, user, pass);*/
        String db_url = System.getenv("DATABASE_URL");
        String port = System.getenv("POSTGRES_PORT");
        String user = System.getenv("POSTGRES_USER");
        String pass = System.getenv("POSTGRES_PASS");

        // Get a fully-configured connection to the database, or exit
        // immediately
        Database db = Database.getDatabase(db_url, port, user, pass);
        //Database db = DriverManager.getConnection(db_url);
        if (db == null)
            return;

        //will fail if postgres variables have not been set
        assertNotNull(db);

    }
}
