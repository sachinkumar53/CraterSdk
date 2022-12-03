package com.crater.android.feature.invoicing.ui.screen.invoice.preview.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.crater.android.R
import com.crater.android.core.ui.theme.AppTheme
import com.crater.android.feature.invoicing.domain.model.InvoicePreviewDetail
import com.crater.android.feature.invoicing.domain.model.ServiceInfo
import com.crater.android.feature.invoicing.ui.screen.invoice.component.InvoiceAddress
import com.crater.android.feature.invoicing.ui.screen.invoice.component.InvoiceHeader

@Composable
fun Invoice(
    modifier: Modifier = Modifier,
    detail: InvoicePreviewDetail,
    qrBitmap: ImageBitmap?
) {
    Surface(
        modifier = modifier.fillMaxWidth(),
        color = MaterialTheme.colors.surface,
        shape = MaterialTheme.shapes.large,
    ) {
        Column(
            modifier = Modifier.padding(AppTheme.dimensions.spacingMedium),
            verticalArrangement = Arrangement.spacedBy(AppTheme.dimensions.spacingMedium)
        ) {
            InvoiceHeader(
                invoiceNumber = detail.invoiceNumber,
                date = detail.creationDate,
                datePrefix = stringResource(id = R.string.created)
            )
            ProvideTextStyle(LocalTextStyle.current.copy(fontSize = 10.sp, lineHeight = 12.sp)) {
                InvoiceAddress(user = detail.fromUserInfo, customer = detail.customerInfo)
                ServiceTable(detail.serviceInfo)
                PaymentInstructions(detail.dueDate, qrBitmap)
                BrandWaterMark()
            }
        }
    }
}

@Composable
fun PaymentInstructions(dueDate: String, qrBitmap: ImageBitmap?) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(AppTheme.dimensions.spacingMedium)
    ) {
        Column(modifier = Modifier.weight(1f)) {
            Text(text = "Due date: $dueDate")
            Spacer(modifier = Modifier.height(AppTheme.dimensions.spacingMedium))
            Text(text = stringResource(id = R.string.payment_instructions))
            Spacer(modifier = Modifier.height(AppTheme.dimensions.spacingTiny))
            Text(text = stringResource(id = R.string.payment_instructions_desc))
        }



        Box(
            modifier = Modifier
                .size(88.dp)
                .aspectRatio(1f)
                .align(Alignment.CenterVertically),
            contentAlignment = Alignment.Center
        ) {
            qrBitmap?.let {
                Image(
                    bitmap = it,
                    contentDescription = null,
                    modifier = Modifier
                        .size(88.dp)
                        .aspectRatio(1f)
                        .background(Color.White)
                )
            }
        }
    }
}

@Composable
fun BrandWaterMark() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .alpha(0.5f),
        horizontalArrangement = Arrangement.End,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text = stringResource(id = R.string.powered_by))
        Spacer(modifier = Modifier.width(4.dp))
        Image(
            painter = painterResource(id = R.drawable.crater_logo),
            contentDescription = null,
            modifier = Modifier.height(10.dp)
        )
    }
}

@Composable
private fun ServiceTable(serviceInfo: ServiceInfo) {
    Column {
        Divider()
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 4.dp)
        ) {
            CenterText(
                text = stringResource(R.string.service),
                modifier = Modifier.weight(0.25f)
            )
            CenterText(
                text = stringResource(R.string.sac_code),
                modifier = Modifier.weight(0.2f)
            )
            CenterText(
                text = stringResource(R.string.qty),
                modifier = Modifier.weight(0.15f)
            )
            CenterText(
                text = stringResource(R.string.price),
                modifier = Modifier.weight(0.2f)
            )
            CenterText(
                text = stringResource(R.string.amount),
                modifier = Modifier.weight(0.2f)
            )
        }
        Divider()
        Spacer(modifier = Modifier.height(4.dp))
        serviceInfo.services.forEach {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 2.dp)
            ) {
                CenterText(
                    text = it.name,
                    modifier = Modifier.weight(0.25f)
                )
                CenterText(
                    text = it.sac ?: "",
                    modifier = Modifier.weight(0.2f)
                )
                CenterText(
                    text = it.quantity.toString(),
                    modifier = Modifier.weight(0.15f)
                )
                CenterText(
                    text = it.price,
                    modifier = Modifier.weight(0.2f)
                )
                CenterText(
                    text = it.totalPrice.toString(),
                    modifier = Modifier.weight(0.2f)
                )
            }
        }
        Spacer(modifier = Modifier.height(4.dp))
        Divider()
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = AppTheme.dimensions.spacingExtraSmall)
                .padding(end = AppTheme.dimensions.spacingMedium),
            horizontalArrangement = Arrangement.spacedBy(AppTheme.dimensions.spacingLarge)
        ) {
            Column(
                modifier = Modifier.weight(1f),
                horizontalAlignment = Alignment.End,
                verticalArrangement = Arrangement.spacedBy(2.dp)
            ) {
                if (serviceInfo.tax != 0.0) {
                    CenterText(text = stringResource(id = R.string.sub_total))
                    CenterText(text = serviceInfo.taxType.ifNullOrBlank {
                        stringResource(id = R.string.tax)
                    })
                }
                CenterText(text = stringResource(id = R.string.total))
            }

            Column(
                modifier = Modifier.width(IntrinsicSize.Max),
                horizontalAlignment = Alignment.End,
                verticalArrangement = Arrangement.spacedBy(2.dp)
            ) {
                if (serviceInfo.tax != 0.0) {
                    CenterText(text = serviceInfo.subTotalAmount.displayValue)
                    CenterText(text = serviceInfo.taxAmount.displayValue)
                }
                CenterText(text = serviceInfo.totalAmount.displayValue)
            }
        }
    }
}

private inline fun String?.ifNullOrBlank(function: () -> String): String {
    return if (this.isNullOrBlank())
        function()
    else this
}

@Composable
fun CenterText(text: String, modifier: Modifier = Modifier) {
    Text(
        text = text,
        modifier = modifier,
        maxLines = 1,
        textAlign = TextAlign.Center
    )
}
