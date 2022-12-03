package com.crater.android.utils

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Base64
import java.io.IOException

fun String?.toBitmap(): Bitmap? {
    if (this.isNullOrBlank()) return null
    return try {
        val imageBytes = Base64.decode(this, Base64.DEFAULT)
        BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.size)
    } catch (e: IOException) {
        null
    }
}