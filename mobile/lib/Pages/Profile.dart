import 'package:flutter/material.dart';
import 'package:provider/provider.dart';
import 'package:thebuzz/Components/Data/ProfileData.dart';
import '../Components/Data/GlobalState.dart';
import 'package:http/http.dart' as http;
import 'dart:convert';

class Profile extends StatefulWidget{
  const Profile({Key? key, required this.profile}) : super(key: key);
  final ProfileData profile;

  @override
  State<Profile> createState() => _ProfileState(profile);
}
class _ProfileState extends State<Profile>{
  _ProfileState(this.profile);

  ProfileData profile;
  String note = "";
  String? token;
  bool shouldUpdate = true;

  void test(String? token) async{
    ProfileData profileDataSpecific;
    http.Response res = await http.get(
      Uri.parse('https://cse216-group4-test.herokuapp.com/api/user/${profile.userId}/user?token=${token}'),
      headers: <String, String>{'Content-Type': 'application/json; charset=UTF-8',},
    );
    Map<String, dynamic> response = jsonDecode(res.body);
    dynamic data = response['mData'];
    if(data == null){print("No data");}
    profileDataSpecific = ProfileData.fromJson(data);

    if(shouldUpdate){
      setState(() {
        shouldUpdate = false;
        profile = profileDataSpecific;
      });
    }
  }


  @override
  Widget build(BuildContext context){
    final globalState = Provider.of<GlobalStateService>(context).state;
    const TextStyle text1Style = TextStyle(fontSize: 14);
    const TextStyle text2Style = TextStyle(fontSize: 32);
    //print(globalState.userAvatar);
    void editNote(){
      /**
       * Pass modified note to backend on submission
       */
      
    }
    bool isUser = (profile.name == globalState.userName);
    return Scaffold(
      appBar: AppBar(
        backgroundColor: Color.fromARGB(255, 28, 28, 28),
        title: const Text('Profile'),
        leading: GestureDetector(
          onTap: (){context.read<GlobalStateService>().setProfile(null);},
          child: const Icon(Icons.arrow_back_ios_rounded),
        ),
      ),
      body: SizedBox.expand(
          child: Padding(
            padding: const EdgeInsets.symmetric(horizontal: 12, vertical: 30),
            child: Column(
              children: [
                Image(image: NetworkImage(profile.avatar),),
                // ignore: prefer_const_constructors
                SizedBox(height: 25,),
                Text(profile.name, style:text2Style),
                Text(profile.email, style:text1Style),
                  // ignore: prefer_const_constructors
                  //padding: EdgeInsets.all(10.0),
              ],
            )
          ),
        ),
    );
  }
}


/*
                  autocorrect: true,  
                  decoration: const InputDecoration(
                    border: OutlineInputBorder(),
                    labelText: 'Profile Note',
                  ),
                  onChanged: (String value) => {note = value},
                )*/