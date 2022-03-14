import 'package:flutter/material.dart';
import 'dart:async';

import 'package:flutter/services.dart';
import 'package:flutter_telpo/flutter_telpo.dart';
import 'package:flutter_telpo/model/row.dart';

void main() {
  runApp(MyApp());
}

class MyApp extends StatefulWidget {
  @override
  _MyAppState createState() => _MyAppState();
}

class _MyAppState extends State<MyApp> {
  String _platformVersion = 'Unknown';

  @override
  void initState() {
    super.initState();
  }

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      home: Scaffold(
        appBar: AppBar(
          title: const Text('Plugin example app'),
        ),
        body: Column(
          children: [
            Image.asset("assets/images/icon.png"),
            Center(
              child: Text('Running on: $_platformVersion\n'),
            ),
            ListTile(
              leading: Icon(Icons.bluetooth_searching),
              trailing:
              IconButton(icon: Icon(Icons.print_rounded),),
              title: Text("Default Printer"),
              onTap: (){
                TestPrintables().printThroughUsbPrinter();
              },
            ),
          ],
        ),
      ),
    );
  }
}

class TestPrintables {
  FlutterTelpo _printer = new FlutterTelpo();


  printThroughUsbPrinter() {
    try {
      _printer.connect();
      _printer.isConnected().then((bool isConneted) {
        if (isConneted) {
          List<dynamic> _printables = [];

          _printables.addAll([
            PrintRow(
              text: "BAUCHI STATE INTERNAL REVENUE SERVICE",
              fontSize: 2,
              position: 1,
            ),
            PrintRow(
                text: "*****************************",
                fontSize: 1,
                position: 1),
          ]);
          _printables.add(PrintQRCode(text: "Howdy", height: 200, width: 200, position: 1));
          _printables.addAll([
            PrintRow(text: "TESTING", fontSize: 2, position: 1),
            PrintAssetImage(imagePath: "assets/images/icon.png", height: 200, width: 200, position: 1),
            PrintRow(
                text: "*****************************",
                fontSize: 1,
                position: 0,),
            PrintAssetImage(imagePath: "assets/images/icon.png", height: 200, width: 200, position: 1)
          ]);

          _printables.add(PrintRow(
            text: "Howdy",
            fontSize: 1,
            position: 0,),);

          _printables.add(PrintAssetImage(imagePath: "assets/images/icon.png", height: 200, width: 200, position: 1));
          _printables.add(WalkPaper(step: 10));
          _printer.print(_printables.toList());
        }
      });
    }
    on PlatformException catch (e) {
      print(e);
    }
  }
}

