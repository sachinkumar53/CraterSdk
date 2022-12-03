package com.crater.android.feature.invoicing.ui.common

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import com.crater.android.core.ui.theme.AppTheme
import com.crater.android.feature.invoicing.domain.model.Customer

@Composable
fun CustomerListItem(
    onClick: () -> Unit,
    customer: Customer
) {
    Row(
        modifier = Modifier
            .clip(MaterialTheme.shapes.medium)
            .fillMaxWidth()
            .background(Color(0xFF243931))
            .clickable { onClick() }
            .padding(AppTheme.dimensions.spacingMedium)
    ) {
        Text(text = customer.name)
    }

}