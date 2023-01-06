import 'package:flutter/material.dart';
import 'Data/IdeaData.dart';
import 'package:provider/provider.dart';
import 'Data/GlobalState.dart';

extension StringExtension on String {
  String truncateTo(int maxLength) =>
      (this.length <= maxLength) ? this : '${this.substring(0, maxLength)}...';
}

class Idea extends StatefulWidget {
  const Idea({Key? key, required this.data, required this.full}) : super(key: key);

  final IdeaData data;
  final bool full;

  @override
  State<Idea> createState() => _IdeaState();
}

class _IdeaState extends State<Idea> {
  //_IdeaState(this.data);
  
  late IdeaData _data;
  late bool full;

  @override
  Widget build(BuildContext context) {   
    const TextStyle text2Style = TextStyle(fontSize: 10);
    const TextStyle text1Style = TextStyle(fontSize: 20,fontWeight: FontWeight.bold);
    _data = widget.data;
    full = widget.full;
    if(!full){
      _data.content = '${_data.content}'.truncateTo(200);
    }
    int totalLikes = _data.numLikes! - _data.numDislikes!;
    int numComments = _data.comments!.length;
    String reaction = "";
    if(totalLikes > 0){
      reaction = 'Positive (${totalLikes} Likes)';
    }else if(totalLikes < 0){
      reaction = 'Negative (${totalLikes} Likes)';
    }else{
      reaction = 'Neutral (${totalLikes} Likes)';
    }
    reaction += ' | ${numComments} Comments';

    return Container(
      child: SizedBox(
        child: Column(
          children: [
            SizedBox(height: 15),
            DecoratedBox(
              decoration: const BoxDecoration(
                color:Color.fromARGB(255, 245, 245, 245), 
                boxShadow:[BoxShadow(
                  color: Color.fromARGB(125, 220, 220, 220),
                  spreadRadius: 5,
                  blurRadius: 7,
                  offset: Offset(0, 3),
                )]),
              
              child: Padding(
                padding: const EdgeInsets.all(12.0),
                child: Column(
                  mainAxisAlignment: MainAxisAlignment.start,
                  children: <Widget>[
                      Row(
                        children: <Widget>[
                          Expanded(
                            child: Column(
                              children: [
                                Row(children:[SizedBox(width:300, child: Text('${_data.userId}', style: text2Style))]),
                                Row(
                                  children: [
                                    Expanded(child: SizedBox(width:300, child: Text('${_data.subject}',style: text1Style))),
                                    SizedBox(width: 50),
                                    Expanded(child: SizedBox(width:50, child: Text(reaction, style: text2Style))),
                                  ],
                                ),
                                Row(
                                  children: [
                                    SizedBox(width:300, child: Text('${_data.content}', style: Theme.of(context).textTheme.caption)),
                                  ],
                                ), 
                              ],
                            ),
                          ),
                          
                        ],
                      ),
                    ],
                ),
              ),
            ),
          ],
        ),
      ),
    );
  }
}