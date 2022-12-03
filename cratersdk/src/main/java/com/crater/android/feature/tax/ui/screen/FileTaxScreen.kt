package com.crater.android.feature.tax.ui.screen

import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import com.crater.android.R
import com.crater.android.core.extension.showToast
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator


@Destination
@Composable
fun FileTaxScreen(
    navigator: DestinationsNavigator
) {
    val context = LocalContext.current
    FileTaxScreenContent(
        onBackClick = navigator::navigateUp,
        onContactClick = {
            val whatsappIntent = Intent(
                Intent.ACTION_VIEW,
                Uri.parse(WHATSAPP_URL)
            ).apply {
                setPackage(WHATSAPP_PKG)
                flags = Intent.FLAG_ACTIVITY_NEW_TASK
            }
            try {
                context.startActivity(whatsappIntent)
            } catch (ex: ActivityNotFoundException) {
                context.showToast(R.string.unable_to_start_activity)
            }
        },
        onDiyClick = { context.showToast(R.string.coming_soon) }
    )
}

private const val WHATSAPP_URL = "https://wa.me/message/U3AQWIPMBB6QO1"
private const val WHATSAPP_PKG = "com.whatsapp"
