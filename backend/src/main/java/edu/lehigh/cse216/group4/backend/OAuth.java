package edu.lehigh.cse216.group4.backend;



import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Arrays;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken.Payload;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;





public class OAuth {

    public static final String CLIENT_ID = "841253943983-23js8dkv8houcvggnt3trl09v83270am.apps.googleusercontent.com";

    public static String OAuthAuthorize(String AcessKey) throws GeneralSecurityException, IOException {
        JsonFactory jsonFactory = new GsonFactory();
        NetHttpTransport transport = GoogleNetHttpTransport.newTrustedTransport();
        GoogleIdTokenVerifier verifier = new GoogleIdTokenVerifier.Builder(transport, jsonFactory)
                // Specify the CLIENT_ID of the app that accesses the backend:
                .setAudience(Arrays.asList(CLIENT_ID))
                // Or, if multiple clients access the backend:
                // .setAudience(Arrays.asList(CLIENT_ID_1, CLIENT_ID_2, CLIENT_ID_3))
                .build();

        // (Receive idTokenString by HTTPS POST)
        System.out.println(AcessKey);
        GoogleIdToken idToken = verifier.verify(AcessKey);
        System.out.println(idToken);
        if (idToken != null) {
            Payload payload = idToken.getPayload();

            // Print user identifier
            String userId = payload.getSubject();
            System.out.println("User ID: " + userId);

            // Get profile information from payload
            String email = payload.getEmail();
            boolean emailVerified = Boolean.valueOf(payload.getEmailVerified());
            String name = (String) payload.get("name");
            String pictureUrl = (String) payload.get("picture");
            String locale = (String) payload.get("locale");
            String familyName = (String) payload.get("family_name");
            String givenName = (String) payload.get("given_name");
            return email;



        } else {
            System.out.println("Invalid ID token.");
            return "";
        }
    }
}