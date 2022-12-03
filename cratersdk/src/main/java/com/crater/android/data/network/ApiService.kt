package com.crater.android.data.network

import com.crater.android.data.dto.engagement.EngagementResponse
import com.crater.android.data.dto.invoice.CreateInvoiceRequest
import com.crater.android.data.dto.invoice.InvoiceDetailResponse
import com.crater.android.data.dto.invoice.upi.InvoiceUPILinkResponse
import com.crater.android.data.dto.onboarding.OnBoardingDetailsResponse
import com.crater.android.data.dto.phyllo.CreateUserResponse
import com.crater.android.data.dto.phyllo.GenerateSdkTokenResponse
import com.crater.android.data.dto.phyllo.GetProfileResponse
import com.crater.android.data.dto.transaction.account_detail.AccountDetailResponse
import com.crater.android.data.dto.transaction.generate_upi_link.UpiLinkResponse
import com.crater.android.data.dto.transaction.get_balance.GetBalanceResponse
import com.crater.android.data.dto.transaction.get_statement.GetStatementResponse
import com.crater.android.data.dto.transaction.initiate_collect.CollectRequestResponse
import com.crater.android.data.dto.transaction.self_transfer.SendToSelfResponse
import com.crater.android.data.dto.transaction.upi_verification.UpiVerificationResponse
import com.crater.android.feature.cash.data.dto.bankdetail.UserBankDetailResponse
import com.crater.android.feature.invoicing.data.dto.CustomerDto
import com.crater.android.feature.kyc.data.dto.PanVerificationResponse
import com.crater.android.feature.social.data.dto.comment.CommentResponse
import com.crater.android.feature.social.data.dto.disconnect.AccountDisconnectResponse
import com.crater.android.model.*
import io.reactivex.Observable
import okhttp3.ResponseBody
import retrofit2.http.*

interface ApiService {

    @POST("api/v1/phyllo/create_a_user")
    suspend fun createPhylloUser(): CreateUserResponse

    @POST("api/v1/phyllo/sdk_tokens")
    suspend fun generatePhylloSdkToken(): GenerateSdkTokenResponse

    @GET("api/v1/phyllo/get_profile_instagram")
    suspend fun getInstagramProfile(): GetProfileResponse


    @GET("api/v1/phyllo/get_profile_youtube")
    suspend fun getYoutubeProfile(): GetProfileResponse


    @POST("/api/v1/kyc/pan/validate")
    suspend fun verifyPanNumber(
        @Query("pan_number") panNumber: String,
    ): PanVerificationResponse


    @POST("/api/auth/logout")
    suspend fun logout(): LogoutResponse

    @POST("api/v1/phyllo/disconnect")
    suspend fun disconnectPhylloAccount(
        @Query("account_id") id: String
    ): AccountDisconnectResponse

    @GET("api/v1/phyllo/get_all_engagemnet_youtube")
    suspend fun getYoutubeEngagements(
        @Query("limit") limit: String = "100",
        @Query("to_date") toDate: String,
        @Query("from_date") fromDate: String
    ): EngagementResponse

    @GET("api/v1/phyllo/get_all_engagemnet_instagram")
    suspend fun getInstagramEngagements(
        @Query("limit") limit: String = "100",
        @Query("to_date") toDate: String,
        @Query("from_date") fromDate: String
    ): EngagementResponse

    @GET("api/v1/payment/get_bank_logo")
    fun getBankLogo(
        @Query("bank_name") bankName: String
    ): Observable<String?>

    @GET("api/v1/phyllo/get_comments_youtube_per_video")
    suspend fun getYoutubeCommentForVideo(
        @Query("video_content_id")
        videoId: String
    ): CommentResponse

    @GET("api/v1/phyllo/get_comments_per_post_instagram")
    suspend fun getInstagramCommentForPost(
        @Query("content_id")
        videoId: String
    ): CommentResponse

    @GET("api/v1/invoice/fetch_all_customer_details")
    suspend fun getCustomers(): List<CustomerDto>

    @POST("api/v1/invoice/add_customer_details")
    suspend fun addCustomer(
        @Query("name") name: String,
        @Query("phone") phone: String,
        @Query("email_id") emailId: String?,
        @Query("PAN") pan: String?,
        @Query("GST") gst: String?,
        @Query("address") address: String?,
        @Query("city") city: String?,
        @Query("state") state: String?,
        @Query("pincode") pinCode: String?,
        @Query("country") country: String?
    ): String

    @PATCH("api/v1/invoice/update_customer_details")
    suspend fun updateCustomer(
        @Query("phone") phone: String,
        @Body body: CustomerDto
    ): ResponseBody

    @DELETE("api/v1/invoice/delete customer")
    suspend fun deleteCustomer(
        @Query("phone") phone: String
    ): String


    @POST("api/v1/kyc/penny_drop_transaction")
    suspend fun verifyBankDetails(
        @Query("name") name: String,
        @Query("account_number") accountNumber: String,
        @Query("ifsc") ifsc: String,
        @Query("email") email: String
    ): CommonCodeResponse

