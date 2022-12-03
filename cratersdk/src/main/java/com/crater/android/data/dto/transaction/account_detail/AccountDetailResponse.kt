package com.crater.android.data.dto.transaction.account_detail


import com.google.gson.annotations.SerializedName

data class AccountDetailResponse(
    @SerializedName("Account_no")
    val accountNo: String,
    @SerializedName("IFSC")
    val ifsc: String,
    @SerializedName("UPI_id")
    val upiId: String,
    @SerializedName("upi_qr")
    val upiQr: String
)