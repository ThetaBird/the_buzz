import 'package:flutter/material.dart';
import 'Data/IdeaData.dart';

class Comment extends StatefulWidget {
  const Comment({Key? key, required this.data}) : super(key: key);

  final IdeaData data;

  @override
  State<Comment> createState() => _CommentState(data);
}

class _CommentState extends State<Comment> {
  _CommentState(this.data);

  IdeaData data;

  @override
  Widget build(BuildContext context) {    
    return SizedBox(
      child: Column(
        children: [
          SizedBox(height: 10),
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
                              Row(children:[
                                SizedBox(width:300, child: Text('${data.userId}', style: Theme.of(context).textTheme.caption)),
                                Expanded(child: SizedBox(width:50, child: Text('Likes', style: Theme.of(context).textTheme.caption))),
                                ]),
                              
                              Row(
                                children: [
                                  SizedBox(width:300, child: Text('${data.content}', style: Theme.of(context).textTheme.caption)),
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
    );
  }
}