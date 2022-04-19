import 'dart:convert';
import 'dart:developer';

import 'package:flutter/material.dart';
import 'package:google_sign_in/google_sign_in.dart';
import 'package:firebase_auth/firebase_auth.dart';
import 'package:provider/provider.dart';
import 'package:http/http.dart' as http;

import 'Data/GlobalState.dart';

class LoginButton extends StatelessWidget{
  const LoginButton({Key? key}) : super(key: key);
  

  Stream<GlobalState>? get user {
      return null;
    }

  @override
  Widget build(BuildContext context){

    final ButtonStyle style = ElevatedButton.styleFrom(textStyle: const TextStyle(fontSize: 30),primary: Colors.white,onPrimary: Colors.black);
    TextStyle textStyle = TextStyle(color:Colors.black,fontSize:14,fontWeight: FontWeight.bold);
    

    void onLogin() async {
      LoginService auth = new LoginService();
      var data = await auth.googleLogin();
      GlobalState state = GlobalState(data['AcessKey'],data['name'],data['email'],data['pictureUrl']);
      context.read<GlobalStateService>().setGlobalState(state);
      //log('$token');

    }
    
    return Center(
      child: Column(
        mainAxisSize: MainAxisSize.min,
        children: <Widget>[
          ElevatedButton(
            style: style,
            onPressed: onLogin,
            child: Text('Login',style: textStyle,),
          ),
        ],
      ),
    );
  }
}
class LoginService{
  final GoogleSignIn googleSignIn = GoogleSignIn();
  GoogleSignInAccount? _user;

  Future<dynamic> googleLogin() async {
    
    
    final GoogleSignInAccount? googleSignInAccount = await googleSignIn.signIn();
      if(googleSignInAccount == null){return null;}
      //_user = googleSignInAccount;

      final googleAuth = await googleSignInAccount.authentication;
      String? token = googleAuth.idToken;
      print("TOKEN");
      print(token);
      /*
      final credential = GoogleAuthProvider.credential(
        accessToken: googleAuth.accessToken,
        idToken: googleAuth.idToken,
      );*/
      //String token = 'eyJhbGciOiJSUzI1NiIsImtpZCI6ImQzMzJhYjU0NWNjMTg5ZGYxMzNlZmRkYjNhNmM0MDJlYmY0ODlhYzIiLCJ0eXAiOiJKV1QifQ.eyJpc3MiOiJhY2NvdW50cy5nb29nbGUuY29tIiwiYXpwIjoiODQxMjUzOTQzOTgzLTIzanM4ZGt2OGhvdWN2Z2dudDN0cmwwOXY4MzI3MGFtLmFwcHMuZ29vZ2xldXNlcmNvbnRlbnQuY29tIiwiYXVkIjoiODQxMjUzOTQzOTgzLTIzanM4ZGt2OGhvdWN2Z2dudDN0cmwwOXY4MzI3MGFtLmFwcHMuZ29vZ2xldXNlcmNvbnRlbnQuY29tIiwic3ViIjoiMTEyNTgzMDQ1NjMyMTE5MjY1MjU4IiwiaGQiOiJsZWhpZ2guZWR1IiwiZW1haWwiOiJtYWoyMjRAbGVoaWdoLmVkdSIsImVtYWlsX3ZlcmlmaWVkIjp0cnVlLCJhdF9oYXNoIjoieGVfSGNsSEEwdUxUY0VIcUg1Z0JIUSIsIm5hbWUiOiJNYXggSmlraGFyZXYiLCJwaWN0dXJlIjoiaHR0cHM6Ly9saDMuZ29vZ2xldXNlcmNvbnRlbnQuY29tL2EvQUFUWEFKd3BlMGhBQnhudUtTNVlBTS05UXVMUU1HQlVxZTd5Ym1RR2Rrdz1zOTYtYyIsImdpdmVuX25hbWUiOiJNYXgiLCJmYW1pbHlfbmFtZSI6Ikppa2hhcmV2IiwibG9jYWxlIjoiZW4iLCJpYXQiOjE2NTAzNjY5NzIsImV4cCI6MTY1MDM3MDU3MiwianRpIjoiMDZlNGMyOWE4MDVmMDY4NmEzNDdkNDMyOWJiYTY4Yjc1NGMwYjQ4MyJ9.fkC6lFj_98HUcVek0sNG2nPl0V_tOK6RB1fzePmcFeZ8uwNncRMgqt27PIhq1BnD9gBDCeh0q90hPQM4MX8C0pKS4OTbI71OYvP62bRyfH8a1AU1ZMT4UeUvt7pVuVdV4VZWHaIID8k4ggNyVtg_og8R_9ArhRgGw1iLGiJbjSwfBvSdyt1qaXuxqE-lFHPYfP9wzQ63827HzQf7FIFeLlY8jPw4gj5--ia8Mo1le7HvcMnc_m-vqOYk5F9a9HxGV5xQF1sldVfpG5L8Y-64QLgaO1A8lI6qyrWTqr8br0Uso2lgAqz3uBORjV7jHaEchYdqzyMfFfgCRkp59VojdA';
      http.Response res = await http.post(
        Uri.parse('https://cse216-group4-test.herokuapp.com/api/auth'),
        headers: <String, String>{
          'Content-Type': 'application/json; charset=UTF-8',
        },
        body: jsonEncode(<String, String>{
          'id_token': '${token}',
        }),
      );
      print(res.body);
      Map<String, dynamic> response = jsonDecode(res.body);
      dynamic data = response['mData'];
      if(data == null){print("No data");}
      //setGlobalState(GlobalState(data['AcessKey'],data['name']));

      print('${response['mData']['email']}');
      return data;
      //return token; //googleAuth.idToken;
    }
}