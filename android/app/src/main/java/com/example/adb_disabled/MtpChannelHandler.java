package com.example.adb_disabled;

import android.content.Context;
import android.content.Intent;
import android.hardware.usb.UsbManager;
import android.provider.Settings;
import androidx.annotation.NonNull;

import io.flutter.plugin.common.MethodCall;
import io.flutter.plugin.common.MethodChannel;
import io.flutter.plugin.common.PluginRegistry.Registrar;

public class MtpChannelHandler implements MethodChannel.MethodCallHandler {
    private final Context context;
    private final MethodChannel channel;

    private MtpChannelHandler(Context context, MethodChannel channel) {
        this.context = context;
        this.channel = channel;
    }

public static void registerWith(Registrar registrar) {
    final MethodChannel channel = new MethodChannel(registrar.messenger(), "mtp_disable_app");
    channel.setMethodCallHandler(new MtpChannelHandler(registrar.context(), channel));
}


    @Override
    public void onMethodCall(@NonNull MethodCall call, @NonNull MethodChannel.Result result) {
        if (call.method.equals("disableMTP")) {
            disableMTP();
            result.success(null);
        } else {
            result.notImplemented();
        }
    }

 private void disableMTP() {
    // Disable MTP by changing the MTP_DEFAULT
    int mtpDefault = 0;
    Settings.Secure.putInt(context.getContentResolver(), "mtp_default", mtpDefault);

    // Send a broadcast to notify the system about the MTP setting change
    Intent intent = new Intent("android.hardware.usb.action.USB_STATE");
    intent.putExtra("connected", false);
    context.sendBroadcast(intent);
}
}
