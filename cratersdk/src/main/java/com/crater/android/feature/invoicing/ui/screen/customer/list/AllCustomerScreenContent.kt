package com.crater.android.feature.invoicing.ui.screen.customer.list

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.ContentAlpha
import androidx.compose.material.LocalContentColor
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import com.crater.android.core.ui.theme.AppTheme
import com.crater.android.feature.invoicing.domain.model.Customer


@Composable
fun AllCustomerScreenContent(
    onCustomerClick: (Customer) -> Unit,
    customers: List<Customer>
) {
    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(AppTheme.dimensions.spacingSmall),
        modifier = Modifier.padding(AppTheme.dimensions.spacingMedium)
    ) {
        items(customers) { customer ->
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(MaterialTheme.shapes.medium)
                    .background(AppTheme.colors.surfaceVariant)
                    .clickable {
                        onCustomerClick(customer)
                    }
                    .padding(AppTheme.dimensions.spacingMedium)
            ) {
                Text(text = customer.name)
                Text(
                    text = customer.phone,
                    style = MaterialTheme.typography.body2,
                    color = LocalContentColor.current.copy(alpha = ContentAlpha.medium)
                )
            }
        }
    }
}
