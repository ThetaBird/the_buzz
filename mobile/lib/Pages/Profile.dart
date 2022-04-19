import 'package:flutter/material.dart';
import 'package:provider/provider.dart';
import '../Components/Data/GlobalState.dart';

class Profile extends StatelessWidget{
  @override
  Widget build(BuildContext context){
    final globalState = Provider.of<GlobalStateService>(context).state;
    const TextStyle text1Style = TextStyle(fontSize: 14);
    const TextStyle text2Style = TextStyle(fontSize: 32);
    //print(globalState.userAvatar);
    return Scaffold(
      appBar: AppBar(
        backgroundColor: Color.fromARGB(255, 28, 28, 28),
        title: const Text('Profile'),
        leading: GestureDetector(
          onTap: (){context.read<GlobalStateService>().setProfile(false);},
          child: const Icon(Icons.arrow_back_ios_rounded),
        ),
      ),
      body: SizedBox.expand(
          child: Padding(
            padding: const EdgeInsets.symmetric(horizontal: 12, vertical: 50),
            child: Column(
              children: [
                Image(image: NetworkImage(globalState.userAvatar),),
                SizedBox(height: 25,),
                Text(globalState.userName, style:text2Style),
                Text(globalState.userEmail, style:text1Style)
              ],
            )
          ),
        ),
    );
  }
}