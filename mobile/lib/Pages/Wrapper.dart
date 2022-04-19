import 'package:flutter/material.dart';
import 'package:provider/provider.dart';

import '../Components/Data/GlobalState.dart';
import './Login.dart';
import './IdeaList.dart';

class Wrapper extends StatelessWidget{
  @override
  Widget build(BuildContext context){
    print("buildWrapper");
    final globalState = Provider.of<GlobalStateService>(context).state;
    print(globalState.userName);
    //print(user.userName);
    if(globalState.userToken == ""){
      return Login();
    }
    return IdeaList();
  }
}