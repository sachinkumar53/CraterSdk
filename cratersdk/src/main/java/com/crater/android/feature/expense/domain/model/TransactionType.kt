package com.crater.android.feature.expense.domain.model

enum class TransactionType {
    Credit,
    Debit,

    //Refund,
    Unknown;

    companion object {
        fun findTransactionTypeByText(text: String): TransactionType {
            val debitKeywords = listOf(
                "debited",
                "paid",
                "deducted",
                "sent",
                "spent",
                "withdrawn",
                "payment",
                "purchase"
            )
            val creditKeywords = listOf("credited", "received", "deposited", "refunded")
            return when {
                debitKeywords.any { text.contains(it, true) } -> Debit
                creditKeywords.any { text.contains(it, true) } -> Credit
                else -> Unknown
            }
        }
    }

}