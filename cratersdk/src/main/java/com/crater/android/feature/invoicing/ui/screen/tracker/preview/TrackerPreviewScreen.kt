package com.crater.android.feature.invoicing.ui.screen.tracker.preview

import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asAndroidBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.crater.android.R
import com.crater.android.core.common.UiState
import com.crater.android.core.data.Resource
import com.crater.android.core.extension.saveImage
import com.crater.android.core.extension.showToast
import com.crater.android.core.extension.startImageShareIntent
import com.crater.android.core.ui.CenteredTopAppBar
import com.crater.android.core.ui.FancyButton
import com.crater.android.core.ui.screen.EmptyScreen
import com.crater.android.core.ui.screen.LoadingScreen
import com.crater.android.core.ui.theme.AppTheme
import com.crater.android.core.ui.theme.CraterTheme
import com.crater.android.feature.cash.domain.model.Amount
import com.crater.android.feature.destinations.DeleteConfirmationDialogDestination
import com.crater.android.feature.invoicing.domain.model.CustomerInfo
import com.crater.android.feature.invoicing.domain.model.PaymentTrackerPreviewDetail
import com.crater.android.feature.invoicing.domain.model.UserInfo
import com.crater.android.feature.invoicing.ui.common.MenuButton
import com.crater.android.feature.invoicing.ui.model.PreviewArgs
import com.crater.android.feature.invoicing.ui.screen.invoice.component.InvoiceAddress
import com.crater.android.feature.invoicing.ui.screen.invoice.component.InvoiceHeader
import com.crater.android.feature.invoicing.ui.screen.invoice.preview.component.BrandWaterMark
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.ramcosta.composedestinations.result.NavResult
import com.ramcosta.composedestinations.result.ResultRecipient
import de.palm.composestateevents.EventEffect
import de.palm.composestateevents.StateEventWithContent
import de.palm.composestateevents.consumed
import dev.shreyaspatil.capturable.Capturable
import dev.shreyaspatil.capturable.controller.CaptureController
import dev.shreyaspatil.capturable.controller.rememberCaptureController
import kotlinx.coroutines.launch


@Destination(navArgsDelegate = PreviewArgs::class)
@Composable
fun PaymentTrackerPreviewScreen(
    viewModel: TrackerPreviewViewModel = hiltViewModel(),
    navigator: DestinationsNavigator,
    resultRecipient: ResultRecipient<DeleteConfirmationDialogDestination, Boolean>
) {
    val context = LocalContext.current
    val uiState by viewModel.uiState.collectAsState()
    val controller = rememberCaptureController()
    val coroutineScope = rememberCoroutineScope()


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

    Scaffold(
        topBar = {
            CenteredTopAppBar(
                title = stringResource(id = R.string.payment_track_preview),
                onBackClick = navigator::navigateUp
            )
        }
    ) {
        when (val resource = uiState.resource) {
            is Resource.Success -> {
                SuccessContent(
                    controller = controller,
                    detail = resource.data,
                    onRemindClick = { controller.capture() },
                    onDeleteClick = {
                        navigator.navigate(DeleteConfirmationDialogDestination())
                    },
                    onCaptured = { bitmap, error ->
                        if (bitmap != null) {
                            coroutineScope.launch {
                                context.saveImage(
                                    bitmap.asAndroidBitmap()
                                )?.also {
                                    context.startImageShareIntent(it)
                                }

                            }
                        }

                        if (error != null) {
                            context.showToast(R.string.unable_to_save_invoice)
                            Log.e("PaymentTrackerPreview", "Error capturing", error)
                        }
                    },
                    onDoneClick = navigator::navigateUp
                )
            }
            is Resource.Loading -> LoadingScreen()
            is Resource.Error -> EmptyScreen()
        }
    }

    if (uiState.isLoading) LoadingScreen()
}

