package com.crater.android.data.dto.invoice


import com.crater.android.model.RecordPaymentHistory
import com.google.gson.annotations.SerializedName

data class CreateInvoiceRequest(
    @SerializedName("invoice_no") val invoiceNo: String,
    @SerializedName("from_user") val fromUser: String,
    @SerializedName("customer") val customer: String,
    @SerializedName("phone") val phone: String,
    @SerializedName("email") val email: String,
    @SerializedName("gst") val gst: String,
    @SerializedName("address") val address: String,
    @SerializedName("service") val service: List<ServiceDto>,
    @SerializedName("amount") val amount: String,
    @SerializedName("creation_date") val creationDate: String,
    @SerializedName("due_date") val dueDate: String,
    @SerializedName("tax") val tax: String,
    @SerializedName("tax_type") val taxType: String,

    @SerializedName("notes") val notes: String? = null,
    @SerializedName("payment_instruction") val paymentInstruction: String? = null,
    @SerializedName("signature") val signature: String? = null,
    @SerializedName("reference") val reference: String? = null,
    @SerializedName("record_payment_history")
    val recordPaymentHistory: List<RecordPaymentHistory> = emptyList()
)