package com.crater.android.data.dto.phyllo

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class WorkPlatform(
   @SerializedName("id") val id: String,
   @SerializedName("logo_url") val logo_url: String,
   @SerializedName("name") val name: String,
)