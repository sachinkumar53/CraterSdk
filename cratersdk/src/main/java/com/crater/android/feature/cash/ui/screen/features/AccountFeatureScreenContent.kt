package com.crater.android.feature.cash.ui.screen.features

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringArrayResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.crater.android.R
import com.crater.android.core.ui.CenteredTopAppBar
import com.crater.android.core.ui.FancyButton
import com.crater.android.core.ui.FancyHeader
import com.crater.android.core.ui.theme.AppTheme
import com.crater.android.core.ui.theme.CraterTheme
import com.ramcosta.composedestinations.annotation.Destination

@Destination
@Composable
fun AccountFeatureScreenContent(
    onBackClick: () -> Unit,
    onCompleteKycClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val scrollState = rememberScrollState()
    Scaffold(
        modifier = modifier,
        topBar = {
            CenteredTopAppBar(
                onBackClick = onBackClick,
                title = stringResource(id = R.string.crater_cash)
            )
        },
        bottomBar = {
            FancyButton(
                onClick = onCompleteKycClick,
                text = stringResource(R.string.complete_kyc),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(AppTheme.dimensions.spacingMedium)
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .padding(horizontal = AppTheme.dimensions.spacingMedium)
                .verticalScroll(scrollState),
            verticalArrangement = Arrangement.spacedBy(AppTheme.dimensions.spacingSmall)
        ) {
            FancyHeader(title = { Text(text = stringResource(R.string.features_you_have)) })

            stringArrayResource(id = R.array.features_have).forEach {
                FeatureListItem(text = it)
            }

            Spacer(modifier = Modifier.height(AppTheme.dimensions.spacingExtraSmall))

            FancyHeader(
                title = { Text(text = stringResource(R.string.features_you_need)) },
                subtitle = {
                    Text(text = stringResource(R.string.features_you_need_desc))
                }
            )

            stringArrayResource(id = R.array.features_need).forEach {
                FeatureListItem(
                    text = it,
                    checkIconTint = MaterialTheme.colors.primary
                )
            }
        }
    }
}

@Composable
private fun FeatureListItem(
    text: String,
    checkIconTint: Color = LocalContentColor.current
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(AppTheme.dimensions.spacingMedium)
    ) {
        Icon(
            imageVector = Icons.Default.CheckCircle,
            contentDescription = null,
            tint = checkIconTint,
            modifier = Modifier.size(AppTheme.dimensions.iconSmall)
        )

        Text(text = text)
    }
}

@Preview(showSystemUi = true)
@Composable
private fun PreviewAccountFeatureScreenContent() {
    CraterTheme {
        AccountFeatureScreenContent(
            onCompleteKycClick = {},
            onBackClick = {}
        )
    }
}