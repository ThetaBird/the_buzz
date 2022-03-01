import 'dart:async';
import 'dart:convert';

import 'package:flutter/material.dart';
import 'package:http/http.dart' as http;

String? inputUserID = "default ID";
String? inputComment = "default comment";
void main() {
  runApp(const MyApp());
}

class AddLikes{
  static int likes = 0;

  static void increment() => likes++;

  static void decrement() => likes--;
}

class MyApp extends StatefulWidget {
  const MyApp({Key? key}) : super(key: key);

  @override
  _MyAppState createState() => _MyAppState();
}

class _MyAppState extends State<MyApp> {
  late Future<Album> futureAlbum;

  @override
  void initState() {
    super.initState();
    futureAlbum = fetchAlbum();
  }

  var myliked = false;
  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      home: Scaffold(
        appBar: AppBar(
          leading: ElevatedButton.icon(
            icon: const Icon(Icons.post_add_outlined, size: 18),
            label: Text("Post"),
            onPressed: () {},
          ),
          backgroundColor: Colors.deepPurple.shade300,
          title: Text('The Buzz'),
          centerTitle: true,
          actions: [
            IconButton(
              //tooltip: localization.starterAppTooltipFavorite,
              icon: const Icon(Icons.favorite),
              onPressed: () {},
            ),
            IconButton(
              //tooltip: localization.starterAppTooltipSearch,
              icon: const Icon(
                Icons.search,
              ),
              onPressed: () {},
            ),
            PopupMenuButton<Text>(
              itemBuilder: (context) {
                return [
                  PopupMenuItem(
                    child: Text("Page1 coming soon"),
                  ),
                  PopupMenuItem(
                    child: Text("Page2 coming soon"),
                  ),
                  PopupMenuItem(
                    child: Text("page3 coming soon"),
                  ),
                ];
              },
            )
          ],
        ),
        body: Scrollbar(
          child: ListView(
            restorationId: 'Comment:',
            padding: const EdgeInsets.symmetric(vertical: 8),
            children: [
              for (int index = 1; index < 6; index++)
                ListTile(
                    leading: ExcludeSemantics(
                      child: CircleAvatar(child: Text('$index')),
                    ),
                    title: FutureBuilder<Album>(
                      future: futureAlbum,
                      builder: (context, snapshot) {
                        if (snapshot.hasData) {
                          return Text(snapshot.data!.userId);
                        } else if (snapshot.hasError) {
                          return Text('${snapshot.error}');
                        }

                        // By default, show a loading spinner.
                        return const CircularProgressIndicator();
                      },
                    ),
                    subtitle: FutureBuilder<Album>(
                      future: futureAlbum,
                      builder: (context, snapshot) {
                        if (snapshot.hasData) {
                          return Text(snapshot.data!.content);
                        } else if (snapshot.hasError) {
                          return Text('${snapshot.error}');
                        }

                        // By default, show a loading spinner.
                        return const CircularProgressIndicator();
                      },
                    ),
                    trailing: Container(
                      child: Column(
                        children: [
                          IconButton(
                            iconSize: 30,
                            icon: const Icon(
                              Icons.thumb_up,
                            ),
                            onPressed: () {},
                          ),
                          Text("111"),
                        ],
                      ),
                    )),
              const MyCustomForm(),
            ],
          ),
        ),
      ),
    );
  }
}

Future<Album> fetchAlbum() async {
  final response = await http
      .get(Uri.parse('https://cse216-group4-app.herokuapp.com/api/idea/:id'));

  if (response.statusCode == 200) {
    // If the server did return a 200 OK response,
    // then parse the JSON.
    return Album.fromJson(jsonDecode(response.body));
  } else {
    // If the server did not return a 200 OK response,
    // then throw an exception.
    throw Exception('Failed to load album');
  }
}

class Album {
  final String userId;
  final String content;

  const Album({
    required this.userId,
    required this.content,
  });

  factory Album.fromJson(Map<String, dynamic> json) {
    return Album(
      userId: json['userId'],
      content: json['content'],
    );
  }
}

// Create a Form widget.
class MyCustomForm extends StatefulWidget {
  const MyCustomForm({Key? key}) : super(key: key);

  @override
  MyCustomFormState createState() {
    return MyCustomFormState();
  }
}

// Create a corresponding State class.
// This class holds data related to the form.
class MyCustomFormState extends State<MyCustomForm> {
  // Create a global key that uniquely identifies the Form widget
  // and allows validation of the form.
  //
  // Note: This is a GlobalKey<FormState>,
  // not a GlobalKey<MyCustomFormState>.
  final _formKey = GlobalKey<FormState>();

  @override
  Widget build(BuildContext context) {
    // Build a Form widget using the _formKey created above.
    return Form(
      key: _formKey,
      child: Column(
        crossAxisAlignment: CrossAxisAlignment.start,
        children: [
          Text("User ID:"),
          TextFormField(
            // The validator receives the text that the user has entered.
            validator: (value) {
              if (value == null || value.isEmpty) {
                return 'Please enter the user ID';
              } else {
                inputUserID = value;
              }
              return null;
            },
          ),
          Text("Comment:"),
          TextFormField(
            // The validator receives the text that the user has entered.
            validator: (value) {
              if (value == null || value.isEmpty) {
                return 'Please enter your comments';
              } else {
                inputComment = value;
              }
              return null;
            },
          ),
          Padding(
            padding: const EdgeInsets.symmetric(vertical: 16.0),
            child: ElevatedButton(
              onPressed: () {
                // Validate returns true if the form is valid, or false otherwise.
                if (_formKey.currentState!.validate()) {
                  // If the form is valid, display a snackbar. In the real world,
                  // you'd often call a server or save the information in a database.
                  ScaffoldMessenger.of(context).showSnackBar(
                    SnackBar(
                        content: Text(
                            " Posting $inputUserID's comment: $inputComment")),
                  );
                }
              },
              child: const Text('Post'),
            ),
          ),
        ],
      ),
    );
  }
}
