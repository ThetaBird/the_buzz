package edu.lehigh.cse216.group4.backend;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class Censor {
    public static void censor(){
        try{
            URL url = new URL ("https://www.purgomalum.com/service/containsprofanity?text=fuck%20you");
            HttpURLConnection conn = (HttpURLConnection)url.openConnection();
            conn.setRequestMethod("GET");
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
                JSONParser parse = new JSONParser();
                JSONObject dataObject = (JSONObject) parse.parse(inline);
                System.out.println(dataObject.get(0));
                JSONObject userData = (JSONObject) dataObject.get(0);
            }
        } catch (Exception e){
            System.out.println("error" + e);
        }
    }
}
