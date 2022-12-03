package com.crater.android.feature.cash.ui.screen.detail

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.crater.android.core.extension.saveImage
import com.crater.android.core.extension.startTextWithImageShareIntent
import com.crater.android.data.model.profile.UserName
import com.crater.android.feature.cash.domain.model.AccountDetail
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.ramcosta.composedestinations.spec.DestinationStyle
import kotlinx.coroutines.launch


@Destination(style = DestinationStyle.Dialog::class)
@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun AccountDetailScreen(
    userName: UserName,
    accountDetail: AccountDetail,
    navigator: DestinationsNavigator
) {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()

    Dialog(
        onDismissRequest = navigator::navigateUp,
        properties = DialogProperties(usePlatformDefaultWidth = false)
    ) {
        AccountDetailScreenContent(
            userName = userName,
            accountDetail = accountDetail,
            onCloseClick = navigator::navigateUp,
            onShareClick = { bitmap ->
                scope.launch {
                    context.saveImage(bitmap)?.let { imageUri ->
                        context.startTextWithImageShareIntent(
                            imageUri = imageUri,
                            text = buildString {
                                appendLine("Name: " + userName.name)
                                appendLine("UPI ID: " + accountDetail.upiId)
                                appendLine("A/C No: " + accountDetail.accountNo)
                                appendLine("IFSC: " + accountDetail.accountNo)
                            }
                        )
                    }
                }
            }
        )
    }
}
