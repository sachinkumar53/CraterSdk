package com.crater.android.feature.invoicing.ui.screen.invoice.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.crater.android.R
import com.crater.android.core.ui.theme.AppTheme
import com.crater.android.feature.invoicing.domain.model.CustomerInfo
import com.crater.android.feature.invoicing.domain.model.UserInfo

@Composable
fun InvoiceAddress(
    modifier: Modifier = Modifier,
    user: UserInfo,
    customer: CustomerInfo
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .then(modifier),
        horizontalArrangement = Arrangement.spacedBy(AppTheme.dimensions.spacingMedium)
    ) {
        Text(
            text = buildString {
                appendLine(stringResource(id = R.string.from1))
                appendLine(user.name)
                appendLine(user.phone)
                appendLineIfNotBlank(user.emailId)
            },
            modifier = Modifier.weight(1f)
        )

        Text(
            text = buildString {
                appendLine(stringResource(id = R.string.to))
                appendLine(customer.name)
                appendLine(customer.phone)
                appendLineIfNotBlank(customer.emailId)
                ifNotBlank(customer.address) {
                    appendLine("\nAddress:")
                    appendLine(it)
                }
                ifNotBlank(customer.gst) {
                    appendLine("\nGST: $it")
                }
            },
            modifier = Modifier.weight(1f)
        )
    }
}

private fun StringBuilder.appendLineIfNotBlank(line: String) {
    if (line.isNotBlank()) appendLine(line)
}

private fun StringBuilder.ifNotBlank(
    text: String,
    action: StringBuilder.(String) -> Unit
) {
    if (text.isNotBlank()) action(text)

}
