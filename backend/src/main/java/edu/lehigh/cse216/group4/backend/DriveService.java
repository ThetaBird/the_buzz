package edu.lehigh.cse216.group4.backend;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.FileContent;
import com.google.api.client.http.HttpRequestInitializer;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.DriveScopes;
import java.io.File;
import com.google.api.services.drive.model.FileList;
import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;

import org.apache.commons.io.FileUtils;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.security.GeneralSecurityException;
import java.security.PrivateKey;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/* class to demonstarte use of Drive files list API */
public class DriveService {
    /** Application name. */
    private static final String APPLICATION_NAME = "thebuzz";
    /** Global instance of the JSON factory. */
    private static final JsonFactory JSON_FACTORY = GsonFactory.getDefaultInstance();
    /** Directory to store authorization tokens for this application. */
    private static final String TOKENS_DIRECTORY_PATH = "tokens";

    private static Drive driveService;

    /**
     * Global instance of the scopes required by this quickstart.
     * If modifying these scopes, delete your previously saved tokens/ folder.
     */
    private static final List<String> SCOPES = Arrays.asList(DriveScopes.DRIVE);

    //private static final List<String> SCOPES = Collections.singletonList(DriveScopes.DRIVE_METADATA_READONLY);
    private static final String CREDENTIALS_FILE_PATH = "/credentials.json";

    public static void init() throws IOException, GeneralSecurityException {
        final NetHttpTransport HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
        System.out.println(new File(".").getAbsolutePath());
    File pk12 = new File("quickstartserv.p12");
    String serviceAccount = "drive-779@group4-344719.iam.gserviceaccount.com";

    // Build service account credential.Builder necessary for the ability to refresh tokens
    //InputStream in = DriveService.class.getResourceAsStream(CREDENTIALS_FILE_PATH);
    GoogleCredential getCredentials = new GoogleCredential.Builder()
     .setTransport(HTTP_TRANSPORT)
     .setJsonFactory(JSON_FACTORY)
     .setServiceAccountId(serviceAccount)
     .setServiceAccountPrivateKeyFromP12File(pk12)
     .setServiceAccountScopes(SCOPES)
     //.setServiceAccountUser("xxx") //IF YOU WANT TO IMPERSONATE A USER
     .build();

    // Build a new authorized API client service.

    Drive service = new Drive.Builder(HTTP_TRANSPORT, JSON_FACTORY, getCredentials)
     .setApplicationName(APPLICATION_NAME)
     .build();

     //FileList result = service.files().list().setPageSize(10).setQ('"ID OF THE SHARED DRIVE" in parents').setIncludeTeamDriveItems(true).setSupportsTeamDrives(true).setFields("nextPageToken, files(id, name)").execute();
    }

   
}