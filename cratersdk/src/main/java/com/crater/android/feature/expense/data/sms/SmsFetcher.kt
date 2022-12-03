package com.crater.android.feature.expense.data.sms

import android.content.Context
import android.provider.Telephony
import androidx.core.database.getLongOrNull
import androidx.core.database.getStringOrNull
import com.crater.android.feature.expense.data.entity.TransactionDetailEntity
import com.crater.android.feature.expense.data.sms.filter.SmsRecognizer
import com.crater.android.feature.expense.domain.model.LocalSms
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class SmsFetcher(
    private val context: Context,
    private val recognizer: SmsRecognizer
) {

    suspend fun fetchSms() = withContext(Dispatchers.IO) {
        val contentResolver = context.contentResolver
        contentResolver.query(
            Telephony.Sms.Inbox.CONTENT_URI,
            arrayOf(
                Telephony.Sms.Inbox.BODY,
                Telephony.Sms.Inbox.ADDRESS,
                Telephony.Sms.Inbox.DATE
            ),
            null,
            null,
            Telephony.Sms.Inbox.DATE + " DESC"
        )?.use {
            val list = mutableListOf<TransactionDetailEntity>()
            while (it.moveToNext()) {
                val body = it.getStringOrNull(0)
                val address = it.getStringOrNull(1)
                val date = it.getLongOrNull(2)

                if (!address.isNullOrEmpty() && !body.isNullOrEmpty()) {
                    val sms = LocalSms(
                        text = body,
                        sender = address,
                        date = date
                    )
                    recognizer.recognize(sms)?.let { merchant ->
                        list.add(merchant.parser.parseSms(merchant, sms))
                    }
                }
            }
            list
        }
    }

    companion object {
        private const val TAG = "SMSLoader"
    }
}


