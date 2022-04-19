import 'dart:convert';
import 'package:flutter/material.dart';
import 'package:http/http.dart' as http;
import 'package:provider/provider.dart';

import 'package:thebuzz/Components/Data/IdeaData.dart';
import 'package:thebuzz/Components/Idea.dart';
import '../Components/Comment.dart';
import '../Components/Data/GlobalState.dart';

class IdeaSpecific extends StatefulWidget{
  const IdeaSpecific({Key? key, required this.ideaId}) : super(key: key);
  final int ideaId;
  @override
  State<IdeaSpecific> createState() => _IdeaSpecificState(ideaId);
}

class _IdeaSpecificState extends State<IdeaSpecific>{
  _IdeaSpecificState(this.ideaId);

  int ideaId;
  IdeaData idea = IdeaData(); 
  String? token;
  bool shouldUpdate = true;

  void test(String? token) async{
    IdeaData ideaDataSpecific;
    http.Response res = await http.get(
      Uri.parse('https://cse216-group4-test.herokuapp.com/api/idea/${ideaId}?token=${token}'),
      headers: <String, String>{'Content-Type': 'application/json; charset=UTF-8',},
    );
    Map<String, dynamic> response = jsonDecode(res.body);
    dynamic data = response['mData'];
    if(data == null){print("No data");}
    ideaDataSpecific = IdeaData.fromJson(data);

    if(shouldUpdate){
      setState(() {
        shouldUpdate = false;
        idea = ideaDataSpecific;
      });
    }
  }
  @override 
  Widget build(BuildContext context){
    
    final globalState = Provider.of<GlobalStateService>(context).state;
    test(globalState.userToken);

    final ButtonStyle buttonStyle = ElevatedButton.styleFrom(textStyle: const TextStyle(fontSize: 14,fontWeight:FontWeight.bold),onPrimary: Colors.white, primary: Color.fromARGB(255, 28, 28, 28));
    const TextStyle textStyle = TextStyle(color: Colors.white,fontSize: 14,fontWeight: FontWeight.bold);
    List<IdeaData> comments = [];
    if(idea.comments != null){comments = idea.comments as List<IdeaData>;}
    

    String commentContent = "";
    print(idea.ideaId);
    Idea ideaChild = Idea(data: idea,full:true);
    void submitComment(){

    }
    return Scaffold(
      appBar: AppBar(
        backgroundColor: Color.fromARGB(255, 28, 28, 28),
        title: const Text('Idea'),
        leading: GestureDetector(
          onTap: (){context.read<GlobalStateService>().setSpecificId(0);},
          child: const Icon(Icons.arrow_back_ios_rounded),
        ),
      ),
      body: Column(
        children: [
          SizedBox(
            height: 150,
            child: ListView(
              children: [ideaChild],
            ),
          ),
          const DecoratedBox(
            decoration: const BoxDecoration(
                color: Color.fromARGB(255, 28, 28, 28),
              ),
              child: SizedBox(
                width: 1000,
                height: 40,
                child: Padding(
                  padding: EdgeInsets.all(12.0),
                  child: Text("Comments",style: textStyle,),
                ),
              ),
          ),
          Expanded(
            child: SizedBox(
              height:300,
              child: ListView(
                children: comments.map((commentData) => Comment(data:commentData)).toList().reversed.toList(),
              ),
            ),
          ),
          SizedBox(
            height: 75,
            child: Row(
              children:[
                Expanded(
                  child: Padding(
                    padding: const EdgeInsets.all(8.0),
                    child: TextField(
                      
                      decoration: const InputDecoration(
                        border: UnderlineInputBorder(),
                        labelText: 'Comment',
                      ),
                      onChanged: (String value) => {commentContent = value},
                    ),
                  ),
                ),
                Padding(
                  padding: const EdgeInsets.all(8.0),
                  child: Container(width:50,child: ElevatedButton(onPressed: submitComment, child: Icon(Icons.send), style: buttonStyle,)),
                )
              ]
            )
          )
        ],
      ),
    );
  }
}