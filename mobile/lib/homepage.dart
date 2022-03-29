import 'dart:async';
import 'dart:convert';

import 'package:flutter/material.dart';
import 'package:http/http.dart' as http;

String? inputUserID = "default ID";
String? inputComment = "default comment";
void main() {
  runApp(const MyApp());
}

class AddLikes {
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

  get onSelected => null;

  @override
  void initState() {
    super.initState();
    futureAlbum = fetchAlbum();
  }

  var myliked = false;
  final navigatorKey = GlobalKey<NavigatorState>();
  @override
  Widget build(BuildContext context) {
    return MaterialApp();
  }
}

Future<Album> fetchAlbum() async {
  try {
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
  } catch (e) {
    print(e);
    throw e;
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
          Row(
            children: [
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
              Padding(
                padding: const EdgeInsets.fromLTRB(110.0, 16.0, 20.0, 8.0),
                child: ElevatedButton(
                  onPressed: () {
                    Navigator.push(
                      context,
                      MaterialPageRoute(
                          builder: (context) => const ProfileRoute()),
                    );
                  },
                  child: const Text('User Profile'),
                ),
              ),
              Padding(
                padding: const EdgeInsets.fromLTRB(8.0, 20.0, 0.0, 16.0),
                child: ElevatedButton(
                  onPressed: () {
                    Navigator.push(
                      context,
                      MaterialPageRoute(
                          builder: (context) => const LogInRoute()),
                    );
                  },
                  child: const Text('Log Out'),
                ),
              ),
            ],
          ),
        ],
      ),
    );
  }
}

class ProfileRoute extends StatelessWidget {
  const ProfileRoute({Key? key}) : super(key: key);
  Widget build(BuildContext context) {
    return Scaffold(
      backgroundColor: Colors.blue[800],
      appBar: AppBar(
        title: Text('User Profile'),
        centerTitle: true,
        backgroundColor: Colors.deepPurple[300],
        elevation: 0.0,
      ),
      body: Padding(
          padding: EdgeInsets.fromLTRB(30.0, 30.0, 30.0, 0.0),
          child: Column(
              crossAxisAlignment: CrossAxisAlignment.start,
              children: <Widget>[
                Center(
                  child: CircleAvatar(
                    backgroundImage: AssetImage('assets/download.jpg'),
                    radius: 50.0,
                  ),
                ),
                Divider(
                  height: 60.0,
                  color: Colors.white,
                ),
                Center(
                  child: Text('NAME',
                      style: TextStyle(
                        color: Colors.blue[300],
                        letterSpacing: 2.0,
                      )),
                ),
                SizedBox(height: 10.0),
                Center(
                  child: Text('Jane Doe',
                      style: TextStyle(
                        color: Colors.white,
                        letterSpacing: 2.0,
                        fontSize: 28.0,
                        fontWeight: FontWeight.bold,
                      )),
                ),
                SizedBox(height: 30.0),
                Center(
                  child: Text('NOTES',
                      style: TextStyle(
                        color: Colors.blue[300],
                        letterSpacing: 2.0,
                      )),
                ),
                SizedBox(height: 10.0),
                Center(
                  child: Text('...',
                      style: TextStyle(
                        color: Colors.white,
                        letterSpacing: 2.0,
                        fontSize: 28.0,
                        fontWeight: FontWeight.bold,
                      )),
                ),
                SizedBox(height: 10.0),
                Center(
                  child: Row(
                    children: <Widget>[
                      Icon(
                        Icons.email,
                        color: Colors.blue[300],
                      ),
                      Text('  jad284@gmail.com',
                          style: TextStyle(
                            color: Colors.blue[300],
                            letterSpacing: 1.0,
                            fontSize: 18.0,
                          )),
                    ],
                  ),
                )
              ])),
    );
  }
}

class HomePageRoute extends StatelessWidget {
  const HomePageRoute({Key? key}) : super(key: key);

  get futureAlbum => null;

  get navigatorKey => null;
  Widget build(BuildContext context) {
    debugShowCheckedModeBanner:
    false;
    navigatorKey:
    navigatorKey;
    home:
    Scaffold(
      appBar: AppBar(
        leading: ElevatedButton.icon(
          icon: const Icon(Icons.post_add_outlined, size: 18),
          label: Text("Post"),
          onPressed: () {},
        ),
        backgroundColor: Colors.deepPurple.shade300,
        title: Text('The Buzz'),
        centerTitle: true,
        actions: <Widget>[
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
          PopupMenuButton(
            itemBuilder: (context) {
              onSelected:
              (value) {
                if (value == '/user profile') {
                  Navigator.push(
                      context,
                      MaterialPageRoute(
                          builder: (context) => const ProfileRoute()));
                } else if (value == '/log out') {
                  Navigator.push(
                      context,
                      MaterialPageRoute(
                          builder: (context) => const LogInRoute()));
                }
              };
              return [
                PopupMenuItem(
                  value: '/user profile',
                  child: Text("User Profile"),
                ),
                PopupMenuItem(
                  value: 2,
                  child: Text("Page 2 coming soon"),
                ),
                PopupMenuItem(
                  value: '/log out',
                  child: Text("Log Out"),
                ),
              ];
            },
          ),
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
    );
    throw Exception("homepage not found");
  }
}

class LogInRoute extends StatelessWidget {
  const LogInRoute({Key? key}) : super(key: key);
  Widget build(BuildContext context) {
    return Scaffold(
      backgroundColor: Colors.grey[300],
      appBar: AppBar(
        title: Text('Welcome!'),
        centerTitle: true,
        backgroundColor: Colors.deepPurple[300],
        elevation: 0.0,
      ),
      body: Padding(
          padding: EdgeInsets.fromLTRB(30.0, 30.0, 30.0, 0.0),
          child: Column(
              crossAxisAlignment: CrossAxisAlignment.start,
              children: <Widget>[
                Center(
                  child: Text('THE BUZZ',
                      style: TextStyle(
                        color: Colors.deepPurple[300],
                        letterSpacing: 2.0,
                        fontSize: 40.0,
                        fontWeight: FontWeight.bold,
                      )),
                ),
                SizedBox(height: 20.0),
                TextField(
                  decoration: InputDecoration(
                    hintText: "username",
                    hintStyle: TextStyle(
                      color: Colors.deepPurple[300],
                    ),
                    focusedBorder: UnderlineInputBorder(
                      borderSide: BorderSide(color: Colors.grey),
                    ),
                    enabledBorder: UnderlineInputBorder(
                      borderSide: BorderSide(color: Colors.grey),
                    ),
                  ),
                ),
                SizedBox(height: 10.0),
                TextField(
                  decoration: InputDecoration(
                    hintText: "password",
                    hintStyle: TextStyle(
                      color: Colors.deepPurple[300],
                    ),
                    focusedBorder: UnderlineInputBorder(
                      borderSide: BorderSide(color: Colors.blue),
                    ),
                    enabledBorder: UnderlineInputBorder(
                      borderSide: BorderSide(color: Colors.blue),
                    ),
                  ),
                ),
                SizedBox(height: 10.0),
                Center(
                  child: RaisedButton(
                    onPressed: () {},
                    child: Text('Sign Up with Google'),
                    color: Colors.blue[800],
                  ),
                ),
                SizedBox(height: 10.0),
                Center(
                  child: RaisedButton(
                    onPressed: () {},
                    child: Text('Log In with Google'),
                    color: Colors.blue[800],
                  ),
                ),
              ])),
    );
  }
}
