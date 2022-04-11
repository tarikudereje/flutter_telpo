package com.efikas.telpo


//import android.content.IntentFilter;
//import android.graphics.Bitmap;
//import android.graphics.BitmapFactory;

//import com.telpo.tps550.api.printer.ThermalPrinter;
//import com.telpo.tps550.api.printer.UsbThermalPrinter;
import android.app.Activity
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.BatteryManager
import androidx.annotation.NonNull
//import com.telpo.tps550.api.util.StringUtil
//import com.telpo.tps550.api.util.SystemUtil
import io.flutter.embedding.engine.plugins.FlutterPlugin
import io.flutter.embedding.engine.plugins.FlutterPlugin.FlutterPluginBinding
import io.flutter.embedding.engine.plugins.activity.ActivityAware
import io.flutter.embedding.engine.plugins.activity.ActivityPluginBinding
import io.flutter.plugin.common.MethodCall
import io.flutter.plugin.common.MethodChannel
import io.flutter.plugin.common.MethodChannel.Result
import io.flutter.plugin.common.MethodChannel.MethodCallHandler
import io.flutter.plugin.common.PluginRegistry.Registrar
import java.util.*

/** FlutterTelpoPlugin */
class FlutterTelpoPlugin: FlutterPlugin, MethodCallHandler, ActivityAware {
  public lateinit var channel : MethodChannel
  private val channelId = "com.efikas.app/printer";
 private lateinit var usbPrinter: USBPrinter;
//   private lateinit var thermalPrinter: ThermalPrinterClass;
  private var LowBattery = false
  private var _isConnected = false;
  private var documentDetails: MutableList<Map<String, Any>> = ArrayList();
    public lateinit var context: Context;
    public lateinit var activity: Activity;
    public lateinit var registrar: Registrar;
    public lateinit var binding: FlutterPluginBinding;


  override fun onAttachedToEngine(@NonNull flutterPluginBinding: FlutterPlugin.FlutterPluginBinding) {
    channel = MethodChannel(flutterPluginBinding.binaryMessenger, channelId);
    channel.setMethodCallHandler(this);
      binding = flutterPluginBinding;
      context = flutterPluginBinding.applicationContext;
     usbPrinter = USBPrinter(this@FlutterTelpoPlugin);
    //   thermalPrinter = ThermalPrinterClass(this@FlutterTelpoPlugin);

  }

    companion object {
        @JvmStatic
        fun registerWith(registrar: Registrar) {
            val channel = MethodChannel(registrar.messenger(), FlutterTelpoPlugin().channelId)
            val plugin = FlutterTelpoPlugin();
            channel.setMethodCallHandler(plugin);
            FlutterTelpoPlugin().registrar = registrar;
        }
    }

 override fun onMethodCall(@NonNull call: MethodCall, @NonNull result: Result) {
    if (call.method == "connect") {
        if (!_isConnected) {
            context.registerReceiver(printReceive, IntentFilter());
            _isConnected = true;
        }
    } else if (call.method == "isConnected") {
        result.success(_isConnected);
    } else if (call.method == "printDocument") {
        documentDetails = call.argument("details") ?: ArrayList();
       usbPrinter.printText(documentDetails);
        // thermalPrinter.printText(documentDetails);
    } else if (call.method == "disconnect") {
        if (_isConnected) {
            context.unregisterReceiver(printReceive);
            _isConnected = false;
        }
    }
    else {
        result.notImplemented()
    }
  }

  override fun onDetachedFromEngine(@NonNull binding: FlutterPlugin.FlutterPluginBinding) {
    if (_isConnected) {
        context.unregisterReceiver(printReceive)
    }
    channel.setMethodCallHandler(null);
    //   thermalPrinter.stop();
  }

    override fun onDetachedFromActivity() {
    }

    override fun onReattachedToActivityForConfigChanges(binding: ActivityPluginBinding) {

    }

    override fun onAttachedToActivity(binding: ActivityPluginBinding) {
        activity = binding.activity;
    }

    override fun onDetachedFromActivityForConfigChanges() {
    }

  private val printReceive: BroadcastReceiver = object : BroadcastReceiver() {
      override fun onReceive(context: Context, intent: Intent) {
          val action: String? = intent.getAction()
          if (action == Intent.ACTION_BATTERY_CHANGED) {
              val status: Int = intent.getIntExtra(BatteryManager.EXTRA_STATUS, BatteryManager.BATTERY_STATUS_NOT_CHARGING)
              val level: Int = intent.getIntExtra(BatteryManager.EXTRA_LEVEL, 0)
              val scale: Int = intent.getIntExtra(BatteryManager.EXTRA_SCALE, 0)
              //TPS390 can not print,while in low battery,whether is charging or not charging
//              LowBattery = if (SystemUtil.getDeviceType() == StringUtil.DeviceModelEnum.TPS390.ordinal) {
//                  if (level * 5 <= scale) {
//                      true
//                  } else {
//                      false
//                  }
//              } else {
                  if (status != BatteryManager.BATTERY_STATUS_CHARGING) {
                      if (level * 5 <= scale) {
                          true
                      } else {
                          false
                      }
                  } else {
                      false
                  }
//              }
          } else if (action == "android.intent.action.BATTERY_CAPACITY_EVENT") {
              val status: Int = intent.getIntExtra("action", 0)
              val level: Int = intent.getIntExtra("level", 0)
              LowBattery = if (status == 0) {
                  if (level < 1) {
                      true
                  } else {
                      false
                  }
              } else {
                  false
              }
          }
      }
  }
}
