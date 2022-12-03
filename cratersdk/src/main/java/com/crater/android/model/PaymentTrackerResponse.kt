package com.crater.android.model

import com.google.gson.annotations.SerializedName

data class PaymentTrackerResponse(
    @SerializedName("Total_Amount")
    val totalAmount: String,
    @SerializedName("amount_due")
    val amountDue: String,
    @SerializedName("amount_paid")
    val amountPaid: String,
    @SerializedName("customer")
    val customer: String,
    @SerializedName("date")
    val date: String,
    @SerializedName("from_user")
    val fromUser: String,
    @SerializedName("id")
    val id: Int,
    @SerializedName("payment_inv")
    val paymentInv: String,
    @SerializedName("phone")
    val phone: String?,
    @SerializedName("email")
    val email: String?,
    @SerializedName("status")
    val status: String,
    @SerializedName("record_payment_history")
    val recordPaymentHistory: List<RecordPaymentHistory>,
    @SerializedName("user_id")
    val userId: String,
)