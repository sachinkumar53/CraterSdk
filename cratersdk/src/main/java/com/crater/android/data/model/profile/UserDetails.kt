package com.crater.android.data.model.profile

import kotlinx.serialization.Serializable

@Serializable
data class UserDetails(
    val userName: UserName = UserName("", ""),
    val gender: String = "",
    val dob: String = "",
    val emailId: String = "",
    val city: String = "",
    val state: String = "",
    val pinCode: String = "",
    val mobileNumber: String = "",
    val pan: String? = null,
    val isBankKycDone: Boolean = false,
    val isVirtualAccountCreated: Boolean = false,
    val isVirtualAccountPayoutCreated: Boolean = false
    /*val address: String?,
    val address1: String?,
    val address2: String?,

    val adhaarNumber: String?,
    val upiId: String?,
    val bankAccountNo: String?,
    val isWalletCreated: String?,
    val pin: String?,
    val inviteCode: String?,*/
)
