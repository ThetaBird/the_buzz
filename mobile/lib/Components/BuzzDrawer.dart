import 'package:flutter/material.dart';
import 'package:provider/provider.dart';
import '../Components/Data/GlobalState.dart';

class BuzzDrawer extends StatelessWidget{
  Widget build(BuildContext context){
    final globalState = Provider.of<GlobalStateService>(context).state;

    const TextStyle buzzStyle = TextStyle(color: Colors.white,fontSize: 30);
    const TextStyle text1Style = TextStyle(color: Colors.white,fontSize: 22);
    const TextStyle text2Style = TextStyle(color: Colors.white,fontSize: 24);
    final ButtonStyle buttonStyle = ElevatedButton.styleFrom(primary: const Color.fromARGB(255, 28, 28, 28),textStyle: const TextStyle(fontSize: 14,fontWeight:FontWeight.bold),onPrimary: Colors.white);
    void logout(){
      context.read<GlobalStateService>().logout();
    }
    return  Drawer(
        child: Column(
          children: [
            DrawerHeader(
              decoration: const BoxDecoration(
                color: Color.fromARGB(255, 28, 28, 28),
              ),
              child: Column(
                children: [
                  const SizedBox(height: 10),
                  const SizedBox(width:300,height: 60,child: Text('The Buzz',style:buzzStyle,)),
                  const SizedBox(width:300,child: Text('Welcome,',style:text1Style,)),
                  SizedBox(width:300,child: Text(globalState.userName.split(" ")[0],style:text2Style,)),
                ],
              ),
            ),
            ListTile(
              title: const Text('Home'),
              onTap: () {
                Navigator.pop(context);
              },
            ),
            ListTile(
              title: const Text('Profile'),
              onTap: () {
                context.read<GlobalStateService>().setProfile(true);
                Navigator.pop(context);
              },
            ),
            ListTile(
              title: const Text('Employees'),
              onTap: () {
                Navigator.pop(context);
              },
            ),
            Expanded(child: SizedBox()),
            Padding(
              padding: const EdgeInsets.all(8.0),
              child: Align(alignment: Alignment.bottomLeft,child: ElevatedButton(onPressed: logout, child: const Text('Logout'), style: buttonStyle,)),
            )
          ],
        ),
      );
    
  }
}