package com.crater.android.feature.social.data.dto.common


import com.google.gson.annotations.SerializedName
import androidx.annotation.Keep

@Keep
data class User(
    @SerializedName("id")
    val id: String,
    @SerializedName("name")
    val name: String
)