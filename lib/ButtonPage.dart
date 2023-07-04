import 'package:flutter/material.dart';
import 'package:flutter/services.dart';

class ButtonPage extends StatefulWidget {
  const ButtonPage({super.key});

  @override
  State<ButtonPage> createState() => _ButtonPageState();
}

class _ButtonPageState extends State<ButtonPage> {
  static const MethodChannel _channel = MethodChannel('adb_disable_app');

  static Future<void> disableUSBFileTransfer() async {
    try {
      await _channel.invokeMethod('disableUSBFileTransfer');
    } catch (e) {
      print('Failed to disable USB file transfer: $e');
    }
  }

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
            ElevatedButton(onPressed: () {}, child: Text("Enable")),
            SizedBox(
              height: 20,
            ),
            ElevatedButton(
                onPressed: disableUSBFileTransfer, child: Text("Disabled"))
          ],
        ),
      ),
    );
  }
}
