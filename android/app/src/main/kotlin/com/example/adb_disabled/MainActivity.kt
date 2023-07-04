package com.example.adb_disabled

import android.os.Build
import android.provider.Settings
import io.flutter.embedding.android.FlutterActivity
import io.flutter.embedding.engine.FlutterEngine
import io.flutter.plugin.common.MethodChannel

class MainActivity : FlutterActivity() {
    private val CHANNEL = "adb_disable_app"

    override fun configureFlutterEngine(flutterEngine: FlutterEngine) {
        super.configureFlutterEngine(flutterEngine)
        MethodChannel(flutterEngine.dartExecutor.binaryMessenger, CHANNEL).setMethodCallHandler { call, result ->
            if (call.method == "disableUSBFileTransfer") {
                val adbEnabled = 0
                try {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                        Settings.Global.putInt(contentResolver, Settings.Global.ADB_ENABLED, adbEnabled)
                    } else {
                        Settings.Secure.putInt(contentResolver, Settings.Secure.ADB_ENABLED, adbEnabled)
                    }
                    result.success(null)
                } catch (e: Exception) {
                    result.error("DISABLE_FAILURE", e.message, null)
                }
            } else if (call.method == "enableUSBFileTransfer") {
                val adbEnabled = 1
                try {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                        Settings.Global.putInt(contentResolver, Settings.Global.ADB_ENABLED, adbEnabled)
                    } else {
                        Settings.Secure.putInt(contentResolver, Settings.Secure.ADB_ENABLED, adbEnabled)
                    }
                    result.success(null)
                } catch (e: Exception) {
                    result.error("ENABLE_FAILURE", e.message, null)
                }
            } else {
                result.notImplemented()
            }
        }
    }
}
