import 'package:adb_disabled/ButtonPage.dart';
import 'package:flutter/material.dart';
import 'package:flutter/material.dart';
import 'package:flutter/services.dart';
import 'package:root/root.dart';

void main() {
  runApp(const MyApp());
}

class MyApp extends StatelessWidget {
  const MyApp({super.key});

  // This widget is the root of your application.
  @override
  Widget build(BuildContext context) {
    return MaterialApp(
        debugShowCheckedModeBanner: false,
        title: 'MTP disabler',
        theme: ThemeData(
          colorScheme: ColorScheme.fromSeed(seedColor: Colors.deepPurple),
          useMaterial3: true,
        ),
        home: ButtonPage());
  }
}

class ButtonPage extends StatefulWidget {
  const ButtonPage({super.key});

  @override
  State<ButtonPage> createState() => _ButtonPageState();
}

class _ButtonPageState extends State<ButtonPage> {
  String? _result;
  static const MethodChannel _channel = MethodChannel('adb_disable_app');
//!
  static Future<void> disableUSBFileTransfer() async {
    try {
      await _channel.invokeMethod('disableUSBFileTransfer');
    } catch (e) {
      print('Failed to disable USB file transfer: $e');
    }
  }

//!
  void enableUSBFileTransfer() async {
    try {
      await MethodChannel('adb_disable_app')
          .invokeMethod('enableUSBFileTransfer');
      print('USB file transfer enabled successfully.');
    } catch (e) {
      print('Failed to enable USB file transfer: $e');
    }
  }

//!
  Future<void> disableFileTransfer() async {
    try {
      await _channel.invokeMethod('disableFileTransfer');
      print('File transfer disabled.');
    } catch (e) {
      print('Failed to disable file transfer: $e');
    }
  }

//!
  Future<void> setCommand() async {
    String? res = await Root.exec(
        cmd:
            "adb shell pm grant com.example.adb_disabled android.permission.WRITE_SECURE_SETTINGS");
    setState(() {
      _result = res;
    });
  }

//!
  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: Text("ADB Disabler"),
        centerTitle: true,
      ),
      body: Center(
        child: Column(
          mainAxisAlignment: MainAxisAlignment.center,
          children: [
            ElevatedButton(
                onPressed: () {
                  setCommand();
                  enableUSBFileTransfer();
                },
                child: Text("Enable")),
            SizedBox(
              height: 20,
            ),
            ElevatedButton(
                onPressed: () {
                  setCommand();
                  print("aaaaaaaaaaaaaaaaaaaaaaaaaaa$_result <=");
                  disableUSBFileTransfer();
                  disableFileTransfer();
                },
                child: Text("Disabled"))
          ],
        ),
      ),
    );
  }
}
