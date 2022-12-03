package com.crater.android.feature.cash.ui.screen.detail

import android.graphics.Bitmap
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.crater.android.R
import com.crater.android.core.extension.showToast
import com.crater.android.core.ui.FancyButton
import com.crater.android.core.ui.theme.AppTheme
import com.crater.android.core.ui.theme.CraterTheme
import com.crater.android.data.model.profile.UserName
import com.crater.android.feature.cash.domain.model.AccountDetail
import com.crater.android.feature.cash.domain.model.Amount
import com.crater.android.utils.toBitmap


@Composable
fun AccountDetailScreenContent(
    onCloseClick: () -> Unit,
    onShareClick: (Bitmap) -> Unit,
    userName: UserName,
    accountDetail: AccountDetail
) {

    val qrBitmap = remember(accountDetail) { accountDetail.upiQr.toBitmap() }

    val clipboardManager = LocalClipboardManager.current
    val context = LocalContext.current

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxWidth()
            .padding(AppTheme.dimensions.spacingMedium)
            .clip(AppTheme.shapes.large)
            .background(AppTheme.colors.surface)
            .padding(AppTheme.dimensions.spacingMedium)
    ) {

        Box(modifier = Modifier.fillMaxWidth()) {
            IconButton(
                onClick = onCloseClick,
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .requiredSize(AppTheme.dimensions.iconSmall)
                    .padding(4.dp)
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_close),
                    contentDescription = null
                )
            }

            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = AppTheme.dimensions.spacingExtraSmall)
            ) {
                Text(
                    text = userName.name,
                    style = AppTheme.typography.headlineMedium
                )

                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = buildString {
                            append(stringResource(id = R.string.upi_id))
                            append(": ")
                            append(accountDetail.upiId)
                        },
                        modifier = Modifier.padding(end = 4.dp),
                        style = AppTheme.typography.bodySmall,
                        color = AppTheme.colors.textSecondary
                    )
                    IconButton(
                        onClick = {
                            clipboardManager.setText(AnnotatedString(text = accountDetail.upiId))
                            context.showToast(R.string.copied_to_clipboard)
                        },
                        modifier = Modifier
                            .size(26.dp)
                            .padding(4.dp)
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_copy),
                            contentDescription = null
                        )
                    }
                }
            }
        }
        if (qrBitmap != null) {
            Image(
                bitmap = qrBitmap.asImageBitmap(),
                contentDescription = null,
                modifier = Modifier
                    .padding(vertical = AppTheme.dimensions.spacingLarge)
                    .clip(AppTheme.shapes.small)
                    .size(128.dp)
                    .aspectRatio(1f)
            )
        }

        Column(
            modifier = Modifier
                .padding(top = AppTheme.dimensions.spacingSmall)
                .clip(MaterialTheme.shapes.medium)
                .fillMaxWidth()
                .border(
                    width = 1.dp,
                    color = AppTheme.colors.divider,
                    shape = AppTheme.shapes.medium
                )
                .padding(AppTheme.dimensions.spacingMedium)
        ) {
            Text(
                text = stringResource(R.string.account_details),
                style = AppTheme.typography.labelLarge
            )
            Spacer(modifier = Modifier.height(AppTheme.dimensions.spacingExtraSmall))
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_bank),
                    contentDescription = null,
                    modifier = Modifier
                        .size(AppTheme.dimensions.iconLarge)
                        .aspectRatio(1f),
                    tint = AppTheme.colors.primary
                )
                Spacer(modifier = Modifier.width(AppTheme.dimensions.spacingMedium))
                Column {
                    LabeledText(
                        label = stringResource(R.string.ac_no),
                        text = accountDetail.accountNo
                    )
                    LabeledText(label = stringResource(R.string.ifsc), text = accountDetail.ifsc)
                }
            }
        }

        FancyButton(
            onClick = {
                if (qrBitmap != null) {
                    onShareClick(qrBitmap)
                }
            },
            text = stringResource(R.string.share_account_details),
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    top = AppTheme.dimensions.spacingLarge,
                    bottom = AppTheme.dimensions.spacingTiny
                ),
            leading = {
                Icon(
                    painter = painterResource(id = R.drawable.ic_share),
                    contentDescription = null,
                    modifier = Modifier.padding(end = AppTheme.dimensions.spacingMedium)
                )
            }
        )

    }
}

@Composable
private fun LabeledText(
    label: String,
    text: String
) {
    Text(
        text = buildAnnotatedString {
            append(label)
            append(": ")
            withStyle(
                AppTheme.typography.labelMedium.copy(
                    color = AppTheme.colors.textPrimary
                ).toSpanStyle()
            ) {
                append(text)
            }
        },
        color = AppTheme.colors.textSecondary
    )
}

@Preview(showSystemUi = true)
@Composable
fun AccountDetailScreenContentPreview() {
    CraterTheme {
        Box(contentAlignment = Alignment.Center) {
            AccountDetailScreenContent(
                accountDetail = AccountDetail(
                    availableBalance = Amount(0.0),
                    accountNo = "848336595223668",
                    ifsc = "BANK0123456",
                    upiId = "myupi@abc",
                    upiQr = SAMPLE_QR_BASE64
                ),
                userName = UserName("Sachin", "Kumar"),
                onCloseClick = {},
                onShareClick = {}
            )
        }
    }
}

private const val SAMPLE_QR_BASE64 =
    "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AAAQ8888888888888888888888888888888888888888888888888888888888888888888888888888888888888888888888888888888888888888888888888888888888888888888888888888888888888888888888888888888888888888888888888888888888888888888888888888888888888888888888888888888888888888888888888888888888888888888888888888888888888888888888888888888888888888888888888888888888888888888888888888888888888888888888888888888888888888888888888888888888888888888888888888888888888888888888888888888888888888888888888888/8QAFBEBAAAAAAAAAAAAAAAAAAAAgP/aAAgBAwEBPxByf//EABQRAQAAAAAAAAAAAAAAAAAAAID/2gAIAQIBAT8Qcn//xAAUEAEAAAAAAAAAAAAAAAAAAACg/9oACAEBAAE/EGc//9k="