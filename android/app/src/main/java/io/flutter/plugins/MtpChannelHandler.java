package com.example.<your_package_name>;

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

    public static void registerWith(io.flutter.embedding.engine.plugins.FlutterPluginRegistry.Registrar registrar) {
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
        // Disable MTP by changing the MTP_ENABLED setting
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.JELLY_BEAN_MR1) {
            Settings.Global.putInt(context.getContentResolver(), Settings.Global.MTP_ENABLED, 0);
        } else {
            Settings.System.putInt(context.getContentResolver(), Settings.System.MTP_ENABLED, 0);
        }

        // Send a broadcast to notify the system about the MTP setting change
        Intent intent = new Intent(UsbManager.ACTION_USB_STATE);
        intent.putExtra(UsbManager.EXTRA_USB_CONNECTED, false);
        intent.putExtra(UsbManager.EXTRA_USB_FUNCTION, UsbManager.USB_FUNCTION_MTP);
        context.sendBroadcast(intent);
    }
}
