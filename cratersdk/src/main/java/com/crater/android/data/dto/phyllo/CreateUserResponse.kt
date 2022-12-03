package com.crater.android.data.dto.phyllo

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class CreateUserResponse(
    @SerializedName("output") val output: Output,
    @SerializedName("status") val status: String,
)

@Keep
data class Output(
    @SerializedName("Status") val Status: String,
    @SerializedName("account_id") val account_id: String,
    @SerializedName("external_id") val external_id: String,
    @SerializedName("id") val id: Int,
    @SerializedName("name") val name: String,
    @SerializedName("phyllo_id") val phyllo_id: String?,
    @SerializedName("profile_id") val profile_id: String,
)