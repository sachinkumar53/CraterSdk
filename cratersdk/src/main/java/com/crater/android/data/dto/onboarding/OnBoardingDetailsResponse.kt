package com.crater.android.data.dto.onboarding

import com.google.gson.annotations.SerializedName

data class OnBoardingDetailsResponse(
    @SerializedName("First_name")
    val firstName: String?,

    @SerializedName("Last_name")
    val lastName: String?,

    @SerializedName("Gender")
    val gender: String?,

    @SerializedName("DOB")
    val dob: String?,

    @SerializedName("Address1")
    val address1: String?,

    @SerializedName("Address2")
    val address2: String?,

    @SerializedName("city")
    val city: String?,

    @SerializedName("state")
    val state: String?,

    @SerializedName("pincode")
    val pinCode: String?,

    @SerializedName("pan")
    val pan: String?,

    @SerializedName("Adhaar")
    val adhaarNum: String?,

    @SerializedName("upi")
    val upi: String?,

    @SerializedName("bank_account_no")
    val bankAccountNo: String?,

    @SerializedName("wallet_created")
    val walletCreated: String?,

    @SerializedName("pin")
    val pin: String?,

    @SerializedName("mobile_no")
    val mobileNo: String?,

    @SerializedName("invite_code")
    val inviteCode: String?,

    @SerializedName("email")
    val email: String?,

    @SerializedName("address")
    val address: String?,

    @SerializedName("bank_account_verify_status")
    val isBankKycDone: Boolean,

    @SerializedName("virtual_account_created")
    val isVirtualAccountCreated: Boolean,

    @SerializedName("virtual_account_created_payout")
    val isVirtualAccountPayoutCreated: Boolean
)