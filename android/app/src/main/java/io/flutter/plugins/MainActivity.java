package com.example.adb_disabled ; 
import android.os.Bundle;

import io.flutter.app.FlutterActivity;
import io.flutter.plugins.GeneratedPluginRegistrant;

public class MainActivity extends FlutterActivity {
    @Override
public void configureFlutterEngine(@NonNull FlutterEngine flutterEngine) {
    super.configureFlutterEngine(flutterEngine);
    MtpChannelHandler.registerWith(flutterEngine.getDartExecutor().getBinaryMessenger());
}

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        GeneratedPluginRegistrant.registerWith(this);
        MtpChannelHandler channelHandler = new MtpChannelHandler();
           channelHandler.onAttachedToEngine(this);

    }
}
