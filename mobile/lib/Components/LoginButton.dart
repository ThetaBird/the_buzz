import 'dart:convert';
import 'dart:developer';

import 'package:flutter/material.dart';
import 'package:google_sign_in/google_sign_in.dart';
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
    final ButtonStyle style = ElevatedButton.styleFrom(textStyle: const TextStyle(fontSize: 20));

    

    void onLogin() async {
      LoginService auth = new LoginService();
      var data = await auth.googleLogin();
      GlobalState state = GlobalState(data['AcessKey'],data['name']);
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
            child: const Text('Sign In With Google'),
          ),
        ],
      ),
    );
  }
}
class LoginService{
  final googleSignIn =  GoogleSignIn();
  GoogleSignInAccount? _user;

  Future<dynamic> googleLogin() async {
    /*
      final googleUser = await googleSignIn.signIn();
      if(googleUser == null){return null;}
      _user = googleUser;

      final googleAuth = await googleUser.authentication;
      */
      /*
      final credential = GoogleAuthProvider.credential(
        accessToken: googleAuth.accessToken,
        idToken: googleAuth.idToken,
      );*/
      String token = 'eyJhbGciOiJSUzI1NiIsImtpZCI6ImQzMzJhYjU0NWNjMTg5ZGYxMzNlZmRkYjNhNmM0MDJlYmY0ODlhYzIiLCJ0eXAiOiJKV1QifQ.eyJpc3MiOiJhY2NvdW50cy5nb29nbGUuY29tIiwiYXpwIjoiODQxMjUzOTQzOTgzLTIzanM4ZGt2OGhvdWN2Z2dudDN0cmwwOXY4MzI3MGFtLmFwcHMuZ29vZ2xldXNlcmNvbnRlbnQuY29tIiwiYXVkIjoiODQxMjUzOTQzOTgzLTIzanM4ZGt2OGhvdWN2Z2dudDN0cmwwOXY4MzI3MGFtLmFwcHMuZ29vZ2xldXNlcmNvbnRlbnQuY29tIiwic3ViIjoiMTEyNTgzMDQ1NjMyMTE5MjY1MjU4IiwiaGQiOiJsZWhpZ2guZWR1IiwiZW1haWwiOiJtYWoyMjRAbGVoaWdoLmVkdSIsImVtYWlsX3ZlcmlmaWVkIjp0cnVlLCJhdF9oYXNoIjoiaUdJSkZVWDNkbFljMUJOdmZPT2F4USIsIm5hbWUiOiJNYXggSmlraGFyZXYiLCJwaWN0dXJlIjoiaHR0cHM6Ly9saDMuZ29vZ2xldXNlcmNvbnRlbnQuY29tL2EvQUFUWEFKd3BlMGhBQnhudUtTNVlBTS05UXVMUU1HQlVxZTd5Ym1RR2Rrdz1zOTYtYyIsImdpdmVuX25hbWUiOiJNYXgiLCJmYW1pbHlfbmFtZSI6Ikppa2hhcmV2IiwibG9jYWxlIjoiZW4iLCJpYXQiOjE2NTAzMzc4OTIsImV4cCI6MTY1MDM0MTQ5MiwianRpIjoiZDNmOWJmOGQ4NTk2MTM2Y2NjNGRmMGM3NWY3MDUwNTRiMjViYjI0ZCJ9.a5oD7YUdg9K8b2zpTLsMR8tWQp_c-niZOniWLTqkjE3EQSkfTvaUQOryFWCE9tt6ycJuHn5gFY4sh9DgnyoVYk0tTccZcWyYt4bLcUC06YsZ19KO4ghj3z_Ea9w2LHwmodkLI00Zvkmi77w3EE2intSarueKFz4OEnxIv039xuMhO5-FLqKz73NrIXSO6oG0i2CF9jYeF1fpF_u2wQraccL4KmZrbLLyjkBcZmOaxonyd8a2jW6qc3dThg1IlXWtJZ-07cza-072bAOWfsOnaeEssvwuschIXaAnurqpvkqBtnIKoJ00F2RqMTmFd8e-kIjVr3NkWxYt682hSu-RNQ';
      http.Response res = await http.post(
        Uri.parse('https://cse216-group4-test.herokuapp.com/api/auth'),
        headers: <String, String>{
          'Content-Type': 'application/json; charset=UTF-8',
        },
        body: jsonEncode(<String, String>{
          'id_token': token,
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