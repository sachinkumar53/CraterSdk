package com.crater.android.feature.invoicing.data.repository

import com.crater.android.core.data.cache.CacheManager
import com.crater.android.data.dto.invoice.CreateInvoiceRequest
import com.crater.android.data.network.ApiService
import com.crater.android.feature.cash.domain.model.Amount
import com.crater.android.feature.invoicing.data.mapper.toInfo
import com.crater.android.feature.invoicing.data.mapper.toInvoiceTracker
import com.crater.android.feature.invoicing.data.mapper.toPaymentTracker
import com.crater.android.feature.invoicing.data.mapper.toService
import com.crater.android.feature.invoicing.domain.model.*
import com.crater.android.feature.invoicing.domain.model.Tracker.InvoiceTracker
import com.crater.android.feature.invoicing.domain.model.Tracker.PaymentTracker
import com.crater.android.feature.invoicing.domain.repository.InvoiceRepository
import com.crater.android.model.PaymentTrackerResponse
import com.crater.android.utils.toBitmap
import kotlinx.coroutines.Dispatchers.Default
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.withContext

class InvoiceRepositoryImpl(
    private val apiService: ApiService,
    private val cacheManager: CacheManager
) : InvoiceRepository {

    override suspend fun getInvoiceTracker(): InvoiceTracker = withContext(Default) {
        val response = apiService.getInvoiceTracker()
        response.toInvoiceTracker()
    }

    override suspend fun getPaymentTracker(): PaymentTracker = withContext(Default) {
        val response = apiService.getPaymentTracker()
        response.toPaymentTracker()
    }

    override suspend fun getRecentInvoices(): List<InvoiceInfo> =
        withContext(Default) {
            val response = apiService.getRecentInvoices()
            response.map { it.toInfo() }
        }

    override suspend fun getAllInvoices(): List<InvoiceInfo> = withContext(Default) {
        val response = apiService.getAllInvoices()
        response.map { it.toInfo() }
    }

    override suspend fun getRecentPaymentTrackers(): List<PaymentTrackerInfo> {
        return getPaymentTrackers(false)
    }

    override suspend fun getAllPaymentTrackers(): List<PaymentTrackerInfo> {
        return getPaymentTrackers(true)
    }

    private suspend fun getPaymentTrackers(all: Boolean) = withContext(Default) {
        val response = apiService.getPaymentTrackers(all)
        response.map { it.toInfo() }
    }

    override suspend fun createInvoice(request: CreateInvoiceRequest): String {
        return apiService.createInvoice(request)
    }

    override suspend fun createPaymentTracker(
        trackerNumber: String,
        fromUser: String,
        customer: Customer,
        amountDue: String
    ): String {
        return apiService.createPaymentTracker(
            trackerNumber = trackerNumber,
            fromUser = fromUser,
            customer = customer.name,
            amountDue = amountDue,
            phone = customer.phone,
            email = customer.emailId ?: ""
        )
    }

    override suspend fun getInvoicePreviewDetails(invoiceNo: String): InvoicePreviewDetail {
        val response = apiService.getInvoiceDetails(invoiceNo)
        val userDetail = cacheManager.getUserDetails().first()

        return InvoicePreviewDetail(
            invoiceNumber = invoiceNo,
            creationDate = response.creationDate,
            dueDate = response.dueDate,
            isPaid = response.status.equals("paid", true),
            fromUserInfo = UserInfo(
                name = response.fromUser,
                phone = userDetail.mobileNumber,
                emailId = userDetail.emailId
            ),
            customerInfo = CustomerInfo(
                name = response.customer,
                phone = response.phone,
                emailId = response.email ?: "",
                address = response.address ?: "",
                gst = response.gst ?: ""
            ),
            serviceInfo = ServiceInfo(
                services = response.service.map { it.toService() },
                tax = response.tax.toDoubleOrNull() ?: 0.0,
                taxType = response.taxType
            )
        )
    }

    override suspend fun getTrackerPreviewDetails(trackerNo: String): PaymentTrackerPreviewDetail {
        val response = apiService.getPaymentTrackerDetails(trackerNo)
        val userDetail = cacheManager.getUserDetails().first()
        return PaymentTrackerPreviewDetail(
            tackerNumber = trackerNo,
            dueDate = response.date,
            fromUserInfo = UserInfo(
                name = response.fromUser,
                phone = userDetail.mobileNumber,
                emailId = userDetail.emailId
            ),
            customerInfo = CustomerInfo(
                name = response.customer,
                phone = response.phone ?: "",
                emailId = response.email ?: "",
                address = "",
                gst = ""
            ),
            amountDue = Amount(response.amountDue.toDoubleOrNull() ?: 0.0),
            amountPaid = Amount(response.amountPaid.toDoubleOrNull() ?: 0.0)
        )
    }

    override suspend fun deletePaymentTracker(trackerNo: String): String {
        return apiService.deletePaymentTracker(trackerNo)
    }

    override suspend fun deleteInvoice(invoiceNo: String): String {
        return apiService.deleteInvoice(invoiceNo)
    }

    override suspend fun generatePaymentLink(invoiceNo: String): PaymentInfo {
        val response = apiService.generateUpiLinkForInvoice(invoiceNo)
        return PaymentInfo(
            qrCode = response.data.encodedDynamicQrCode.toBitmap(),
            upiLink = response.data.upiUri
        )
    }
}

private fun PaymentTrackerResponse.toInfo(): PaymentTrackerInfo {
    return PaymentTrackerInfo(
        id = paymentInv,
        customerName = customer,
        amount = Amount(amountDue.toDoubleOrNull() ?: 0.0),
    )
}
