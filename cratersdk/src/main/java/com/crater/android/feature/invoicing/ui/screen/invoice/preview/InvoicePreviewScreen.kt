package com.crater.android.feature.invoicing.ui.screen.invoice.preview

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.graphics.asAndroidBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import com.crater.android.R
import com.crater.android.core.common.UiState
import com.crater.android.core.data.Resource
import com.crater.android.core.extension.saveImage
import com.crater.android.core.extension.showToast
import com.crater.android.core.extension.startImageShareIntent
import com.crater.android.core.extension.startTextWithImageShareIntent
import com.crater.android.core.ui.screen.LoadingScreen
import com.crater.android.feature.destinations.DeleteConfirmationDialogDestination
import com.crater.android.feature.invoicing.domain.model.InvoicePreviewDetail
import com.crater.android.feature.invoicing.domain.model.PaymentInfo
import com.crater.android.feature.invoicing.ui.model.PreviewArgs
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.ramcosta.composedestinations.result.NavResult
import com.ramcosta.composedestinations.result.ResultRecipient
import de.palm.composestateevents.EventEffect
import de.palm.composestateevents.StateEventWithContent
import de.palm.composestateevents.consumed
import dev.shreyaspatil.capturable.controller.rememberCaptureController
import kotlinx.coroutines.launch

@Destination(navArgsDelegate = PreviewArgs::class)
@Composable
fun InvoicePreviewScreen(
    viewModel: InvoicePreviewViewModel = hiltViewModel(),
    navigator: DestinationsNavigator,
    resultRecipient: ResultRecipient<DeleteConfirmationDialogDestination, Boolean>
) {

    val uiState by viewModel.uiState.collectAsState()
    val controller = rememberCaptureController()
    val coroutineScope = rememberCoroutineScope()
    val context = LocalContext.current

    resultRecipient.onNavResult {
        if (it is NavResult.Value && it.value) {
            viewModel.onDeleteClick()
        }
    }

    EventEffect(
        event = uiState.successEvent,
        onConsumed = viewModel::onDeleteSuccessEventConsumed,
        action = {
            context.showToast(it)
            navigator.navigateUp()
        }
    )

    EventEffect(
        event = uiState.errorEvent,
        onConsumed = viewModel::onErrorEventConsumed,
        action = context::showToast
    )

    InvoicePreviewScreenContent(
        onBackClick = navigator::navigateUp,
        uiState = uiState,
        onRemindClick = {
            controller.capture()
        },
        onSendClick = {
            if (uiState.paymentInfo == null) {
                viewModel.generateUpiLink { controller.capture() }
            } else {
                controller.capture()
            }
        },
        onDeleteClick = {
            navigator.navigate(DeleteConfirmationDialogDestination())
        }, onCaptured = { bitmap, error ->
            if (bitmap != null) {
                coroutineScope.launch {
                    context.saveImage(bitmap.asAndroidBitmap())?.also {
                        val paymentLink = uiState.paymentInfo?.upiLink
                        if (paymentLink.isNullOrBlank()) {
                            context.startImageShareIntent(it)
                        } else {
                            context.startTextWithImageShareIntent(
                                imageUri = it,
                                text = "Payment link: $paymentLink"
                            )
                        }
                    }

                }
            }

            if (error != null) {
                context.showToast(R.string.unable_to_save_invoice)
                Log.e("PaymentTrackerPreview", "Error capturing", error)
            }
        },
        controller = controller
    )

    if (uiState.isLoading) LoadingScreen()
}

data class InvoicePreviewUiState(
    val resource: Resource<InvoicePreviewDetail> = Resource.Loading(),
    val paymentInfo: PaymentInfo? = null,
    override val successEvent: StateEventWithContent<String> = consumed(),
    override val errorEvent: StateEventWithContent<String> = consumed(),
    override val isLoading: Boolean = false
) : UiState<String>