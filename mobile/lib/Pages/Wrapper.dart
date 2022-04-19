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
    if(globalState.specificId != 0){
      return IdeaSpecific(ideaId: globalState.specificId);
    }
    if(globalState.newIdea){
      return const NewIdea();
    }
    if(globalState.profile){
      return Profile();
    }
    return const IdeaList();
  }
}