@Composable
private fun SuccessContent(
    controller: CaptureController,
    onCaptured: (ImageBitmap?, Throwable?) -> Unit,
    onRemindClick: () -> Unit,
    onDeleteClick: () -> Unit,
    onDoneClick: () -> Unit,
    detail: PaymentTrackerPreviewDetail
) {
    Column(
        modifier = Modifier.padding(AppTheme.dimensions.spacingMedium),
        verticalArrangement = Arrangement.spacedBy(AppTheme.dimensions.spacingMedium)
    ) {
        Box(modifier = Modifier.weight(1f)) {
            Capturable(
                controller = controller,
                onCaptured = onCaptured
            ) {
                Surface(
                    modifier = Modifier.fillMaxWidth(),
                    color = MaterialTheme.colors.surface,
                    shape = MaterialTheme.shapes.large,
                ) {
                    Column(
                        modifier = Modifier.padding(AppTheme.dimensions.spacingMedium),
                        verticalArrangement = Arrangement.spacedBy(AppTheme.dimensions.spacingMedium)
                    ) {
                        InvoiceHeader(
                            invoiceNumber = detail.tackerNumber,
                            date = detail.dueDate,
                            datePrefix = stringResource(id = R.string.due)
                        )
                        ProvideTextStyle(LocalTextStyle.current.copy(fontSize = 10.sp)) {
                            InvoiceAddress(
                                user = detail.fromUserInfo,
                                customer = detail.customerInfo
                            )
                            Divider()
                            AmountSection(detail.amountDue, detail.amountPaid)
                            BrandWaterMark()
                        }
                    }
                }
            }
        }

        MenuSection(
            modifier = Modifier.align(Alignment.CenterHorizontally),
            onRemindClick = onRemindClick,
            onDeleteClick = onDeleteClick
        )

        FancyButton(
            onClick = onDoneClick,
            text = stringResource(id = R.string.done),
            modifier = Modifier.fillMaxWidth()
        )
    }
}

@Composable
private fun MenuSection(
    onRemindClick: () -> Unit,
    onDeleteClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(AppTheme.dimensions.spacingMedium),
        modifier = Modifier
            .height(IntrinsicSize.Max)
            .then(modifier)
    ) {
        MenuButton(
            iconPainter = painterResource(id = R.drawable.ic_remind),
            label = stringResource(id = R.string.remind),
            modifier = Modifier.widthIn(88.dp),
            onClick = onRemindClick
        )

        MenuButton(
            iconPainter = painterResource(id = R.drawable.ic_delete),
            label = stringResource(id = R.string.delete),
            modifier = Modifier.widthIn(88.dp),
            onClick = onDeleteClick
        )
    }
}

@Composable
private fun AmountSection(amountDue: Amount, amountPaid: Amount) {
    Column(
        verticalArrangement = Arrangement.spacedBy(AppTheme.dimensions.spacingExtraSmall)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(text = stringResource(id = R.string.amount_due))
            Text(text = amountDue.displayValue)
        }
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(text = stringResource(id = R.string.amount_paid))
            Text(text = amountPaid.displayValue)
        }
    }
}

data class TrackerPreviewUiState(
    val resource: Resource<PaymentTrackerPreviewDetail> = Resource.Loading(),
    override val successEvent: StateEventWithContent<String> = consumed(),
    override val errorEvent: StateEventWithContent<String> = consumed(),
    override val isLoading: Boolean = false
) : UiState<String>


@Preview(showSystemUi = true)
@Composable
fun PaymentTrackerPreviewScreenPreview() {
    CraterTheme {
        SuccessContent(
            detail = PaymentTrackerPreviewDetail(
                tackerNumber = "Tracker",
                dueDate = "24/11/22",
                fromUserInfo = UserInfo(
                    name = "Rahul Verma",
                    phone = "7891203456",
                    emailId = "rahul@xyz.com"
                ),
                customerInfo = CustomerInfo(
                    name = "Ram Prasad",
                    phone = "9812304567",
                    emailId = "ramu@abc.com",
                    address = "",
                    gst = ""
                ),
                amountDue = Amount(2000.0),
                amountPaid = Amount(0.0)
            ),
            onDeleteClick = {},
            onRemindClick = {},
            onCaptured = { _, _ -> },
            controller = rememberCaptureController(),
            onDoneClick = {}
        )
    }
}