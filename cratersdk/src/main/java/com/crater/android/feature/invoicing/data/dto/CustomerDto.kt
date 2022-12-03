package com.crater.android.feature.invoicing.data.dto


import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class CustomerDto(
    /*@SerializedName("id")
    val id: Int,*/
    @SerializedName("name")
    val name: String,
    @SerializedName("phone")
    val phone: String,

    @SerializedName("email_id")
    val emailId: String?,
    @SerializedName("GST")
    val gst: String?,
    @SerializedName("city")
    val city: String?,
    @SerializedName("pincode")
    val pinCode: String?,
    /*@SerializedName("user_id")
    val userId: String,*/
    @SerializedName("PAN")
    val pan: String?,
    @SerializedName("address")
    val address: String?,
    @SerializedName("state")
    val state: String?,
    @SerializedName("country")
    val country: String?
)