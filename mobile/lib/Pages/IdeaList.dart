import 'dart:convert';
import 'package:flutter/material.dart';
import 'package:http/http.dart' as http;
import 'package:provider/provider.dart';
import 'package:thebuzz/Components/BuzzDrawer.dart';

import 'package:thebuzz/Components/Data/IdeaData.dart';
import 'package:thebuzz/Components/Idea.dart';
import '../Components/Data/GlobalState.dart';

class IdeaList extends StatefulWidget{
  const IdeaList({Key? key}) : super(key: key);
  
  @override
  State<IdeaList> createState() => _IdeaListState();
}

class _IdeaListState extends State<IdeaList>{
  List ideas = [];
  String? token;
  bool shouldUpdate = true;

  //pulls info from backend
  void test(String? token) async{
    List<IdeaData> ideaDataList = [];
    http.Response res = await http.get(
      Uri.parse('https://cse216-group4-test.herokuapp.com/api/ideas?token=${token}'),
      headers: <String, String>{'Content-Type': 'application/json; charset=UTF-8',},
    );
    Map<String, dynamic> response = jsonDecode(res.body);
    dynamic data = response['mData'];
    if(data == null){print("No data");}
    for(var idea in data){
      print(idea);
      ideaDataList.insert(0,IdeaData.fromJson(idea));
      
    }
    if(shouldUpdate){
      shouldUpdate = false;
      context.read<IdeaListStateService>().setIdeaList(ideaDataList);
    }
  }
  @override 
  Widget build(BuildContext context){
    final globalState = Provider.of<GlobalStateService>(context).state;
    final listState = Provider.of<IdeaListStateService>(context).state;

    final ButtonStyle buttonStyle = ElevatedButton.styleFrom(textStyle: const TextStyle(fontSize: 14,fontWeight:FontWeight.bold),onPrimary: Colors.white, maximumSize: const Size(100,40));
    const TextStyle buzzStyle = TextStyle(color: Colors.white,fontSize: 30);
    const TextStyle text1Style = TextStyle(color: Colors.white,fontSize: 22);
    const TextStyle text2Style = TextStyle(color: Colors.white,fontSize: 24);

    test(globalState.userToken);
    return Scaffold(
      appBar: AppBar(
        backgroundColor: Color.fromARGB(255, 28, 28, 28),
        title: const Text('Ideas'),
        
        actions: <Widget>[
          Padding(
            padding: const EdgeInsets.only(right:20.0),
            child: TextButton(
              style: buttonStyle,
              onPressed:(){
                context.read<GlobalStateService>().setNewIdea(true);
                },
              child: const Text('New Idea'),
            ),
          ),
        ],
      ),
      drawer: BuzzDrawer(),
      body: SizedBox.expand(
          child: ListView(
            children: listState.ideas.map(
              (ideaData) => 
              GestureDetector(
                onTap: (() => {context.read<GlobalStateService>().setSpecificId(ideaData)}),
                child:Idea(data:ideaData,full: false,)
              )
            ).toList(),
          ),
        ),
    );
  }
}