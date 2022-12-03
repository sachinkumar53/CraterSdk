package com.crater.android.feature.cash.data.dto.bankdetail


import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class UserBankDetailResponse(
    @SerializedName("Account_no") val accountNo: String,
    @SerializedName("IFSC") val ifsc: String,
    @SerializedName("name") val name: String
)