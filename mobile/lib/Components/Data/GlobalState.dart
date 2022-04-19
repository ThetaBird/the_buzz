import 'package:flutter/material.dart';
import 'package:provider/provider.dart';

class GlobalState{
  String userToken = "";
  String userName = "";
  String userEmail = "";
  String userAvatar = "";
  int specificId = 0;
  bool newIdea = false;
  bool profile = false;

  GlobalState(this.userToken,this.userName,this.userEmail,this.userAvatar);
}

class GlobalStateService with ChangeNotifier{
  var _state = GlobalState("", "", "", "");
  GlobalState get state => _state;
  
  void setGlobalState(GlobalState s){
    _state = s;
    notifyListeners();
  }
  void setSpecificId(int id){
    _state.specificId = id;
    notifyListeners();
  }
  void setNewIdea(bool b){
    _state.newIdea = b;
    notifyListeners();
  }
  void setProfile(bool b){
    _state.profile = b;
    notifyListeners();
  }
  void logout(){
    _state.userName = "";
    _state.userToken = "";
    notifyListeners();
  }
}