package com.crater.android.feature.expense.data.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.provider.Telephony
import com.crater.android.feature.expense.data.dao.TransactionDao
import com.crater.android.feature.expense.data.sms.filter.SmsRecognizer
import com.crater.android.feature.expense.data.sms.merchant.parseSms
import com.crater.android.feature.expense.domain.model.LocalSms
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class SmsReceiver : BroadcastReceiver() {
    @Inject
    lateinit var recognizer: SmsRecognizer

    @Inject
    lateinit var transactionDao: TransactionDao

    override fun onReceive(context: Context?, intent: Intent?) {
        if (intent != null && intent.action == ACTION_SMS_RECEIVED) {
            val message = Telephony.Sms.Intents.getMessagesFromIntent(intent)
            for (msg in message) {
                val body = msg.messageBody
                val sender = msg.originatingAddress ?: return
                val sms = LocalSms(sender = sender, text = body, date = msg.timestampMillis)
                //Log.i(TAG, "onReceive: $sms")
                recognizer.recognize(sms)?.let {
                    val transaction = it.parseSms(sms)
                    //Log.i(TAG, "onReceive: $transaction")
                    CoroutineScope(Dispatchers.IO).launch {
                        transactionDao.insert(transaction)
                    }
                }
            }
        }
    }

    companion object {
        private const val TAG = "SmsReceiver"
        private const val ACTION_SMS_RECEIVED = "android.provider.Telephony.SMS_RECEIVED"
    }

}