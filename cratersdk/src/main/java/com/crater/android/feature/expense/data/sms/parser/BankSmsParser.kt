package com.crater.android.feature.expense.data.sms.parser

import android.util.Log
import com.crater.android.feature.expense.data.entity.TransactionDetailEntity
import com.crater.android.feature.expense.data.sms.merchant.Bank
import com.crater.android.feature.expense.domain.model.ExpenseCategory
import com.crater.android.feature.expense.domain.model.LocalSms
import com.crater.android.feature.expense.domain.model.TransactionType
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId
import java.util.regex.Pattern

object BankSmsParser : SmsParser {
    /**
     * Sample transaction
     * HDFC Bank: Rs 31133.00 debited from a/c **9612 on 07-09-22 to
     * VPA cred.club@axisb(UPI Ref No 225016920384). Not you? Call on 18002586161 to report
     */

    /**
     * Sample Credit card transaction
     * You've spent Rs.1530 On HDFC Bank CREDIT Card xx0362 At AMAZON On 2022-09-05:14:36:32
     * Avl bal: Rs.283981 Curr O/s: Rs.42019 Not you?Call 18002586161
     */

    private const val AMOUNT_PATTERN = "([Rr]s\\.?|INR)\\s*([\\d,]+\\.?\\d*)"

    override fun parseSms(bank: Bank, sms: LocalSms): TransactionDetailEntity {
        val text = sms.text
        return if (text.contains("OneCard", true)) {
            parseOneCardSms(bank, sms)
        } else if (
            text.contains("Credit card", true) ||
            text.contains("Debit card", true) ||
            text.contains("Your card", true)
        ) {
            Log.i(TAG, "getAccountNumber: card")
            parseCardSms(bank, sms)
        } else {
            Log.i(TAG, "getAccountNumber: Acc")
            parseBankSms(bank, sms)
        }
    }


    private fun parseOneCardSms(bank: Bank, sms: LocalSms): TransactionDetailEntity {
        val spentOn = getSpentOnOneCard(sms.text)
        return TransactionDetailEntity(
            merchantName = spentOn?.smartCapitalize(),
            category = ExpenseCategory.findCategoryByText(sms.text),
            amount = getAmount(sms.text),
            date = getLocalDateTime(sms.date ?: 0L),
            cardNumber = getCardNumber(sms.text),
            refNumber = ParserUtil.getRefNo(sms.text),
            transactionType = TransactionType.Debit,
            accountNumber = null,
            bank = bank
        )
    }

    private fun parseCardSms(bank: Bank, sms: LocalSms): TransactionDetailEntity {
        var spentOn = getSpentOn(sms.text)
        if (spentOn.isNullOrEmpty())
            spentOn = ParserUtil.getMerchantName(sms.text)
        if (spentOn != null && spentOn.startsWith("your card", true))
            spentOn = "Merchant"

        return TransactionDetailEntity(
            merchantName = spentOn?.smartCapitalize(),
            category = ExpenseCategory.findCategoryByText(sms.text),
            amount = getAmount(sms.text),
            date = getLocalDateTime(sms.date ?: 0L),
            cardNumber = getCardNumber(sms.text),
            refNumber = ParserUtil.getRefNo(sms.text),
            transactionType = TransactionType.findTransactionTypeByText(sms.text),
            accountNumber = null,
            bank = bank
        )
    }

    private fun parseBankSms(bank: Bank, sms: LocalSms): TransactionDetailEntity {
        var merchant = ParserUtil.getMerchantName(sms.text)?.smartCapitalize()
        if (merchant.isNullOrEmpty())
            merchant = ParserUtil.getMerchantUpiId(sms.text)
        if (merchant.isNullOrEmpty())
            merchant = if (ParserUtil.isUPITransfer(sms.text)) "UPI Transfer" else "Merchant"

        return TransactionDetailEntity(
            merchantName = merchant,
            category = ExpenseCategory.findCategoryByText(sms.text),
            amount = getAmount(sms.text),
            date = getLocalDateTime(sms.date ?: 0L),
            refNumber = ParserUtil.getRefNo(sms.text),
            transactionType = TransactionType.findTransactionTypeByText(sms.text),
            accountNumber = getAccountNumber(sms.text),
            cardNumber = null,
            bank = bank
        )

    }

