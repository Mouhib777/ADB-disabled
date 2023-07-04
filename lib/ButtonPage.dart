import 'package:flutter/material.dart';
import 'package:flutter/services.dart';

class ButtonPage extends StatefulWidget {
  const ButtonPage({super.key});

  @override
  State<ButtonPage> createState() => _ButtonPageState();
}

class _ButtonPageState extends State<ButtonPage> {
  static const adbChannel = MethodChannel('adb_disable_app/channel');
  void disableADB() async {
    try {
      await adbChannel.invokeMethod('disableADB');
    } on PlatformException catch (e) {
      print('Failed to disable ADB: ${e.message}');
    }
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: Text("ADB Disabled"),
        centerTitle: true,
      ),
      body: Center(
        child: Column(
          mainAxisAlignment: MainAxisAlignment.center,
          children: [
            ElevatedButton(onPressed: () {}, child: Text("Enable")),
            SizedBox(
              height: 20,
            ),
            ElevatedButton(onPressed: disableADB, child: Text("Disabled"))
          ],
        ),
      ),
    );
  }
}
