package com.crater.android.feature.invoicing.ui.screen.tracker.component

import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource
import com.crater.android.R
import com.crater.android.core.ui.FancyListItem
import com.crater.android.feature.invoicing.domain.model.PaymentTrackerInfo

@Composable
fun PaymentTrackerListItem(
    info: PaymentTrackerInfo,
    onItemClick: () -> Unit
) {

    FancyListItem(
        icon = {
            Icon(
                painter = painterResource(id = R.drawable.ic_invoice),
                contentDescription = null
            )
        },
        text = { Text(info.customerName) },
        secondaryText = { Text(info.id) },
        trailingText = { Text(info.amount.displayValue) },
        onClick = onItemClick
    )

}