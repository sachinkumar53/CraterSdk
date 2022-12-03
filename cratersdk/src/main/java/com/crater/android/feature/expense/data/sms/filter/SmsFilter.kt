package com.crater.android.feature.expense.data.sms.filter

import com.crater.android.feature.expense.data.sms.merchant.Bank
import com.crater.android.feature.expense.domain.model.LocalSms

abstract class SmsFilter {

    abstract fun recognizeSms(sms: LocalSms): Bank?

}