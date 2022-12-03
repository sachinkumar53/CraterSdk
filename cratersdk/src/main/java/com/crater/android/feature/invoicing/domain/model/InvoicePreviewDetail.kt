package com.crater.android.feature.invoicing.domain.model

import com.crater.android.feature.cash.domain.model.Amount

data class InvoicePreviewDetail(
    val invoiceNumber: String,
    val creationDate: String,
    val dueDate: String,
    val isPaid: Boolean,
    val fromUserInfo: UserInfo,
    val customerInfo: CustomerInfo,
    val serviceInfo: ServiceInfo
)

data class UserInfo(
    val name: String,
    val phone: String,
    val emailId: String
)

data class CustomerInfo(
    val name: String,
    val phone: String,
    val emailId: String,
    val address: String,
    val gst: String,
)

data class ServiceInfo(
    val services: List<Service>,
    val tax: Double,
    val taxType: String?
) {
    val subTotalAmount: Amount
        get() {

            val amount = services.sumOf {
                val price = it.price.toDoubleOrNull() ?: 0.0
                val quantity = it.quantity.toDouble()
                price * quantity
            }
            return Amount(amount)
        }

    val taxAmount: Amount
        get() {
            val amount = (subTotalAmount.value * tax) / 100.0
            return Amount(amount)
        }

    val totalAmount: Amount
        get() = subTotalAmount + taxAmount

}
