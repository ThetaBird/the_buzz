import 'package:flutter/material.dart';
import '../Components/LoginButton.dart';

class Login extends StatelessWidget{
@override
  Widget build(BuildContext context){
    TextStyle textStyle = TextStyle(color:Colors.white,fontSize: 50,fontWeight: FontWeight.w400);
     return Scaffold(
      body: Center(
        child: DecoratedBox(
          decoration: BoxDecoration(color: Color.fromARGB(255, 28, 28, 28)),
          child: Column(
            mainAxisAlignment: MainAxisAlignment.center,
            children: <Widget>[
              Text(
                'The Buzz',
                style: textStyle
              ),
              const SizedBox(height: 30),
              const LoginButton()
            ],
          ),
        ),
      ),
    );
  }
}