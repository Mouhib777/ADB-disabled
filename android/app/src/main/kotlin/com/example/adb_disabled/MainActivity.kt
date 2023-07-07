package com.example.adb_disabled

import android.os.Build
import android.provider.Settings
import io.flutter.embedding.android.FlutterActivity
import io.flutter.embedding.engine.FlutterEngine
import io.flutter.plugin.common.MethodCall
import io.flutter.plugin.common.MethodChannel

import android.Manifest
import android.content.pm.PackageManager
import android.content.Context
import android.widget.Toast
import android.hardware.usb.UsbManager

import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat

class MainActivity : FlutterActivity() {
    private val CHANNEL = "adb_disable_app"
    private val WRITE_SETTINGS_PERMISSION_REQUEST_CODE = 123

    override fun configureFlutterEngine(flutterEngine: FlutterEngine) {
        super.configureFlutterEngine(flutterEngine)
        MethodChannel(flutterEngine.dartExecutor.binaryMessenger, CHANNEL).setMethodCallHandler { call, result ->
           //!
            if (call.method == "disableUSBFileTransfer") {
                val adbEnabled = 0
                try {
                    
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                        Settings.Global.putInt(contentResolver, Settings.Global.ADB_ENABLED, adbEnabled)
                        Settings.Global.putInt(contentResolver, "mtp_enabled", 0)
                    } else {
                        Settings.Secure.putInt(contentResolver, Settings.Secure.ADB_ENABLED, adbEnabled)
                        Settings.Secure.putInt(contentResolver, "mtp_enabled", 0)
                    }
                    Toast.makeText(this, "ADB Disabled", Toast.LENGTH_SHORT).show()
                    result.success(null)
                } catch (e: Exception) {
                    Toast.makeText(this, "Error disabling ADB", Toast.LENGTH_SHORT).show()
                    result.error("DISABLE_FAILURE", e.message, null)
                }
                try {
                    val usbManager = getSystemService(Context.USB_SERVICE) as UsbManager
                    val usbDeviceConnection = usbManager.javaClass.getMethod("getDeviceConnection").invoke(usbManager)
                    val setEnabledFunction = usbDeviceConnection.javaClass.getMethod("setEnabled", Boolean::class.javaPrimitiveType)
                    setEnabledFunction.invoke(usbDeviceConnection, false)
                    Toast.makeText(this, "USB File Transfer Disabled", Toast.LENGTH_SHORT).show()
                    result.success(null)
                } catch (e: Exception) {
                    Toast.makeText(this, "Error disabling USB File Transfer", Toast.LENGTH_SHORT).show()
                    result.error("DISABLE_FAILURE", e.message, null)
                }
            
                
                
                //!
            } else if (call.method == "enableUSBFileTransfer") {
                val adbEnabled = 1
                try {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                        Settings.Global.putInt(contentResolver, Settings.Global.ADB_ENABLED, adbEnabled)
                        Settings.Global.putInt(contentResolver, "mtp_enabled", 1)
                    } else {
                        Settings.Secure.putInt(contentResolver, Settings.Secure.ADB_ENABLED, adbEnabled)
                        Settings.Secure.putInt(contentResolver, "mtp_enabled", 1)
                    }
                    Toast.makeText(this, "ADB Enabled", Toast.LENGTH_SHORT).show()
                    result.success(null)
                } catch (e: Exception) {
                    Toast.makeText(this, "Error enabling ADB", Toast.LENGTH_SHORT).show()
                    result.error("ENABLE_FAILURE", e.message, null)
                }
                try {
                    val usbManager = getSystemService(Context.USB_SERVICE) as UsbManager
                    val usbDeviceConnection = usbManager.javaClass.getMethod("getDeviceConnection").invoke(usbManager)
                    val setEnabledFunction = usbDeviceConnection.javaClass.getMethod("setEnabled", Boolean::class.javaPrimitiveType)
                    setEnabledFunction.invoke(usbDeviceConnection, true)
                    Toast.makeText(this, "USB File Transfer Enabled", Toast.LENGTH_SHORT).show()
                    result.success(null)
                } catch (e: Exception) {
                    Toast.makeText(this, "Error enabling USB File Transfer", Toast.LENGTH_SHORT).show()
                    result.error("ENABLE_FAILURE", e.message, null)
                }
            } else {
                result.notImplemented()
            }
        }

        requestWriteSettingsPermission() // Request permission when the activity is configured
    }

    private fun requestWriteSettingsPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_SECURE_SETTINGS)
                != PackageManager.PERMISSION_GRANTED
            ) {
                ActivityCompat.requestPermissions(
                    this,
                    arrayOf(Manifest.permission.WRITE_SECURE_SETTINGS),
                    WRITE_SETTINGS_PERMISSION_REQUEST_CODE
                )
            }
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == WRITE_SETTINGS_PERMISSION_REQUEST_CODE) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission granted
                Toast.makeText(this, "Permission granted", Toast.LENGTH_SHORT).show()
            } else {
                // Permission denied
                Toast.makeText(this, "Permission denied", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
