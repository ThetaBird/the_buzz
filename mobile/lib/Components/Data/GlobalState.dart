import 'package:flutter/material.dart';
import 'package:provider/provider.dart';

class GlobalState{
  String? userToken = "";
  String? userName = "";

  GlobalState(this.userToken,this.userName);
}

class GlobalStateService with ChangeNotifier{
  var _state = GlobalState("", "");
  GlobalState get state => _state;
  
  void setGlobalState(GlobalState s){
    _state = s;
    notifyListeners();
  }
}