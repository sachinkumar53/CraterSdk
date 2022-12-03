package com.crater.android.feature.expense.data.sms.filter

import com.crater.android.feature.expense.data.sms.filter.bank.BankSmsFilter
import com.crater.android.feature.expense.data.sms.merchant.Bank
import com.crater.android.feature.expense.domain.model.LocalSms

class SmsRecognizer {
    private val filters = listOf<SmsFilter>(BankSmsFilter())

    fun recognize(sms: LocalSms): Bank? {
        for (filter in filters) {
            return filter.recognizeSms(sms)
        }
        return null
    }
}