package com.crater.android.feature.cash.ui.screen.dashboard.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.crater.android.R
import com.crater.android.core.ui.theme.AppTheme
import com.crater.android.feature.cash.domain.model.Amount

@Composable
fun BalanceDetails(
    modifier: Modifier = Modifier,
    onRequestMoneyClick: () -> Unit,
    onSelfTransferClick: () -> Unit,
    onCheckDetailsClick: () -> Unit,
    availableBalance: Amount,
) {
    Column(
        modifier = modifier
            .padding(horizontal = AppTheme.dimensions.spacingLarge)
            .clip(AppTheme.shapes.large)
            .fillMaxWidth()
            .background(AppTheme.colors.surface)
            .padding(
                horizontal = AppTheme.dimensions.spacingMedium,
                vertical = AppTheme.dimensions.spacingSmall
            ),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = stringResource(id = R.string.available_balance))
        Text(
            text = availableBalance.displayValue,
            style = AppTheme.typography.headlineLarge
        )

        Column(
            modifier = Modifier
                .width(IntrinsicSize.Max)
                .padding(vertical = AppTheme.dimensions.spacingMedium)
        ) {
            CustomButton(
                text = stringResource(id = R.string.request_money),
                iconPainter = painterResource(id = R.drawable.ic_request_money),
                onClick = onRequestMoneyClick,
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(AppTheme.dimensions.spacingExtraSmall))
            CustomButton(
                text = stringResource(id = R.string.send_to_self),
                iconPainter = painterResource(id = R.drawable.ic_self_transfer),
                onClick = onSelfTransferClick,
                modifier = Modifier.fillMaxWidth()
            )
        }

        OutlinedButton(
            onClick = onCheckDetailsClick,
            colors = ButtonDefaults.outlinedButtonColors(
                contentColor = MaterialTheme.colors.onSurface
            ),
            shape = AppTheme.shapes.circle,
            border = BorderStroke(1.dp, AppTheme.colors.divider),
            modifier = Modifier.height(28.dp),
            contentPadding = PaddingValues(horizontal = AppTheme.dimensions.spacingMedium)
        ) {
            ProvideTextStyle(AppTheme.typography.labelMedium) {
                Text(text = stringResource(id = R.string.account_details))
                Icon(
                    painter = painterResource(id = R.drawable.ic_arrow_next),
                    contentDescription = null,
                    modifier = Modifier
                        .padding(start = AppTheme.dimensions.spacingExtraSmall)
                        .size(10.dp)
                )
            }
        }

    }

}
