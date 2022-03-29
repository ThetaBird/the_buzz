package edu.lehigh.cse216.group4.backend;

public class OAuthUser {
    String userId;
    String email;
    boolean emailVerified;
    String name;
    String pictureUrl;
    String locale;
    String familyName;
    String givenName;
    String AcessKey;

    public OAuthUser(String userId, String email, boolean emailVerified, String name,  
    String pictureUrl ,String locale,String familyName , String givenName , String AcessKey){
        this.userId = userId;
        this.email = email;
        this.emailVerified =  emailVerified;
        this.name = name ;
        this.pictureUrl = pictureUrl;
        this.locale = locale;
        this.familyName = familyName;
        this.givenName = givenName;
        this.AcessKey = AcessKey;
    }   
}