    private fun getAccountNumber(text: String): String? {
        var accNumber: String? = null
        val pattern1 =
            "([Aa][/][Cc])((\\s?[Nn]o[.:]?)|(\\s?ending)|(\\s?ending\\swith))?\\s*([0-9]*[Xx*.]+\\d{3,6})".toRegex()
        val pattern2 = "([0-9Xx*]+)(\\d{4})".toRegex()

        when {
            text.contains(pattern1) -> {
                val matcher = pattern1.toPattern().matcher(text)
                if (matcher.find()) {
                    accNumber = matcher.group(6)
                }
//                Log.i(TAG, "getAccountNumber: Pattern1 $accNumber")
            }

            text.contains(pattern2) -> {
                val matcher = pattern2.toPattern().matcher(text)
                if (matcher.find()) {
                    accNumber = matcher.group(2)
                }
//                Log.i(TAG, "getAccountNumber: Pattern1 $accNumber")
            }
        }
        if (!accNumber.isNullOrEmpty()) {
            accNumber = accNumber.filter { it.isDigit() }.takeLast(4)
        }

        return accNumber
    }

    private fun getCardNumber(text: String): String? {
        var cardNumber: String? = null
        val cardPattern1 = "([Cc]ard)\\s*([Xx]*\\d{4})".toRegex()
        val cardPattern2 = "([Cc]ard\\sending)(\\swith)?\\s*([Xx]*\\d{4})".toRegex()

        when {
            text.contains(cardPattern2) -> {
                val matcher = cardPattern2.toPattern().matcher(text)
                if (matcher.find())
                    cardNumber = matcher.group(3)
            }
            text.contains(cardPattern1) -> {
                val matcher = cardPattern1.toPattern().matcher(text)
                if (matcher.find())
                    cardNumber = matcher.group(2)
            }
        }
        if (!cardNumber.isNullOrEmpty()) {
            cardNumber = cardNumber.filter { it.isDigit() }.takeLast(4)
        }
        return cardNumber
    }

    private fun getSpentOnOneCard(text: String): String? {
        val pattern1 = "(\\b[Aa]t\\b)([\\w\\s&,+]+)(\\b[Oo]n\\b)".toRegex()
        val pattern2 = "(\\b[Aa]t\\b)([\\w\\s&,+]+)(\\b[Hh]as\\b)".toRegex()
        val pattern3 = "(\\b[Aa]t\\b)([\\w\\s&,+]+)(\\.)".toRegex()

        var spentOn: String? = null
        if (text.contains(pattern1)) {
            val matcher = pattern1.toPattern().matcher(text)
            if (matcher.find()) {
                spentOn = matcher.group(2)?.trim()
            }
        } else if (text.contains(pattern2)) {
            val matcher = pattern2.toPattern().matcher(text)
            if (matcher.find()) {
                spentOn = matcher.group(2)?.trim()
            }
        } else if (text.contains(pattern3)) {
            val matcher = pattern3.toPattern().matcher(text)
            if (matcher.find()) {
                spentOn = matcher.group(2)?.trim()
            }
        }

        spentOn = ParserUtil.removeSpecialChar(spentOn)
        return spentOn
    }

    private fun getSpentOn(text: String): String? {
        val pattern = "(\\b[Aa]t\\b)([\\w\\s&,.+]+)(\\b[Oo]n\\b)".toRegex()
        var spentOn: String? = null
        if (text.contains(pattern)) {
            val matcher = pattern.toPattern().matcher(text)
            if (matcher.find()) {
                spentOn = matcher.group(2)?.trim()
            }
        }
        spentOn = ParserUtil.removeSpecialChar(spentOn)
        return spentOn
    }


    private fun getLocalDateTime(date: Long): LocalDate {
        return Instant.ofEpochMilli(date)
            .atZone(ZoneId.systemDefault())
            .toLocalDate()
    }

    private fun getAmount(body: String): Double? {
        val amountMatcher = Pattern.compile(AMOUNT_PATTERN).matcher(body)
        val amount = if (amountMatcher.find()) {
            amountMatcher.group(2)?.replace(",", "")?.toDoubleOrNull()
        } else null
        return amount
    }
}

private const val TAG = "BankSmsParser"