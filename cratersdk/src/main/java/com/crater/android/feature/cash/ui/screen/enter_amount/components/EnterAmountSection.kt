package com.crater.android.feature.cash.ui.screen.enter_amount.components

import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.crater.android.R
import com.crater.android.core.ui.theme.AppTheme

@Composable
fun EnterAmountSection(
    label: String,
    title: String,
    text: String,
    amount: String,
    onAmountChanged: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(AppTheme.dimensions.spacingMedium)
    ) {
        Spacer(modifier = Modifier.height(AppTheme.dimensions.spacingExtraSmall))

        Text(
            text = stringResource(id = R.string.enter_amount),
            style = AppTheme.typography.headlineMedium
        )

        Spacer(modifier = Modifier.height(AppTheme.dimensions.spacingExtraLarge))
        AccountInformation(
            label = label,
            title = title,
            text = text
        )
        Spacer(modifier = Modifier.height(AppTheme.dimensions.spacingLarge))

        AmountTextField(
            value = amount,
            onValueChange = onAmountChanged
        )
    }
}