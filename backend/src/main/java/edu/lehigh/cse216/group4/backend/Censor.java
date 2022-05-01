package edu.lehigh.cse216.group4.backend;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

public class Censor {
    public static boolean checkProfanity(String input){
        try{
            String modifiedString = input.replaceAll(" ", "%20");
            URL url = new URL ("https://www.purgomalum.com/service/containsprofanity?text=" + modifiedString);
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

                boolean result = Boolean.parseBoolean(inline);
                return result;
            }
        } catch (Exception e){
            System.out.println("error" + e);
        }
        return false;
    }
}
