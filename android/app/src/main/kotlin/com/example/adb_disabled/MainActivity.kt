package com.example.adb_disabled

import android.os.Build;
import android.provider.Settings;
import androidx.annotation.NonNull;
import io.flutter.embedding.android.FlutterActivity;
import io.flutter.embedding.engine.FlutterEngine;
import io.flutter.plugin.common.MethodChannel;


class MainActivity: FlutterActivity() {
    private val adbChannel = "adb_disable_app/channel"
    override fun configureFlutterEngine(@NonNull flutterEngine: FlutterEngine) {
        super.configureFlutterEngine(flutterEngine)
        MethodChannel(flutterEngine.dartExecutor.binaryMessenger, adbChannel).setMethodCallHandler { call, result ->
            if (call.method == "disableADB") {
                disableADB()
                result.success(null)
            } else {
                result.notImplemented()
            }
        }
    }

    private fun disableADB() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            Settings.Global.putInt(contentResolver, Settings.Global.ADB_ENABLED, 0)
        } else {
            Settings.Secure.putInt(contentResolver, Settings.Secure.ADB_ENABLED, 0)
        }
    }
}
