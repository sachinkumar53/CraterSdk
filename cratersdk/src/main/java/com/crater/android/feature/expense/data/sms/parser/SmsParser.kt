package com.crater.android.feature.expense.data.sms.parser

import com.crater.android.feature.expense.data.entity.TransactionDetailEntity
import com.crater.android.feature.expense.data.sms.merchant.Bank
import com.crater.android.feature.expense.domain.model.LocalSms

interface SmsParser {
    fun parseSms(bank: Bank, sms: LocalSms): TransactionDetailEntity
}