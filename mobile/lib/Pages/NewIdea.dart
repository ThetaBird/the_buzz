import 'dart:convert';
import 'dart:io';
import 'dart:typed_data';
import 'package:flutter/material.dart';
import 'package:http/http.dart' as http;
import 'package:provider/provider.dart';
import 'package:image_picker/image_picker.dart';

import '../Components/Data/GlobalState.dart';

class NewIdea extends StatelessWidget{
  const NewIdea({Key? key}) : super(key: key);

  @override
  // ignore: slash_for_doc_comments
  Widget build(BuildContext context){
    final globalState = Provider.of<GlobalStateService>(context).state;

    final ButtonStyle buttonStyle = ElevatedButton.styleFrom(primary: Color.fromARGB(255, 28, 28, 28),textStyle: const TextStyle(fontSize: 14,fontWeight:FontWeight.bold),onPrimary: Colors.white);
    String subject = "";
    String content = "";
    // ignore: avoid_init_to_null
    String? fileBytes = null;
    
    //if(idea.comments != null){comments = idea.comments as List<IdeaData>;}
    //test(globalState.userToken);
    void submitNewIdea() async {
      http.Response res = await http.post(
        Uri.parse('https://cse216-group4-test.herokuapp.com/api/ideas?token=${globalState.userToken}'),
        headers: <String, String>{
          'Content-Type': 'application/json; charset=UTF-8',
        },
        body: jsonEncode(<String, dynamic>{
          'replyTo':0,
          'subject': subject,
          'content': content,
          'attachment':fileBytes,
          'allowedRoles':[]

        }),
      );

      context.read<GlobalStateService>().setNewIdea(false);
    }

    //methods for selecting image from gallery or camera
    //set imageFile to file 
    //File? imageFile = file from camera/gallery;
    //Uint8List imgbytes = await imagefile.readAsBytes();
    //fileBytes = base64.encode(imgbytes);
    final ImagePicker _picker = ImagePicker();
    void _getFromGallery() async {
      final XFile? image = await _picker.pickImage(source: ImageSource.gallery);
      File? imageFile = File(image!.path);
      Uint8List imgbytes = await imageFile.readAsBytes();
      fileBytes = base64.encode(imgbytes);
      print(fileBytes);
    }

    void _getFromCamera() async {
      //final ImagePicker _picker = ImagePicker();
      // Pick an image
      final XFile? image = await _picker.pickImage(source: ImageSource.camera);
      File? imageFile = File(image!.path);
      Uint8List imgbytes = await imageFile.readAsBytes();
      fileBytes = base64.encode(imgbytes);
      print(fileBytes);
    }

    return Scaffold(
      appBar: AppBar(
        backgroundColor: const Color.fromARGB(255, 28, 28, 28),
        title: const Text('New Idea'),
        leading: GestureDetector(
          onTap: (){context.read<GlobalStateService>().setNewIdea(false);},
          child: const Icon(Icons.arrow_back_ios_rounded),
        ),
      ),
      body: SizedBox.expand(
          child: Padding(
            padding: const EdgeInsets.symmetric(horizontal: 12, vertical: 50),
            child: ListView(
              children: [
                TextField(
                  decoration: const InputDecoration(
                    border: UnderlineInputBorder(),
                    labelText: 'Subject',
                  ),
                  onChanged: (String value) => {subject = value},
                ),
                const SizedBox(height: 50),
                TextField(    
                  decoration: const InputDecoration(
                    isDense: true,                      // Added this
                    contentPadding: EdgeInsets.only(bottom:300),
                    border: OutlineInputBorder(),
                    labelText: 'Content',
                  ),
                  onChanged: (String value) => {content = value},
                ),
                ElevatedButton(
                  onPressed: () {
                    _getFromGallery();
                  },
                  // ignore: prefer_const_constructors
                  child: Text('Gallery'),
                  style: buttonStyle,
                ),
                ElevatedButton(
                  onPressed: () {
                    _getFromCamera();
                  },
                  // ignore: prefer_const_constructors
                  child: Text('Camera'),
                  style: buttonStyle,
                ),
  
                ElevatedButton(onPressed: submitNewIdea, child: const Text('Submit'), style: buttonStyle,)
              ]
            ),
          ),
        )
    );
  }
}