import 'package:flutter/material.dart';

class ProfileRoute extends StatelessWidget {
  final logoutAction;
  final String name;
  final String picture;

  ProfileRoute(this.logoutAction, this.name, this.picture);

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
