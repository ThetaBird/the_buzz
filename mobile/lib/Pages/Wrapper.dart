import 'package:flutter/material.dart';
import 'package:provider/provider.dart';
import 'package:thebuzz/Pages/IdeaSpecific.dart';
import 'package:thebuzz/Pages/NewIdea.dart';
import 'package:thebuzz/Pages/Profile.dart';

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
    if(globalState.profile != null){
      return Profile(profile: globalState.profile!);
    }
    if(globalState.specificIdea != null){
      return IdeaSpecific(idea: globalState.specificIdea!);
    }
    if(globalState.newIdea){
      return const NewIdea();
    }
    return const IdeaList();
  }
}