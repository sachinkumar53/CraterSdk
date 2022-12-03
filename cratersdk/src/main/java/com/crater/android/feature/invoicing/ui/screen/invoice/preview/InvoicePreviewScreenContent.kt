package com.crater.android.feature.invoicing.ui.screen.invoice.preview

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.crater.android.R
import com.crater.android.core.data.Resource
import com.crater.android.core.ui.CenteredTopAppBar
import com.crater.android.core.ui.FancyButton
import com.crater.android.core.ui.screen.EmptyScreen
import com.crater.android.core.ui.screen.LoadingScreen
import com.crater.android.core.ui.theme.AppTheme
import com.crater.android.core.ui.theme.CraterTheme
import com.crater.android.feature.invoicing.domain.model.*
import com.crater.android.feature.invoicing.ui.common.MenuButton
import com.crater.android.feature.invoicing.ui.screen.invoice.preview.component.Invoice
import dev.shreyaspatil.capturable.Capturable
import dev.shreyaspatil.capturable.controller.CaptureController
import dev.shreyaspatil.capturable.controller.rememberCaptureController

@Composable
fun InvoicePreviewScreenContent(
    onBackClick: () -> Unit,
    onSendClick: () -> Unit,
    onRemindClick: () -> Unit,
    onDeleteClick: () -> Unit,
    controller: CaptureController,
    onCaptured: (ImageBitmap?, Throwable?) -> Unit,
    uiState: InvoicePreviewUiState
) {


    Scaffold(
        topBar = {
            CenteredTopAppBar(
                title = stringResource(id = R.string.invoice_preview),
                onBackClick = onBackClick
            )
        }
    ) {

        when (val resource = uiState.resource) {
            is Resource.Success -> {
                SuccessContent(
                    detail = resource.data,
                    controller = controller,
                    onCaptured = onCaptured,
                    onSendClick = onSendClick,
                    onRemindClick = onRemindClick,
                    onDeleteClick = onDeleteClick,
                    qrBitmap = uiState.paymentInfo?.qrCode?.asImageBitmap(),
                    onDoneClick = onBackClick
                )
            }
            is Resource.Loading -> LoadingScreen()
            is Resource.Error -> EmptyScreen()
        }
    }
}

@Composable
private fun SuccessContent(
    detail: InvoicePreviewDetail,
    qrBitmap: ImageBitmap?,
    onCaptured: (ImageBitmap?, Throwable?) -> Unit,
    controller: CaptureController,
    onRemindClick: () -> Unit,
    onSendClick: () -> Unit,
    onDeleteClick: () -> Unit,
    onDoneClick: () -> Unit
) {
    Column(
        modifier = Modifier.padding(AppTheme.dimensions.spacingMedium),
        verticalArrangement = Arrangement.spacedBy(AppTheme.dimensions.spacingMedium)
    ) {
        Box(
            modifier = Modifier
                .weight(1f)
                .verticalScroll(rememberScrollState())
        ) {
            Capturable(controller = controller, onCaptured = onCaptured) {
                Invoice(detail = detail, qrBitmap = qrBitmap)
            }
        }

        if (!detail.isPaid) {
            MenuSection(
                modifier = Modifier.align(Alignment.CenterHorizontally),
                onRemindClick = onRemindClick,
                onDeleteClick = onDeleteClick,
                onSendClick = onSendClick
            )
        }
        FancyButton(
            onClick = onDoneClick,
            text = stringResource(id = R.string.done),
            modifier = Modifier.fillMaxWidth()
        )
    }
}

@Composable
private fun MenuSection(
    onSendClick: () -> Unit,
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
            iconPainter = painterResource(id = R.drawable.ic_send),
            label = stringResource(id = R.string.send_invoice),
            modifier = Modifier.width(88.dp),
            onClick = onSendClick
        )

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


@Preview(showSystemUi = true)
@Composable
fun InvoicePreviewScreenContentPreview() {
    CraterTheme {
        InvoicePreviewScreenContent(
            uiState = InvoicePreviewUiState(
                Resource.Success(
                    InvoicePreviewDetail(
                        "Invoice 1",
                        creationDate = "24/11/22",
                        dueDate = "30/11/22",
                        fromUserInfo = UserInfo(
                            name = "Rahul Verma",
                            phone = "789410123",
                            emailId = "rahul123@gmail.com"
                        ),
                        customerInfo = CustomerInfo(
                            name = "Aman Gupta",
                            phone = "9874015632",
                            emailId = "aman.gupta1@gmail.com",
                            address = "Robert Robertson, 1234 NW Bobcat Lane, St. Robert, USA",
                            gst = "AAAAA0000A"
                        ),
                        serviceInfo = ServiceInfo(
                            services = (1..10).map {
                                Service(
                                    name = "Service $it",
                                    price = (it * 100).toString(),
                                    sac = "45623$it",
                                    quantity = it
                                )
                            },
                            tax = 5.0,
                            taxType = null
                        ),
                        isPaid = true
                    )
                )
            ),
            onBackClick = {},
            onSendClick = {},
            onRemindClick = {},
            onDeleteClick = {},
            onCaptured = { _, _ -> },
            controller = rememberCaptureController()
        )
    }
}