package com.crater.android.feature.expense.data.sms.merchant

import com.crater.android.feature.expense.data.entity.TransactionDetailEntity
import com.crater.android.feature.expense.data.sms.parser.BankSmsParser
import com.crater.android.feature.expense.data.sms.parser.SmsParser
import com.crater.android.feature.expense.domain.model.LocalSms

enum class Bank(
    val bankName: String,
) {
    CENTBK("CBI"),
    HDFCBK("HDFC"),
    ICICIB("ICICI"),
    SBIINB("SBI"),
    SBMSMS("SBMSMS"),
    CBSSBI("CBSSBI"),
    SBIPSG("SBI"),
    SBIUPI("SBI"),
    SBICRD("SBI"),
    ATMSBI("SBI"),
    IDFCFB("IDFC"),
    UCOBNK("UCO"),
    CANBNK("CANARA"),
    BOIIND("BOI"),
    AXISBK("AXIS"),
    PAYTMB("PAYTM"),
    UnionB("UNION"),
    INDBNK("INDIAN"),
    KOTAKB("KOTAK"),
    SCBANK("SC "),
    PNBSMS("PUNJAB"),
    DOPBNK("DOP"),
    YESBNK("YES"),
    IDBIBK("IDBI"),
    CITIBK("CITI"),
    BOBTXN("BOB"),
    IOBCHN("IOB"),
    MAHABK("MAHARASHTRA"),
    OBCBNK("OBCB"),
    RBLBNK("RBL"),
    RBLCRD("RBL"),
    HSBCBK("HSB"),
    HSBCIN("HSB"),
    INDUSB("INDUS"),
//    JANSVA("JANSEVA"),
    ONECRD("ONE CARD");

    val parser: SmsParser
        get() = BankSmsParser
}

fun Bank.parseSms(sms: LocalSms): TransactionDetailEntity {
    return parser.parseSms(this, sms)
}