package com.crater.android.feature.invoicing.domain.repository

import com.crater.android.data.dto.invoice.CreateInvoiceRequest
import com.crater.android.feature.invoicing.domain.model.*
import com.crater.android.feature.invoicing.domain.model.Tracker.InvoiceTracker
import com.crater.android.feature.invoicing.domain.model.Tracker.PaymentTracker

interface InvoiceRepository {

    suspend fun getInvoiceTracker(): InvoiceTracker
    suspend fun getPaymentTracker(): PaymentTracker

    suspend fun getRecentInvoices(): List<InvoiceInfo>
    suspend fun getAllInvoices(): List<InvoiceInfo>

    suspend fun getRecentPaymentTrackers(): List<PaymentTrackerInfo>
    suspend fun getAllPaymentTrackers(): List<PaymentTrackerInfo>

    suspend fun createInvoice(request: CreateInvoiceRequest): String

    suspend fun createPaymentTracker(
        trackerNumber: String,
        fromUser: String,
        customer: Customer,
        amountDue: String
    ): String

    suspend fun getInvoicePreviewDetails(invoiceNo: String): InvoicePreviewDetail
    suspend fun getTrackerPreviewDetails(trackerNo: String): PaymentTrackerPreviewDetail
    suspend fun deletePaymentTracker(trackerNo: String): String
    suspend fun deleteInvoice(invoiceNo: String): String

    suspend fun generatePaymentLink(invoiceNo: String): PaymentInfo
}