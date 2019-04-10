package com.htetwaiyan.flutter_sms_retriever

import android.app.Activity
import android.content.Context
import android.content.IntentFilter
import android.support.v4.content.LocalBroadcastManager
import android.util.Log
import android.widget.Toast
import com.google.android.gms.auth.api.phone.SmsRetriever

import io.flutter.plugin.common.MethodCall
import io.flutter.plugin.common.MethodChannel
import io.flutter.plugin.common.MethodChannel.MethodCallHandler
import io.flutter.plugin.common.MethodChannel.Result
import io.flutter.plugin.common.PluginRegistry.Registrar

class FlutterSmsRetrieverPlugin(var  channel:MethodChannel,var  activity:Activity) : MethodCallHandler, SMSReceiver.OTPReceiveListener {

init {
    channel.setMethodCallHandler(this)
}


  companion object {
    @JvmStatic
    fun registerWith(registrar: Registrar) {
      val channel = MethodChannel(registrar.messenger(), "flutter_sms_retriever")
      channel.setMethodCallHandler(FlutterSmsRetrieverPlugin(channel,registrar.activity()))
    }
  }

  var smsReceiver:SMSReceiver?=null


  override fun onMethodCall(call: MethodCall, result: Result) {
    if (call.method == "getPlatformVersion") {
      result.success("Android ${android.os.Build.VERSION.RELEASE}")
    }else if(call.method=="startListen"){
      val appSignatureHashHelper = AppSignatureHashHelper(activity)
        result.success("Apps Hash Key: "+ appSignatureHashHelper.appSignatures[0])
        startSMSListener()
    }
    else {
      result.notImplemented()
    }
  }



  private fun startSMSListener() {
    try {
      smsReceiver = SMSReceiver()
      smsReceiver!!.setOTPListener(this)

      val intentFilter = IntentFilter()
      intentFilter.addAction(SmsRetriever.SMS_RETRIEVED_ACTION)
      activity.registerReceiver(smsReceiver, intentFilter)

      val client = SmsRetriever.getClient(activity)

      val task = client.startSmsRetriever()
      task.addOnSuccessListener {
        // API successfully started
      }

      task.addOnFailureListener {
        // Fail to start API
      }
    } catch (e: Exception) {
      e.printStackTrace()
    }

  }


  override fun onOTPReceived(otp: String) {
    showToast("OTP Received: $otp")
    Log.d("hello", otp)

    channel.invokeMethod("otpcode",otp)

    if (smsReceiver != null) {
      LocalBroadcastManager.getInstance(activity).unregisterReceiver(smsReceiver)
    }
  }

  override  fun onOTPTimeOut() {
    showToast("OTP Time out")
  }

  override  fun onOTPReceivedError(error: String) {
    showToast(error)
  }


   fun onDispose() {
    if (smsReceiver != null) {
      LocalBroadcastManager.getInstance(activity).unregisterReceiver(smsReceiver)
    }
  }


  private fun showToast(msg: String) {

  }



}
