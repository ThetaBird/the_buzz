import 'dart:convert';
import 'package:flutter/material.dart';
import 'package:http/http.dart' as http;
import 'package:provider/provider.dart';
import 'package:thebuzz/Components/Data/ProfileData.dart';
import 'package:thebuzz/Components/Data/IdeaData.dart';
import 'package:thebuzz/Components/Idea.dart';
import '../Components/Comment.dart';
import '../Components/Data/GlobalState.dart';

class IdeaSpecific extends StatefulWidget{
  const IdeaSpecific({Key? key, required this.idea}) : super(key: key);
  final IdeaData idea;
  @override
  State<IdeaSpecific> createState() => _IdeaSpecificState(idea);

}

class _IdeaSpecificState extends State<IdeaSpecific> {
  _IdeaSpecificState(this.idea);

  IdeaData idea;
  String? token;
  bool shouldUpdate = true;

  void test(String? token) async{
    IdeaData ideaDataSpecific;
    http.Response res = await http.get(
      Uri.parse('https://cse216-group4-test.herokuapp.com/api/idea/${idea.ideaId}?token=${token}'),
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

    List<IdeaData> commentData = comments.reversed.toList();

    String commentContent = "";
    print(idea.ideaId);
    Idea ideaChild = Idea(data: idea,full:true);
    void submitComment() async {
      http.Response res = await http.post(
        Uri.parse('https://cse216-group4-test.herokuapp.com/api/ideas?token=${globalState.userToken}'),
        headers: <String, String>{
          'Content-Type': 'application/json; charset=UTF-8',
        },
        body: jsonEncode(<String, dynamic>{
          'replyTo':idea.ideaId,
          'subject': "",
          'content': commentContent,
          'attachment':null,
          'allowedRoles':[]
        }),
      );
    }

    void submitReaction(type) async {
      http.Response res = await http.post(
        Uri.parse('https://cse216-group4-test.herokuapp.com/api/idea/${globalState.specificIdea!.ideaId}/reactions?token=${globalState.userToken}'),
        headers: <String, String>{
          'Content-Type': 'application/json; charset=UTF-8',
        },
        body: jsonEncode(<String,dynamic>{
          'type': type,
        }),
      );
    }

    ProfileData profile = ProfileData(idea.userId!,idea.userName!,idea.userAvatar!);

    return Scaffold(
      appBar: AppBar(
        backgroundColor: Color.fromARGB(255, 28, 28, 28),
        title: const Text('Idea'),
        leading: GestureDetector(
          onTap: (){context.read<GlobalStateService>().setSpecificId(null);},
          child: const Icon(Icons.arrow_back_ios_rounded),
        ),
      ),
      body: Column(
        children: [
          SizedBox(
            height: 150,
            child: ListView(
              //first elevated button to click on id
              //second the subject
              //third content
              //put likes and dislikes button to the right (and num comments
              children: [
                ElevatedButton(
                    onPressed: () { context.read<GlobalStateService>().setProfile(profile); },
                    child: Text(idea.userId!, style:textStyle),
                    style: buttonStyle
                ),
                Text("Subject: " + idea.subject!),
                Text("Content: " + idea.content!),
                Text(idea.numLikes.toString() + " likes"),
                Text(idea.numDislikes.toString() + " dislikes"),
              ],
                //display number of comments
                //display likes and dislikes to the right
            ),
          ),
          SizedBox(
            height: 100,
            child: Text(((){
              if(idea.attachment != null){
                return "Download Image File";
              }
              else{
                return "No available attachment";
              }
            }()))
          ),
          ElevatedButton(
            onPressed: () {
              submitReaction(1);
              // ignore: prefer_const_constructors
              var snackBar = SnackBar(content: Text('Like Successful'));
              ScaffoldMessenger.of(context).showSnackBar(snackBar);
            },
            // ignore: prefer_const_constructors
            child: Text('Like'),
            style: buttonStyle
          ),
          ElevatedButton(
            onPressed: () {
              submitReaction(-1);
              // ignore: prefer_const_constructors
              var snackBar = SnackBar(content: Text('Dislike Successful'));
              ScaffoldMessenger.of(context).showSnackBar(snackBar);
            },
            // ignore: prefer_const_constructors
            child: Text('Dislike'),
            style: buttonStyle
          ),
          const DecoratedBox(
            decoration: BoxDecoration(
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
            child: ListView.builder(
              itemCount: commentData.length,
              itemBuilder: (context, index) {
                return ListTile(
                  title: Text(commentData[index].content!),
                  subtitle: Text(commentData[index].numLikes.toString() + " Likes " + commentData[index].numDislikes.toString() + " Dislikes"),
                  //subtitle: Text(commentData[index].numLikes.toString() + " Likes"),
                  trailing: Row(
                    mainAxisSize: MainAxisSize.min,
                    children: [
                      TextButton(style: buttonStyle,
                        onPressed: () {
                        submitReaction(1);
                        // ignore: prefer_const_constructors
                        var snackBar = SnackBar(content: Text('Like Successful'));
                        ScaffoldMessenger.of(context).showSnackBar(snackBar);
                        // ignore: prefer_const_constructors
                        }, child: Text('Like'),
                      ),
                      TextButton(style: buttonStyle,
                        onPressed: () {
                        submitReaction(-1);
                        // ignore: prefer_const_constructors
                        var snackBar = SnackBar(content: Text('Dislike Successful'));
                        ScaffoldMessenger.of(context).showSnackBar(snackBar);
                        // ignore: prefer_const_constructors
                        }, child: Text('Dislike'),
                      ),
                    ],
                  )
                );
              },
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