    @POST("api/v1/payment/Virtual_account/create_account_customupi")
    suspend fun createVirtualAccountWithCustomUpi(
        @Query("custome_VPA") upiId: String
    ): CommonStatusCodeResponse

    @GET("api/v1/payment/Virtual_account/get_balance")
    suspend fun getBalance(): GetBalanceResponse

    @GET("api/v1/payment/Virtual_account/fetch_account_details")
    suspend fun fetchAccountDetails(): AccountDetailResponse

    @GET("api/v1/payment/Virtual_account/get_statement")
    suspend fun getStatement(): GetStatementResponse

    @POST("api/v1/payment/Virtual_account/generate_upi_link_invoice")
    suspend fun generateUpiLinkForInvoice(@Query("invoice_no") invoiceNo: String): InvoiceUPILinkResponse

    @POST("api/v1/payment/vpa/validate")
    suspend fun verifyUpiId(@Query("upi_id") upiId: String): UpiVerificationResponse

    @POST("api/v1/payment/Virtual_account/initiate_collect_request")
    suspend fun initiateCollectRequest(
        @Query("payer_upi") upiId: String,
        @Query("amount") amount: Double
    ): CollectRequestResponse

    @POST("api/v1/payment/Virtual_account/generate_upi_link")
    suspend fun generateUpiLink(@Query("amount") amount: Double): UpiLinkResponse


    @POST("api/v1/payment/Virtual_account/send_money_to_self")
    suspend fun sendMoneyToSelf(@Query("amount") amount: Double): SendToSelfResponse

    @GET("api/v1/invoice/fetch_invoice_status")
    suspend fun getInvoiceTracker(): PendingPaymentResponse

    @GET("api/v1/invoice/fetch_inc_payment_status")
    suspend fun getPaymentTracker(): PendingPaymentResponse

    @GET("api/v1/invoice/recent_invoces")
    suspend fun getRecentInvoices(): List<InvoiceDetailResponse>

    @GET("api/v1/invoice/all_invoices")
    suspend fun getAllInvoices(): List<InvoiceDetailResponse>

    @GET("api/v1/invoice/fetch_payment_tracker")
    suspend fun getPaymentTrackers(@Query("flag") all: Boolean): List<PaymentTrackerResponse>

    @POST("api/v1/invoice/create_an_invoice")
    suspend fun createInvoice(@Body request: CreateInvoiceRequest): String

    @GET("api/v1/invoice/invoces_details")
    suspend fun getInvoiceDetails(@Query("invoice_no") invoiceNo: String): InvoiceDetailResponse

    @POST("api/v1/invoice/track_payment")
    suspend fun createPaymentTracker(
        @Query("payment_inv") trackerNumber: String,
        @Query("from_user") fromUser: String,
        @Query("customer") customer: String,
        @Query("amount_due") amountDue: String,
        @Query("email") email: String,
        @Query("phone") phone: String,
    ): String

    @GET("api/v1/invoice/payment_trac_details")
    suspend fun getPaymentTrackerDetails(
        @Query("payment_inv") trackerNumber: String
    ): PaymentTrackerResponse

    @DELETE("api/v1/invoice/delete_tracked_payment")
    suspend fun deletePaymentTracker(
        @Query("payment_tracker_no") paymentTrackerNo: String,
    ): String

    @DELETE("api/v1/invoice/delete_invoice")
    suspend fun deleteInvoice(
        @Query("invoice_no") invoiceNo: String
    ): String

    @GET("api/auth/get_onboarding_details")
    suspend fun getOnBoardingDetails(): OnBoardingDetailsResponse

    @GET("api/v1/payment/Virtual_account/fetch_user_added_bank_details")
    suspend fun getUserBankAccountDetails(): UserBankDetailResponse

    @POST("api/auth/register_user")
    suspend fun registerUser(
        @Query("mobile") mobile: String,
        @Query("referral") referral: String,
    ): CommonStatusCodeResponse

    @POST("api/auth/login")
    suspend fun login(@Query("mobile") mobile: String): CommonStatusCodeResponse

    @POST("api/auth/verify_login_otp")
    suspend fun verifyLoginOtp(
        @Query("mobile") mobile: String,
        @Query("otp") otp: String,
    ): LoginSuccessResponse

    @POST("api/auth/verify_register_otp")
    suspend fun verifyRegisterOtp(
        @Query("mobile") mobile: String,
        @Query("otp") otp: String,
        @Query("invite_code") inviteCode: String?,
    ): LoginSuccessResponse

    @POST("api/auth/resend_otp")
    suspend fun resendOtp(@Query("mobile") mobile: String): CommonStatusCodeResponse

    @POST("api/auth/enter_user_detail")
    suspend fun addPersonalDetails(
        @Query("firstname") firstName: String,
        @Query("lastname") lastName: String,
        @Query("gender") gender: String,
        @Query("dob") dob: String,
        @Query("email") email: String,
        @Query("city") city: String,
        @Query("state") state: String,
        @Query("pincode") pinCode: String,
    ): CommonCodeResponse

    @POST("api/v1/payment/virtual_account/upi_validation")
    suspend fun validateUpiId(
        @Query("custome_VPA") upiId: String
    ): String
}