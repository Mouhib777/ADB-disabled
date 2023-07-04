package com.example.adb_disabled;

import android.os.Bundle;
import android.provider.Settings;
import androidx.annotation.NonNull;
import io.flutter.embedding.android.FlutterActivity;
import io.flutter.embedding.engine.FlutterEngine;
import io.flutter.plugin.common.MethodCall;
import io.flutter.plugin.common.MethodChannel;
import io.flutter.plugins.GeneratedPluginRegistrant;

public class MainActivity extends FlutterActivity {

    private static final String CHANNEL = "adb_disable_app";

    @Override
    public void configureFlutterEngine(@NonNull FlutterEngine flutterEngine) {
        super.configureFlutterEngine(flutterEngine);
        GeneratedPluginRegistrant.registerWith(flutterEngine);

        new MethodChannel(flutterEngine.getDartExecutor().getBinaryMessenger(), CHANNEL).setMethodCallHandler(
                new MethodChannel.MethodCallHandler() {
                    @Override
                    public void onMethodCall(@NonNull MethodCall call, @NonNull MethodChannel.Result result) {
                        if (call.method.equals("disableFileTransfer")) {
                            try {
                                Settings.Global.putInt(getContentResolver(), Settings.Global.MTP_DISABLED, 1);
                                result.success(null);
                            } catch (Exception e) {
                                result.error("DISABLE_FAILURE", e.getMessage(), null);
                            }
                        } else {
                            result.notImplemented();
                        }
                    }
                }
        );
    }
}
