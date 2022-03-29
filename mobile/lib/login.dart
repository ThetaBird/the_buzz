import 'package:flutter/material.dart';

class LogInState extends StatelessWidget {
  TextEditingController userNameTextEditingController =
      new TextEditingController();
  TextEditingController passwordTextEditingController =
      new TextEditingController();

  final Future<void> Function() loginAction;
  final String loginError;

  LogInState(this.loginAction, this.loginError, {Key? key}) : super(key: key);

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
                      onPressed: () async {
                        try {
                          await loginAction();
                        } catch (e) {
                          print(e);
                        }
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
