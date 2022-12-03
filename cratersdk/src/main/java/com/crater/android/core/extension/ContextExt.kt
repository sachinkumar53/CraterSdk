package com.crater.android.core.extension

import android.app.Activity
import android.content.ActivityNotFoundException
import android.content.Context
import android.content.ContextWrapper
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.util.Log
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.core.content.FileProvider
import com.crater.android.R
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.File
import java.io.FileOutputStream
import java.io.IOException


fun Context.showToast(
    message: String,
    duration: Int = Toast.LENGTH_SHORT
) = Toast.makeText(this, message, duration).show()

fun Context.showToast(
    @StringRes
    messageResId: Int,
    duration: Int = Toast.LENGTH_SHORT
) = Toast.makeText(this, messageResId, duration).show()

fun Context.openActivity(destination: Class<*>) {
    startActivity(Intent(this, destination))
}

fun Context.openActivityWithString(destination: Class<*>, key: String, value: String) {
    val intent = Intent(this, destination)
    intent.putExtra(key, value)
    startActivity(intent)
//        overridePendingTransition(R.anim.left_to_right, R.anim.right_to_left)
}

fun Context.openActivityWithInt(destination: Class<*>, key: String, value: Int) {
    val intent = Intent(this, destination)
    intent.putExtra(key, value)
    startActivity(intent)
//    overridePendingTransition(R.anim.left_to_right, R.anim.right_to_left)
}

fun Context.openActivityWithBoolean(destination: Class<*>, key: String, value: Boolean) {
    val intent = Intent(this, destination)
    intent.putExtra(key, value)
    startActivity(intent)
//        overridePendingTransition(R.anim.left_to_right, R.anim.right_to_left)
}

fun Activity.openActivityForResult(destination: Class<*>, requestCode: Int) {
    startActivityForResult(
        Intent(this, destination),
        requestCode
    )
}

fun Activity.openActivityForResultWithString(
    destination: Class<*>,
    requestCode: Int,
    key: String,
    value: String
) {
    startActivityForResult(
        Intent(this, destination).apply {
            putExtra(key, value)
        },
        requestCode
    )
}


fun Context.startTextShareIntent(
    text: String
) {
    val intent = Intent(Intent.ACTION_SEND)
    intent.type = "text/plain"
    intent.putExtra(Intent.EXTRA_TEXT, text)
    try {
        startActivity(Intent.createChooser(intent, getString(R.string.share_using)))
    } catch (e: ActivityNotFoundException) {
        showToast("No app found to handle this action")
    }
}

fun Context.startImageShareIntent(
    imageUri: Uri
) {
    val intent = Intent(Intent.ACTION_SEND)
    intent.type = "image/png"
    intent.putExtra(Intent.EXTRA_STREAM, imageUri)
    try {
        startActivity(Intent.createChooser(intent, getString(R.string.share_using)))
    } catch (e: ActivityNotFoundException) {
        showToast("No app found to handle this action")
    }
}

fun Context.startTextWithImageShareIntent(
    imageUri: Uri,
    text: String
) {
    val intent = Intent(Intent.ACTION_SEND)
    intent.type = "image/png"
    intent.putExtra(Intent.EXTRA_STREAM, imageUri)
    intent.putExtra(Intent.EXTRA_TEXT, text)
    try {
        startActivity(Intent.createChooser(intent, getString(R.string.share_using)))
    } catch (e: ActivityNotFoundException) {
        showToast("No app found to handle this action")
    }
}

suspend fun Context.saveImage(image: Bitmap): Uri? = withContext(Dispatchers.IO) {
    val imagesFolder = File(cacheDir, "images")

    try {
        imagesFolder.mkdirs()
        val file = File(imagesFolder, "temp.png")
        val stream = FileOutputStream(file)
        image.compress(Bitmap.CompressFormat.PNG, 100, stream)
        stream.flush()
        stream.close()
        FileProvider.getUriForFile(
            this@saveImage,
            applicationContext.packageName + ".provider",
            file
        )
    } catch (e: IOException) {
        Log.d("Utils", "IOException while trying to write file for sharing.", e)
        null
    }

}


fun Context.findActivity(): Activity? {
    var currentContext = this
    while (currentContext is ContextWrapper) {
        if (currentContext is Activity) {
            return currentContext
        }
        currentContext = currentContext.baseContext
    }
    return null
}
