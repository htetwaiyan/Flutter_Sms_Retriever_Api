package com.htetwaiyan.flutter_sms_retriever


import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent

import android.util.Log
import com.google.android.gms.auth.api.phone.SmsRetriever
import com.google.android.gms.common.api.CommonStatusCodes
import com.google.android.gms.common.api.Status


class SMSReceiver : BroadcastReceiver() {

    private var otpListener: OTPReceiveListener? = null

    fun setOTPListener(otpListener: OTPReceiveListener) {
        this.otpListener = otpListener
    }


    /**
     * @param context
     * @param intent
     */
    override fun onReceive(context: Context, intent: Intent) {
        Log.d("hello", "receiver")
        if (SmsRetriever.SMS_RETRIEVED_ACTION == intent.action) {
            val extras = intent.extras
            val status = extras!!.get(SmsRetriever.EXTRA_STATUS) as Status
            when (status.statusCode) {
                CommonStatusCodes.SUCCESS -> {

                    Log.d("hello", "success")
                    val message = extras.get(SmsRetriever.EXTRA_SMS_MESSAGE) as String

                    /*<#> Your ExampleApp code is: 123ABC78
                    FA+9qCX9VSu*/

                    //Extract the OTP code and send to the listener

                    if (otpListener != null) {
                        otpListener!!.onOTPReceived(message)
                    }
                }
                CommonStatusCodes.TIMEOUT -> {

                    Log.d("hello", "Time out")
                    if (otpListener != null) {
                        otpListener!!.onOTPTimeOut()
                    }
                }

                CommonStatusCodes.API_NOT_CONNECTED -> {
                    Log.d("hello", "connected")
                    if (otpListener != null) {
                        otpListener!!.onOTPReceivedError("API NOT CONNECTED")
                    }
                }

                CommonStatusCodes.NETWORK_ERROR -> {
                    Log.d("hello", "erro")
                    if (otpListener != null) {
                        otpListener!!.onOTPReceivedError("NETWORK ERROR")
                    }
                }

                CommonStatusCodes.ERROR -> {
                    Log.d("hello", "error")

                    if (otpListener != null) {
                        otpListener!!.onOTPReceivedError("SOME THING WENT WRONG")
                    }
                }
            }
        }
    }

    /**
     *
     */
    interface OTPReceiveListener {

        fun onOTPReceived(otp: String)

        fun onOTPTimeOut()

        fun onOTPReceivedError(error: String)
    }
}