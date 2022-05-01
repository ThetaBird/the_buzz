import 'package:flutter/material.dart';
import 'package:thebuzz/Components/Data/IdeaData.dart';

class GlobalState{
  String userToken = "";
  String userName = "";
  String userEmail = "";
  String userAvatar = "";
  IdeaData? specificIdea = null;
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
  void setSpecificId(IdeaData? id){
    _state.specificIdea = id;
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

class IdeaListState{
  List<IdeaData> ideas = [];
  IdeaListState(this.ideas);
}

class IdeaListStateService with ChangeNotifier{
  var _state = IdeaListState([]);
  IdeaListState get state => _state;

  void setIdeaList(List<IdeaData> l){
    _state.ideas = l;
    notifyListeners();
  }
}