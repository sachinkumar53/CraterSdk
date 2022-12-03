package com.crater.android.data.dto.invoice

import com.crater.android.model.RecordPaymentHistory
import com.google.gson.annotations.SerializedName

data class InvoiceDetailResponse(
    @SerializedName("amount") val amount: String,
    @SerializedName("creation_date") val creationDate: String,
    @SerializedName("customer") val customer: String,
    @SerializedName("due_date") val dueDate: String,
    @SerializedName("from_user") val fromUser: String,
    @SerializedName("id") val id: Int,
    @SerializedName("invoice_no") val invoiceNo: String,
    @SerializedName("notes") val notes: String,
    @SerializedName("payment_note") val paymentNote: String,
    @SerializedName("payment_instruction") val paymentInstruction: String?,
    @SerializedName("phone") val phone: String,
    @SerializedName("email") val email: String?,
    @SerializedName("reference") val reference: String,
    @SerializedName("service") val service: List<ServiceDto>,
    @SerializedName("signature") val signature: String,
    @SerializedName("user_id") val userId: String,
    @SerializedName("date") val date: String,
    @SerializedName("month") val month: String,
    @SerializedName("year") val year: String,
    @SerializedName("status") val status: String,
    @SerializedName("amount_paid") val amountPaid: String,
    @SerializedName("record_payment_history") val recordPaymentHistory: List<RecordPaymentHistory>,
    @SerializedName("amount_due") val amountDue: String,
    @SerializedName("tax") val tax: String,
    @SerializedName("tax_type") val taxType: String,
    @SerializedName("address") val address: String?,
    @SerializedName("gst") val gst: String?,
    var subTotalAmount: String?,
    var salesTax: String?,
    var totalAmount: String?,
)