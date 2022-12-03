package com.crater.android.feature.authentication.ui.model

import android.os.Parcelable
import com.crater.android.feature.authentication.domain.model.AuthFlowType
import kotlinx.parcelize.Parcelize

@Parcelize
data class OtpScreenArgs(
    val flowType: AuthFlowType,
    val phoneNumber: String,
    val inviteCode: String?
) : Parcelable
