import 'dart:async';
import 'dart:convert';

import 'package:flutter/material.dart';
import 'package:http/http.dart' as http;




void main() {
  runApp(const MyApp());
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
              icon: const Icon(
                Icons.favorite,
              ),
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
              for (int index = 1; index < 21; index++)
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
                    subtitle: Text("T2,$index"),
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
            ],
          ),
        ),
      ),
    );
  }
}

Future<Album> fetchAlbum() async {
  final response = await http
      .get(Uri.parse('https://cse216-group4-app.herokuapp.com/idea/1'));

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

  const Album({
    required this.userId,
  });

  factory Album.fromJson(Map<String, dynamic> json) {
    return Album(
      userId: json['userId'],
    );
  }
}
