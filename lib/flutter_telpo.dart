
import 'dart:async';
import 'package:flutter/services.dart';
import 'package:flutter_telpo/model/row.dart';

export 'package:flutter_telpo/model/row.dart';

class FlutterTelpo {
  late MethodChannel _platform;

  FlutterTelpo(){
    _platform = MethodChannel("com.efikas.app/printer");
  }

  Future<bool?> isConnected() {
    return _platform.invokeMethod('isConnected');
  }

  void print(List<dynamic> _rows) async{
    List<Map<String, dynamic>?> _printableRows = [];

    _rows.forEach((dynamic _row){
      if(_row.runtimeType == PrintRow || _row.runtimeType == PrintQRCode || _row.runtimeType == WalkPaper || _row.runtimeType == PrintAssetImage){
        _printableRows.add(_row.toJson());
      }
    });

    await _platform.invokeMethod('printDocument', {"details": _printableRows});
  }

  void printNewLine() async{
    await _platform.invokeMethod('printNewLine');
  }

  void printCustom(String text, int fontSize, int position) async{
    await _platform.invokeMethod('printCustom', {"text": text, "fontSize": fontSize, "position": position});
  }

  void printQRCode(String text, int height, int width, int position) async {
    await _platform.invokeMethod('printQRCode', {"text": text, "height": height, "width": width});
  }

  void paperWalk(int length) async {
    await _platform.invokeMethod('walkPaper', {"length": length});
  }


  void disconnect() async{
    await _platform.invokeMethod('disconnect');
  }

  void connect() async {
    await _platform.invokeMethod('connect');
  }
}