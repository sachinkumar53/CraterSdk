package com.crater.android.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import com.google.android.gms.auth.api.phone.SmsRetriever
import com.google.android.gms.common.api.CommonStatusCodes
import com.google.android.gms.common.api.Status
import java.util.regex.Pattern


class OTPBroadcastReceiver : BroadcastReceiver() {
    private var otpReceiveListener: OTPReceiveListener? = null

    fun setOTPReceiveListener(otpReceiveListener: OTPReceiveListener?) {
        this.otpReceiveListener = otpReceiveListener
    }

    override fun onReceive(context: Context, intent: Intent) {
        if (SmsRetriever.SMS_RETRIEVED_ACTION == intent.action) {
            val extras = intent.extras
            if (extras != null) {
                val status = extras[SmsRetriever.EXTRA_STATUS] as? Status
                when (status?.statusCode) {
                    CommonStatusCodes.SUCCESS -> {
                        // Get SMS message contents
                        val message = extras[SmsRetriever.EXTRA_SMS_MESSAGE] as String?
                        val otp = parseOTP(message)
//                        Log.w("Hello", "onReceive: $otp")
                        otpReceiveListener?.onOTPReceived(otp)
                    }
                    CommonStatusCodes.TIMEOUT -> Log.w("Hello", "onReceive: OTP Timeout")
                }
            }
        }

    }

    /**
     * Extracts OTP from the message
     * @param  message - Received message
     * @return OTP
     */
    private fun parseOTP(message: String?): String? {
        message ?: return null
        val pattern = Pattern.compile("(\\d{6})")
        val matcher = pattern.matcher(message)
        return if (matcher.find()) {
            matcher.group(0)
        } else null
    }

    fun interface OTPReceiveListener {
        fun onOTPReceived(otp: String?)
    }
}