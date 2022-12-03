package com.crater.android.data.dto.engagement


import com.google.gson.annotations.SerializedName
import androidx.annotation.Keep

@Keep
data class User(
    @SerializedName("id")
    val id: String,
    @SerializedName("name")
    val name: String
)