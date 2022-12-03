package com.crater.android.feature.expense.data.sms.filter

object FilterUtil {

    fun isSmsFormatValid(sender: String): Boolean {
        val senderPattern = "[a-zA-Z0-9]{2}-[a-zA-Z0-9]{6}".toPattern()
        return senderPattern.matcher(sender).matches()
    }
}