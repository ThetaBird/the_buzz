import 'package:flutter/material.dart';

class MainWrapper extends StatefulWidget{
  const MainWrapper({Key? key}) : super(key: key);
  
  @override
  State<MainWrapper> createState() => _MainWrapperState();
}

class _MainWrapperState extends State<MainWrapper>{
  @override 
  Widget build(BuildContext context){
    
    return Scaffold(
      appBar: AppBar(

      ),
      body: Center(),
    );
  }
}