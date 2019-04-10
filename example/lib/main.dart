import 'package:flutter/material.dart';
import 'dart:async';

import 'package:flutter/services.dart';
import 'package:flutter_sms_retriever/flutter_sms_retriever.dart';

void main() => runApp(MyApp());

class MyApp extends StatefulWidget {
  @override
  _MyAppState createState() => _MyAppState();
}

class _MyAppState extends State<MyApp> {
  String _hashKey = 'Unknown';

  @override
  void initState() {
    super.initState();
    initPlatformState();
    initCallBackHandler();
  }

  void initCallBackHandler(){
     FlutterSmsRetriever.setMethodCallHandler((MethodCall call){
       print(call.method);
      if(call.method=="otpcode"){
        setState(() {
          otp=call.arguments.toString();
        });
      }
    });
  }

  Future<void> initPlatformState() async {
    String hashKey;


    try {
      await FlutterSmsRetriever.startListen.then((str){
        print(str+"hey");
        hashKey =str;
      });
    } on PlatformException {
      hashKey = 'Failed to get hash key.';
    }


    if (!mounted) return;

    setState(() {
      _hashKey = _hashKey;
    });
  }
  String otp="";

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      home: Scaffold(
        appBar: AppBar(
          title: const Text('Hash code'),
        ),
        body: Column(
          children: <Widget>[
            Text('Hash code is: $_hashKey\n'),
            Text(otp),
          ],
        ),
      ),
    );
  }
}
