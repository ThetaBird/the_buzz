import 'package:flutter/material.dart';
import 'Data/IdeaData.dart';

class Idea extends StatefulWidget {
  const Idea({Key? key, required this.data}) : super(key: key);

  // This widget is the home page of your application. It is stateful, meaning
  // that it has a State object (defined below) that contains fields that affect
  // how it looks.

  // This class is the configuration for the state. It holds the values (in this
  // case the title) provided by the parent (in this case the App widget) and
  // used by the build method of the State. Fields in a Widget subclass are
  // always marked "final".

  final IdeaData data;

  @override
  State<Idea> createState() => _IdeaState(data);
}

class _IdeaState extends State<Idea> {
  _IdeaState(this.data);

  IdeaData data;

  void _setIdea(IdeaData d) {
    setState(() {
     data = d;
    });
  }

  @override
  Widget build(BuildContext context) {
    // This method is rerun every time setState is called, for instance as done
    // by the _incrementCounter method above.
    //
    // The Flutter framework has been optimized to make rerunning build methods
    // fast, so that you can just rebuild anything that needs updating rather
    // than having to individually change instances of widgets.
    
    return SizedBox(
      child: Column(
        children: [
          DecoratedBox(
            decoration: const BoxDecoration(color:Color.fromARGB(255, 208, 208, 208)),
            child: Column(
              mainAxisAlignment: MainAxisAlignment.start,
              children: <Widget>[
                  Row(
                    children: <Widget>[
                      Column(children: [
                        SizedBox(width:300, child: Text('${data.userId}')),
                        SizedBox(width:300, child: Text('${data.subject}',style: Theme.of(context).textTheme.bodyMedium,)),
                        SizedBox(
                          width: 300,
                          child: Text('${data.content}'),
                        ),
                        
                      ],)
                    ],
                  ),
                  Row(

                  ),
                ],
            ),
          ),
          SizedBox(height: 15),
        ],
      ),
    );
  }
}
/*
Scaffold(
      appBar: AppBar(
        // Here we take the value from the MyHomePage object that was created by
        // the App.build method, and use it to set our appbar title.
        title: Text('${widget.data.subject}'),
      ),
      body: Center(
        child: Column(
          mainAxisAlignment: MainAxisAlignment.center,
          children: <Widget>[
            const Text(
              'You have pushed the button this many times:',
            ),
            Text(
              '_counter',
              style: Theme.of(context).textTheme.headline4,
            ),
          ],
        ),
      ),
      floatingActionButton: FloatingActionButton(
        onPressed: (){},
        tooltip: 'Increment',
        child: const Icon(Icons.add),
      ), // This trailing comma makes auto-formatting nicer for build methods.
    );
*/