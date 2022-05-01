import 'package:flutter/material.dart';
import 'userprofile.dart';
import 'login.dart';
import 'dart:async';
import 'dart:convert';
import 'package:http/http.dart' as http;
import 'package:flutter_appauth/flutter_appauth.dart';
import 'package:flutter_secure_storage/flutter_secure_storage.dart';
import 'package:google_sign_in/google_sign_in.dart';
import 'package:firebase_auth/firebase_auth.dart';

final FlutterAppAuth appAuth = FlutterAppAuth();
final FlutterSecureStorage secureStorage = const FlutterSecureStorage();
final FirebaseAuth _auth = FirebaseAuth.instance;
final GoogleSignIn _googleSignIn = GoogleSignIn(
  clientId:
      '841253943983-23js8dkv8houcvggnt3trl09v83270am.apps.googleusercontent.com',
  scopes: [
    'email',
  ],
);

//AUTHO VARIABLES
const Auth0_Domain = 'dev-r0jcji5y.us.auth0.com';
const Auth0_Client_ID =
    '841253943983-23js8dkv8houcvggnt3trl09v83270am.apps.googleusercontent.com';

const Auth0_Redirect_URI = 'com.example.thebuzz://login-callback';
const Auth0_Issuer = 'https://thebuzz.example.com';

void main() {
  runApp(MyApp());
}

class MyApp extends StatefulWidget {
  const MyApp({Key? key}) : super(key: key);

  @override
  _MyAppState createState() => _MyAppState();
}

class _MyAppState extends State<MyApp> {
  bool isBusy = false;
  bool isLoggedIn = false;
  String errorMessage = "ERROR";
  late String name;
  late String picture;

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      title: 'The Buzz',
      theme: ThemeData(
        scaffoldBackgroundColor: Colors.deepPurple[200],
        primarySwatch: Colors.blue,
      ),
      home: Scaffold(
          appBar: AppBar(title: Text('Welcome!')),
          body: Center(
            child: isBusy
                ? const CircularProgressIndicator()
                : isLoggedIn
                    ? ProfileRoute(logoutAction, name, picture)
                    : LogInState(loginAction, errorMessage),
          )),
    );
  }

  Map<String, dynamic> parseIdToken(String idToken) {
    final List<String> parts = idToken.split(r'.');
    assert(parts.length == 3);

    return jsonDecode(
        utf8.decode(base64Url.decode(base64Url.normalize(parts[1]))));
  }

  Future<Map> getUserDetails(String accessToken) async {
    final url = 'https://dev-r0jcji5y.us.auth0.com/userinfo';
    final response = await http.get(
      url,
      headers: <String, String>{'Authorization': 'Bearer $accessToken'},
    );

    if (response.statusCode == 200) {
      return jsonDecode(response.body);
    } else {
      throw Exception('Failed to get user details');
    }
  }

  Future<void> loginAction() async {
    setState(() {
      isBusy = true;
      errorMessage = '';
    });

    try {
      final AuthorizationTokenResponse result =
          await appAuth.authorizeAndExchangeCode(
        AuthorizationTokenRequest(Auth0_Client_ID, Auth0_Redirect_URI,
            issuer: 'https://dev-r0jcji5y.us.auth0.com',
            scopes: <String>['openid', 'profile', 'offline_access'],
            promptValues: ['login']),
      );

      final idToken = parseIdToken(result.idToken);
      final profile = await getUserDetails(result.accessToken);

      await secureStorage.write(
          key: 'refresh_token', value: result.refreshToken);

      setState(() {
        isBusy = false;
        isLoggedIn = true;
        name = idToken['name'];
        picture = profile['picture'];
      });
    } catch (e, s) {
      print('login error: $e - stack: $s');

      setState(() {
        isBusy = false;
        isLoggedIn = false;
        errorMessage = e.toString();
      });
    }
  }

  void logoutAction() async {
    await secureStorage.delete(key: 'refresh_token');
    setState(() {
      isLoggedIn = false;
      isBusy = false;
    });
  }

  void initState() {
    client_id:
    '841253943983-23js8dkv8houcvggnt3trl09v83270am.apps.googleusercontent.com';
    initAction();
    // super.initState();
  }

  void initAction() async {
    final storedRefreshToken = await secureStorage.read(key: 'refresh_token');
    if (storedRefreshToken == null) return;

    setState(() {
      isBusy = true;
    });

    try {
      final response = await appAuth.token(TokenRequest(
        Auth0_Client_ID,
        Auth0_Redirect_URI,
        issuer: Auth0_Issuer,
        refreshToken: storedRefreshToken,
      ));

      final idToken = parseIdToken(response.idToken);
      final profile = await getUserDetails(response.accessToken);

      secureStorage.write(key: 'refresh_token', value: response.refreshToken);

      setState(() {
        isBusy = false;
        isLoggedIn = true;
        name = idToken['name'];
        picture = profile['picture'];
      });
    } catch (e, s) {
      print('error on refresh token: $e - stack: $s');
      logoutAction();
    }
  }
}

class LogInState extends StatelessWidget {
  TextEditingController userNameTextEditingController =
      new TextEditingController();
  TextEditingController passwordTextEditingController =
      new TextEditingController();

  final loginAction;
  final String loginError;

  LogInState(this.loginAction, this.loginError);

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      backgroundColor: Colors.deepPurple[200],
      appBar: AppBar(
        title: Text('Welcome!'),
        centerTitle: true,
        backgroundColor: Colors.deepPurple[300],
        elevation: 0.0,
      ),
      body: Padding(
          padding: EdgeInsets.fromLTRB(30.0, 30.0, 30.0, 0.0),
          child: SingleChildScrollView(
            child: Column(
                crossAxisAlignment: CrossAxisAlignment.start,
                children: <Widget>[
                  Center(
                    child: Text('THE BUZZ',
                        style: TextStyle(
                          color: Colors.blue,
                          letterSpacing: 2.0,
                          fontSize: 40.0,
                          fontWeight: FontWeight.bold,
                        )),
                  ),
                  SizedBox(height: 20.0),
                  TextField(
                    controller: userNameTextEditingController,
                    decoration: InputDecoration(
                      hintText: "username",
                      hintStyle: TextStyle(
                        color: Colors.white,
                      ),
                      focusedBorder: UnderlineInputBorder(
                        borderSide: BorderSide(color: Colors.white),
                      ),
                      enabledBorder: UnderlineInputBorder(
                        borderSide: BorderSide(color: Colors.white),
                      ),
                    ),
                  ),
                  SizedBox(height: 10.0),
                  TextField(
                    controller: passwordTextEditingController,
                    decoration: InputDecoration(
                      hintText: "password",
                      hintStyle: TextStyle(
                        color: Colors.white,
                      ),
                      focusedBorder: UnderlineInputBorder(
                        borderSide: BorderSide(color: Colors.white),
                      ),
                      enabledBorder: UnderlineInputBorder(
                        borderSide: BorderSide(color: Colors.white),
                      ),
                    ),
                  ),
                  SizedBox(height: 10.0),
                  Center(
                    child: RaisedButton(
                      onPressed: () {
                        //loginAction();
                        _handleSignIn();
                      },
                      child: Text('Log In with Google'),
                      color: Colors.blue[800],
                    ),
                  ),
                  SizedBox(height: 10.0),
                ]),
          )),
    );
  }
}

/*GoogleSignIn _googleSignIn = GoogleSignIn(
  scopes: [
    'email',
  ],
);*/

Future<void> _handleSignIn() async {
  try {
    await _googleSignIn.signIn();
  } catch (error) {
    print(error);
  }
}
