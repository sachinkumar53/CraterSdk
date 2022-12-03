package com.crater.android.feature.expense.data.mapper

import com.crater.android.core.util.DateFormatterUtil
import com.crater.android.core.util.Mapper
import com.crater.android.feature.expense.data.entity.TransactionDetailEntity
import com.crater.android.feature.expense.domain.model.TransactionDetail
import com.crater.android.utils.FormatterUtil

class TransactionMapper : Mapper<TransactionDetailEntity, TransactionDetail> {

    override fun TransactionDetailEntity.map(): TransactionDetail {
        return TransactionDetail(
            merchantName = merchantName ?: "",
            amount = amount?.let { FormatterUtil.getFormattedAmount(it) } ?: "",
            date = DateFormatterUtil.formatInMediumStyle(date),
            category = category,
            transactionType = transactionType,
            refNumber = refNumber,
            accountNumber = accountNumber,
            cardNumber = cardNumber,
            bank = bank
        )
    }

}