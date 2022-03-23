package edu.lehigh.cse216.group4.backend;

public class OAuthUser {
    String email;
    boolean emailVerified;
    String name;
    String pictureUrl;
    String locale;
    String familyName;
    String givenName;

    public OAuthUser(String email, boolean emailVerified, String name,  String pictureUrl ,String locale,String familyName , String givenName){
        this.email = email;
        this.emailVerified =  emailVerified;
        this.name = name ;
        this.pictureUrl = pictureUrl;
        this.locale = locale;
        this.familyName = familyName;
        this.givenName = givenName;
    }   
}
