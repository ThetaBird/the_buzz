import 'package:flutter/material.dart';
import 'package:provider/provider.dart';
import '../Components/LoginButton.dart';

class Login extends StatelessWidget{
@override
  Widget build(BuildContext context){
     return Scaffold(
      body: Center(
        child: Column(
          mainAxisAlignment: MainAxisAlignment.center,
          children: <Widget>[
            Text(
              'The Buzz',
              style: Theme.of(context).textTheme.headline4,
            ),
            const LoginButton()
          ],
        ),
      ),
    );
  }
}