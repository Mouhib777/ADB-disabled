import 'package:flutter/material.dart';
import 'package:flutter/services.dart';
import 'package:process_run/cmd_run.dart';

void main() {
  runApp(MyApp());
}

class MyApp extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      debugShowCheckedModeBanner: false,
      title: 'MTP disabler',
      theme: ThemeData(
        primarySwatch: Colors.deepPurple,
      ),
      home: ButtonPage(),
    );
  }
}

class ButtonPage extends StatefulWidget {
  @override
  _ButtonPageState createState() => _ButtonPageState();
}

class _ButtonPageState extends State<ButtonPage> {
  String? _result;

  Future<void> disableUSBFileTransfer() async {
    try {
      await run('adb shell settings put global adb_enabled 0');
      _showToast('USB File Transfer Disabled');
    } catch (e) {
      print('Failed to disable USB file transfer: $e');
      _showToast('Failed to disable USB File Transfer');
    }
  }

  Future<void> enableUSBFileTransfer() async {
    try {
      await run('adb shell settings put global adb_enabled 1');
      _showToast('USB File Transfer Enabled');
    } catch (e) {
      print('Failed to enable USB file transfer: $e');
      _showToast('Failed to enable USB File Transfer');
    }
  }

  void _showToast(String message) {
    ScaffoldMessenger.of(context).showSnackBar(
      SnackBar(content: Text(message)),
    );
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
            ElevatedButton(
              onPressed: disableUSBFileTransfer,
              child: Text("Disable"),
            ),
            SizedBox(height: 20),
            ElevatedButton(
              onPressed: enableUSBFileTransfer,
              child: Text("Enable"),
            ),
          ],
        ),
      ),
    );
  }
}
