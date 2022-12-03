package com.crater.android.feature.expense.data.sms.filter.bank

import com.crater.android.feature.expense.data.sms.filter.FilterUtil
import com.crater.android.feature.expense.data.sms.filter.SmsFilter
import com.crater.android.feature.expense.data.sms.merchant.Bank
import com.crater.android.feature.expense.domain.model.LocalSms

//private const val TAG = "BankSmsFilter"

class BankSmsFilter : SmsFilter() {

    override fun recognizeSms(sms: LocalSms): Bank? {
        val (sender, text, _) = sms

        if (!isTransactionSms(text)) return null

        val bank: Bank = if (sender.contains('-')) {
            if (FilterUtil.isSmsFormatValid(sender))
                Bank.values().find {
                    sender.endsWith(it.name, true)
                }
            else null
        } else {
            Bank.values().find {
                sender.substring(2, sender.length).equals(it.name, true)
            }
        } ?: return null

        return bank
    }

    private fun isTransactionSms(text: String): Boolean {
        if (text.contains("OneCard", true)) return true

        val accNoPattern1 =
            "([Aa]/[Cc])((\\s?[Nn]o[.:]?)|(\\s?ending)|(\\s?ending\\swith))?\\s*([0-9]*[Xx*.]+\\d{3,6})".toRegex()
        val accNoPattern2 = "([0-9Xx]+\\d{4})".toRegex()

        val cardPattern1 = "([Cc]ard)\\s*([Xx]*\\d{4})".toRegex()
        val cardPattern2 = "([Cc]ard\\sending)(\\swith)?\\s*([Xx]*\\d{4})".toRegex()

        if (
            text.contains("AutoPay", true) ||
            text.contains("recurring", true) ||
            text.contains("due", true)
        ) return false

        if (listOf(
                "credited",
                "received",
                "deposited",
                "refunded",
                "debited",
                "deducted",
                "sent",
                "spent",
                "paid",
                "withdrawn"
            ).any { text.contains(it, true) }
        ) {
            if (text.contains("([Rr]s\\.?|INR)\\s*".toRegex()))
                if (text.contains("a/c", true) || text.contains("card", true))
                    if (text.contains(accNoPattern2)
                        || text.contains(accNoPattern1)
                        || text.contains(cardPattern1)
                        || text.contains(cardPattern2)
                    ) return true
        }
        return false
    }

}