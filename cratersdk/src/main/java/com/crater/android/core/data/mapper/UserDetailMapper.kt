package com.crater.android.core.data.mapper


import com.crater.android.data.dto.onboarding.OnBoardingDetailsResponse
import com.crater.android.data.model.profile.UserDetails
import com.crater.android.data.model.profile.UserName


fun OnBoardingDetailsResponse.toUserDetails(): UserDetails {
    return UserDetails(
        userName = UserName(firstName ?: "", lastName ?: ""),
        mobileNumber = mobileNo ?: "",
        gender = gender ?: "",
        dob = dob ?: "",
        emailId = email ?: "",
        city = city ?: "",
        state = state ?: "",
        pinCode = pinCode ?: "",
        pan = pan,
        isBankKycDone = isBankKycDone,
        isVirtualAccountCreated = isVirtualAccountCreated,
        isVirtualAccountPayoutCreated = isVirtualAccountPayoutCreated
    )
}
