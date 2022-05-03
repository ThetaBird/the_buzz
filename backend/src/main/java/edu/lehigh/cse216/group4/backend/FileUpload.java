package edu.lehigh.cse216.group4.backend;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;
import com.google.api.client.http.FileContent;
import com.google.api.services.drive.model.File;

public class FileUpload {
    /*File fileMetadata = new File();
    fileMetadata.setName("photo.jpg");
    java.io.File filePath = new java.io.File("files/photo.jpg");
    FileContent mediaContent = new FileContent("image/jpeg", filePath);
    File file = driveService.files().create(fileMetadata, mediaContent)
        .setFields("id")
        .execute();
    System.out.println("File ID: " + file.getId()); */

    public static void uploadFile(String file){
        try{
            URL url = new URL ("https://www.googleapis.com/upload/drive/v3/files?uploadType=media");
            HttpURLConnection conn = (HttpURLConnection)url.openConnection();
            conn.setRequestMethod("POST");
            conn.connect();
            if (conn.getResponseCode() == 200){
                Scanner scan = new Scanner (url.openStream());
                while (scan.hasNext()) {
                    String temp = scan.nextLine();
                }
            }
            int responseCode = conn.getResponseCode();
            if (responseCode != 200) {
                throw new Exception("HTTP Response Code: " + responseCode);
            } else {
                String inline = "";
                Scanner scanner = new Scanner(url.openStream());

                while(scanner.hasNext()) {
                    inline += scanner.nextLine();
                }
                scanner.close();

            }
        } catch (Exception e){
            System.out.println("Error: " + e);
        }

        //String content_length = 
    }
  
}
