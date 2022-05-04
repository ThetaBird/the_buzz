package edu.lehigh.cse216.group4.backend;

import spark.Spark;


import java.io.FileOutputStream;
import java.io.OutputStream;
import java.net.http.HttpClient;
import java.util.Base64;

import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpResponse;
import com.google.api.services.drive.*;
import com.google.api.services.drive.model.File;

import com.google.gson.*;


public class Routes {
    // gson provides us with a way to turn JSON into objects, and objects
    // into JSON.
    //
    // NB: it must be final, so that it can be accessed from our lambdas
    //
    // NB: Gson is thread-safe. See
    // https://stackoverflow.com/questions/10380835/is-it-ok-to-use-gson-instance-as-a-static-field-in-a-model-bean-reuse
    final static Gson gson = new Gson();
    public static void setRoutes(DataStore dataStore){
        // Set up a route for serving the main page
        Spark.get("/", (req, res) -> {  //??//
            res.redirect("/index.html");
            return "";
        });
        // GET route that returns all message titles and Ids.  All we do is get 
        // the data, embed it in a StructuredResponse, turn it into JSON, and 
        // return it.  If there's no data, we return "[]", so there's no need 
        // for error handling.
        Spark.get("/api/ideas", (request, response) -> {
            // ensure status 200 OK, with a MIME type of JSON
            response.status(200);
            response.type("application/json");
            return gson.toJson(new StructuredResponse("ok", null, dataStore.readAllIdeas()));
        });
        /*
        Spark.get("/users", (request, response) -> {
            // ensure status 200 OK, with a MIME type of JSON
            response.status(200);
            response.type("application/json");
            return gson.toJson(new StructuredResponse("ok", null, dataStore.readAllUsers()));
        });
        */
        Spark.get("/api/user/:id", (request, response) -> {
            String idx = request.params("id");
            String token = request.queryParams("token");
            // ensure status 200 OK, with a MIME type of JSON
            response.status(200);
            response.type("application/json");

            String validToken = dataStore.verifyToken(token);
            System.out.println("route");
            System.out.println(validToken + "\n");
            

            if(validToken.equals("")){return gson.toJson(new StructuredResponse("error", "unauthorized", null));}
            return gson.toJson(new StructuredResponse("ok", "test", null));
            /*
            Database.UserRowData userData = dataStore.readUser(idx);
            if (userData == null) {
                return gson.toJson(new StructuredResponse("error", idx + " not found", null));
            } else {
                return gson.toJson(new StructuredResponse("ok", null, userData));
            }*/
        });

        //Update user note in DB
        Spark.post("/api/user/:id", (request, response) -> {
            String idx = request.params("id");
            String token = request.queryParams("token");
            RequestUser req = gson.fromJson(request.body(), RequestUser.class);
            // ensure status 200 OK, with a MIME type of JSON
            response.status(200);
            response.type("application/json");

            String validToken = dataStore.verifyToken(token);

            if(validToken.equals("")){return gson.toJson(new StructuredResponse("error", "unauthorized", null));}

            Database.UserRowData userData = dataStore.readUser(idx);
            if (userData == null) {
                return gson.toJson(new StructuredResponse("error", idx + " not found", null));
            } else {
                dataStore.updateUser(userData.userId, req.note);
                return gson.toJson(new StructuredResponse("ok", null, userData));
            }
        });

        // GET route that returns everything for a single row in the DataStore.
        // The ":id" suffix in the first parameter to get() becomes 
        // request.params("id"), so that we can get the requested row ID.  If 
        // ":id" isn't a number, Spark will reply with a status 500 Internal
        // Server Error.  Otherwise, we have an integer, and the only possible 
        // error is that it doesn't correspond to a row with data.
        Spark.get("/api/idea/:id", (request, response) -> {
            long idx = Long.parseLong(request.params("id"));
            String token = request.queryParams("token");
            // ensure status 200 OK, with a MIME type of JSON
            response.status(200);
            response.type("application/json");

            String validToken = dataStore.verifyToken(token);

            if(validToken.equals("")){return gson.toJson(new StructuredResponse("error", "unauthorized", null));}

            Database.IdeaRowData ideaData = dataStore.readIdea(idx);
            if (ideaData == null) {
                return gson.toJson(new StructuredResponse("error", idx + " not found", null));
            } else {
                return gson.toJson(new StructuredResponse("ok", null, ideaData));
            }
        });
        /** 
        Spark.get("/api/idea/:id/reactions", (request, response) -> {
            int idx = Integer.parseInt(request.params("id"));
            // ensure status 200 OK, with a MIME type of JSON
            response.status(200);
            response.type("application/json");
            Database.ReactionRowData reactionData = dataStore.readReaction(idx);
            if (reactionData == null) {
                return gson.toJson(new StructuredResponse("error", idx + " not found", null));
            } else {
                return gson.toJson(new StructuredResponse("ok", null, reactionData));
            }
        });
        */
        
        // POST route for adding a new element to the DataStore.  This will read
        // JSON from the body of the request, turn it into a SimpleRequest 
        // object, extract the title and message, insert them, and return the 
        // ID of the newly created row.
        Spark.post("/api/ideas", (request, response) -> {
            // NB: if gson.Json fails, Spark will reply with status 500 Internal 
            // Server Error
            RequestIdea req = gson.fromJson(request.body(), RequestIdea.class);
            // ensure status 200 OK, with a MIME type of JSON
            // NB: even on error, we return 200, but with a JSON object that
            //     describes the error.
            String token = request.queryParams("token");
            response.status(200);
            response.type("application/json");

            String validToken = dataStore.verifyToken(token);

            if(validToken.equals("")){return gson.toJson(new StructuredResponse("error", "unauthorized", null));}
/*
            if(req.attachment != null){ //optional file upload
                // https://stackoverflow.com/questions/37674016/similar-java-function-like-atob-in-javascript
                String byteAttachment = new String(Base64.getEncoder().encode(req.attachment.getBytes()));  //encodes into base64, makes string

                byte[] data = Base64.getDecoder().decode(byteAttachment);   //decodes string into base64 array
                File file = new File();
                try(OutputStream stream = new FileOutputStream(file.getName()) ) 
                {
                    stream.write(data); //reads data into file
                    Drive driveService = new DriveQuickstart().getService();    //creates google drive instance to upload to
                    if(driveService != null){
                       file = driveService.files().create(file).execute();    //creates and uploads file
                       file.setId(String.valueOf(req.attachment));
                    }
                } catch (Exception e) 
                {
                    System.err.println("Couldn't write to file");
                }
            }
*/
            /*
                - Call the profanity API, pass subject & content and receive results
                (POST request)
                if(response.bad == true){
                    return gson.toJson(new StructuredResponse("error", "no bad words!", null));
                }
            */
            boolean hasProfanity = Censor.checkProfanity(req.subject + " " + req.content);
            if(hasProfanity){
                return gson.toJson(new StructuredResponse("error", "profanity", null));
            }
            // NB: createEntry checks for null title and message
            int newId = dataStore.createIdea( req.replyTo, validToken , req.subject, req.content, req.attachment, req.allowedRoles);
            if (newId == -1) {
                return gson.toJson(new StructuredResponse("error", "error performing insertion", null));
            } else {
                return gson.toJson(new StructuredResponse("ok", "" + newId, null));
            }
        });
        /*
        Spark.post("/api/users", (request, response) -> {
            // NB: if gson.Json fails, Spark will reply with status 500 Internal 
            // Server Error
            RequestUser req = gson.fromJson(request.body(), RequestUser.class); //??// Database.UserRowData//
            // ensure status 200 OK, with a MIME type of JSON
            // NB: even on error, we return 200, but with a JSON object that
            //     describes the error.
            response.status(200);
            response.type("application/json");
            // NB: createEntry checks for null title and message
            int newId = dataStore.createUser(req.note , req.avatar, req.name, req.passwordHash, req.companyRole);
            if (newId == -1) {
                return gson.toJson(new StructuredResponse("error", "error performing insertion", null));
            } else {
                return gson.toJson(new StructuredResponse("ok", "" + newId, null));
            }
        });
        */
        Spark.post("/api/idea/:id/reactions", (request, response) -> {
            long idx = Long.parseLong(request.params("id"));
            //System.out.println(idx);
            // ensure status 200 OK, with a MIME type of JSON
            RequestReaction req = gson.fromJson(request.body(), RequestReaction.class);
            String token = request.queryParams("token");
            response.status(200);
            response.type("application/json");

            String validToken = dataStore.verifyToken(token);

            if(validToken.equals("")){return gson.toJson(new StructuredResponse("error", "unauthorized", null));}

            Database.ReactionRowData result = dataStore.updateReaction(idx, validToken, req.type);
            if (result == null) {
                return gson.toJson(new StructuredResponse("error", "unable to update row " + idx, null));
            } else {
                return gson.toJson(new StructuredResponse("ok", null, result));
            }
        });
        // PUT route for updating a row in the DataStore.  This is almost 
        // exactly the same as POST
        Spark.put("/api/idea/:id", (request, response) -> {
            // If we can't get an ID or can't parse the JSON, Spark will send
            // a status 500
            long idx = Long.parseLong(request.params("id"));
            String token = request.queryParams("token");
            
            String ideaUserId = dataStore.readIdea(idx).userId;
            RequestIdea req = gson.fromJson(request.body(), RequestIdea.class);
            // ensure status 200 OK, with a MIME type of JSON
            response.status(200);
            response.type("application/json");

            String validToken = dataStore.verifyToken(token);
            String userId = validToken;
            
            if(validToken.equals("")){return gson.toJson(new StructuredResponse("error", "unauthorized", null));}

            if(!userId.equals(ideaUserId)){return gson.toJson(new StructuredResponse("error", "access denied", null));}

            Database.IdeaRowData result = dataStore.updateIdea(idx, req.subject, req.content, req.attachment, req.allowedRoles);
            if (result == null) {
                return gson.toJson(new StructuredResponse("error", "operation failed " + idx, null));
            } else {
                return gson.toJson(new StructuredResponse("ok", null, result));
            }
        });

        // DELETE route for removing a row from the DataStore
        Spark.delete("/api/idea/:id", (request, response) -> {
            // If we can't get an ID, Spark will send a status 500
            long idx = Long.parseLong(request.params("id"));
            String token = request.queryParams("token");
            // ensure status 200 OK, with a MIME type of JSON
            response.status(200);
            response.type("application/json");
            // NB: we won't concern ourselves too much with the quality of the 
            //     message sent on a successful delete

            
            String ideaUserId = dataStore.readIdea(idx).userId;
            String validToken = dataStore.verifyToken(token);
            String userId = validToken;

            if(validToken.equals("")){return gson.toJson(new StructuredResponse("error", "unauthorized", null));}
            if(!userId.equals(ideaUserId)){return gson.toJson(new StructuredResponse("error", "access denied", null));}
            boolean result = dataStore.deleteIdea(idx);
            if (!result) {
                return gson.toJson(new StructuredResponse("error", "unable to delete row " + idx, null));
            } else {
                return gson.toJson(new StructuredResponse("ok", null, null));
            }
        });



        Spark.post("/api/auth", (req, res) -> {
            RequestOAuth reqOAuth = gson.fromJson(req.body(), RequestOAuth.class);
            System.out.println(reqOAuth.id_token);
            String accessKey = reqOAuth.id_token;
            return gson.toJson(new StructuredResponse("ok", null ,OAuth.OAuthAuthorize(accessKey)));//return section key

        });   








    }

}
