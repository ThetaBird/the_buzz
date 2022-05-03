package edu.lehigh.cse216.group4.backend;

// Import the Spark package, so that we can make use of the "get" function to 
// create an HTTP GET route
import spark.Spark;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Map;
import com.google.gson.*;

/**
 * For now, our app creates an HTTP server that can only get and add data.
 */
public class App {
    final static Gson gson = new Gson();

    public static void main(String[] args) {
        //Censor censor = new Censor();
        //censor.censor();
        
        // dataStore holds all of the data that has been provided via HTTP
        // requests
        //
        // NB: every time we shut down the server, we will lose all data, and
        // every time we start the server, we'll have an empty dataStore,
        // with IDs starting over from 0.
        try{DriveService.init();}
        catch(IOException e){
            System.out.println("IOException\n" + e);
        }catch(GeneralSecurityException g){
            System.out.println("GeneralSecurityException\n" + g);
        }

        
        final DataStore dataStore = new DataStore();

        // get the Postgres configuration from the environment
        Map<String, String> env = System.getenv();
        String db_url = env.get("DATABASE_URL");

        // Get a fully-configured connection to the database, or exit
        // immediately
        Database db = Database.getDatabase(db_url);
        if (db == null)
            return;


        //db.dropIdeaTable();
        //db.dropReactionTable();
        //db.dropUserTable();
        
        db.createIdeaTable();
        db.createUserTable();
        db.createReactionTable();

        // For Phase0, App.java is mostly unchanged. DB requests are passed over to
        // dataStore (per usual), which is now heavily modified to communicate with
        // POSTGRES.
        // Attach the created Database to dataStore, so that dataStore can read/write it
        dataStore.attachDB(db);
        dataStore.attachCache();
        // Set up the location for serving static files. If the STATIC_LOCATION
        // environment variable is set, we will serve from it. Otherwise, serve
        // from "/web"

        // Get the port on which to listen for requests
        Spark.port(getIntFromEnv("PORT", 4567));

        String static_location_override = System.getenv("STATIC_LOCATION");
        if (static_location_override == null) {
            Spark.staticFileLocation("/web");
        } else {
            Spark.staticFiles.externalLocation(static_location_override);
        }

        String cors_enabled = env.get("CORS_ENABLED");

        if ("True".equalsIgnoreCase(cors_enabled)) {
            final String acceptCrossOriginRequestsFrom = "*";
            final String acceptedCrossOriginRoutes = "GET,PUT,POST,DELETE,OPTIONS";
            final String supportedRequestHeaders = "Content-Type,Authorization,X-Requested-With,Content-Length,Accept,Origin";
            enableCORS(acceptCrossOriginRequestsFrom, acceptedCrossOriginRoutes, supportedRequestHeaders);
        }

        Routes.setRoutes(dataStore);
        OAuth.setDataStore(dataStore);

        // String testJSON = "{'avatar':'ayooo.png','name':'John
        // Doe','passwordHash':'feefgbvgrvf','companyRole':1}";
        // RequestUser req = gson.fromJson(testJSON, RequestUser.class);
        // Short i = 1;
        // int newId = dataStore.createUser("test.png", "John Doe", "frfrfefrf", i);
        // Database.UserRowData userData = dataStore.readUser(newId);
        // System.out.println(req.avatar);
        // System.out.println(req.name);
        // System.out.println(req.passwordHash);
        // System.out.println(req.companyRole);

        // int newId = dataStore.createUser(req.avatar, req.name, req.passwordHash,
        // req.companyRole);
        // System.out.println(newId);
        // Database.IdeaRowData ideaData = dataStore.readIdea(1);
        // System.out.println("TEST");
        // System.out.println(ideaData.allowedRoles);

        // Database.ReactionRowData result = dataStore.updateReaction(1, 1, 1);
        // System.out.println(result.ideaId);

        
    }

    /**
     * Get an integer environment varible if it exists, and otherwise return the
     * default value.
     * 
     * @envar The name of the environment variable to get.
     * @defaultVal The integer value to use as the default if envar isn't found
     * 
     * @returns The best answer we could come up with for a value for envar
     */
    static int getIntFromEnv(String envar, int defaultVal) {
        ProcessBuilder processBuilder = new ProcessBuilder();
        if (processBuilder.environment().get(envar) != null) {
            return Integer.parseInt(processBuilder.environment().get(envar));
        }
        return defaultVal;
    }

    /**
     * Set up CORS headers for the OPTIONS verb, and for every response that the
     * server sends. This only needs to be called once.
     * 
     * @param origin  The server that is allowed to send requests to this server
     * @param methods The allowed HTTP verbs from the above origin
     * @param headers The headers that can be sent with a request from the above
     *                origin
     */
    private static void enableCORS(String origin, String methods, String headers) {
        // Create an OPTIONS route that reports the allowed CORS headers and methods
        Spark.options("/*", (request, response) -> {
            String accessControlRequestHeaders = request.headers("Access-Control-Request-Headers");
            if (accessControlRequestHeaders != null) {
                response.header("Access-Control-Allow-Headers", accessControlRequestHeaders);
            }
            String accessControlRequestMethod = request.headers("Access-Control-Request-Method");
            if (accessControlRequestMethod != null) {
                response.header("Access-Control-Allow-Methods", accessControlRequestMethod);
            }
            return "OK";
        });

        // 'before' is a decorator, which will run before any
        // get/post/put/delete. In our case, it will put three extra CORS
        // headers into the response
        Spark.before((request, response) -> {
            response.header("Access-Control-Allow-Origin", origin);
            response.header("Access-Control-Request-Method", methods);
            response.header("Access-Control-Allow-Headers", headers);
        });
    }

     




}
