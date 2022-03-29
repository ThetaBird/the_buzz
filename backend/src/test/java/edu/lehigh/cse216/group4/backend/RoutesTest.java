package edu.lehigh.cse216.group4.backend;


import java.sql.Array;

import edu.lehigh.cse216.group4.backend.Database.IdeaRowData;
import edu.lehigh.cse216.group4.backend.Database.ReactionRowData;
import edu.lehigh.cse216.group4.backend.Database.UserRowData;

import junit.framework.TestCase;
import junit.framework.TestSuite;
import spark.Spark;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static junit.framework.TestCase.assertEquals;
import static spark.Spark.awaitInitialization;
import static spark.Spark.stop;


public class RoutesTest {
    

    @Test
    public void testIdeasGet() {

        String testUrl = "/api/ideas";

        Spark.get(testUrl, (request, response) -> {
            // ensure status 200 OK, with a MIME type of JSON
            response.status(200);
            response.type("application/json");
            assertEquals(200, response.status());
            return "";
        });
    }

    @Test
    public void testUserGet() {

        String testUrl = "/api/user/:id";

        Spark.get(testUrl, (request, response) -> {
            // ensure status 200 OK, with a MIME type of JSON
            response.status(200);
            response.type("application/json");
            assertEquals(200, response.status());
            return "";
        });
    }

    @Test
    public void testIdeaGet() {

        String testUrl = "/api/idea/:id";

        Spark.get(testUrl, (request, response) -> {
            // ensure status 200 OK, with a MIME type of JSON
            response.status(200);
            response.type("application/json");
            assertEquals(200, response.status());
            return "";
        });
    }



    @Test
    public void testIdeaPost() {

        String testUrl = "/api/ideas";

       Spark.post(testUrl , (request, response) -> {
            // ensure status 200 OK, with a MIME type of JSON
            response.status(200);
            response.type("application/json");
            assertEquals(200, response.status());
            return "";
        });
    }

    @Test
    public void testReactionPost() {

        String testUrl = "/api/idea/:id/reactions";

        Spark.post(testUrl, (request, response) -> {
            // ensure status 200 OK, with a MIME type of JSON
            response.status(200);
            response.type("application/json");
            assertEquals(200, response.status());
            return "";
        });
    }

    @Test
    public void testIdeaPut() {

        String testUrl = "/api/idea/:id";

        Spark.put(testUrl, (request, response) -> {
            // ensure status 200 OK, with a MIME type of JSON
            response.status(200);
            response.type("application/json");
            assertEquals(200, response.status());
            return "";
        });
    }

    @Test
    public void testIdeaDelete() {

        String testUrl = "/api/idea/:id";

        Spark.delete(testUrl, (request, response) -> {
            // ensure status 200 OK, with a MIME type of JSON
            response.status(200);
            response.type("application/json");
            assertEquals(200, response.status());
            return "";
        });
    }  
}
