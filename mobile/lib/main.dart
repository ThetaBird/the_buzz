import 'package:flutter/material.dart';
//import 'package:firebase_core/firebase_core.dart';
import 'package:provider/provider.dart';
import 'package:thebuzz/Components/Data/GlobalState.dart'; 
import 'package:firebase_core/firebase_core.dart';
import 'Pages/Wrapper.dart';
Future main() async{
  WidgetsFlutterBinding.ensureInitialized();
  await Firebase.initializeApp();

  runApp(
    const App()
    );
}

class App extends StatelessWidget {
  const App({Key? key}) : super(key: key);

  // This widget is the root of your application.
  @override
  Widget build(BuildContext context) {
    return MaterialApp(
        title: 'The Buzz',
        theme: ThemeData(
          primarySwatch: Colors.teal,
          //textTheme: GoogleFonts.spartanTextTheme(
          //  Theme.of(context).textTheme
          //)
        ),
        home: const MainWidget(title: 'The Buzz'),
      );
  }
}

class MainWidget extends StatefulWidget {
  const MainWidget({Key? key, required this.title}) : super(key: key);

  // This widget is the home page of your application. It is stateful, meaning
  // that it has a State object (defined below) that contains fields that affect
  // how it looks.

  // This class is the configuration for the state. It holds the values (in this
  // case the title) provided by the parent (in this case the App widget) and
  // used by the build method of the State. Fields in a Widget subclass are
  // always marked "final".

  final String title;

  @override
  State<MainWidget> createState() => _MyHomePageState();
}

class _MyHomePageState extends State<MainWidget> {
  int _counter = 0;
  String token = "";

  void incrementToken(String tok){
    setState(() {
      token = tok;
    });
  }
  void _incrementCounter() {
    setState(() {
      // This call to setState tells the Flutter framework that something has
      // changed in this State, which causes it to rerun the build method below
      // so that the display can reflect the updated values. If we changed
      // _counter without calling setState(), then the build method would not be
      // called again, and so nothing would appear to happen.
      _counter++;
    });
  }

  @override
  Widget build(BuildContext context) {
    print("build");
    return MultiProvider(
      providers: [
        ChangeNotifierProvider(create:(_) => GlobalStateService()),
        ChangeNotifierProvider(create:(_) => IdeaListStateService()),
      ],
      child: Wrapper()
    );
  }
}
