package com.crater.android.feature.invoicing.ui.screen.invoice.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import com.crater.android.R
import com.crater.android.core.ui.theme.AppTheme

@Composable
fun AmountDueTextField(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    isError: Boolean,
    isReadOnly: Boolean = false
) {
    BasicTextField(
        value = value,
        onValueChange = onValueChange,
        decorationBox = { innerTextField ->
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = stringResource(id = R.string.amount_due))
                Spacer(modifier = Modifier.weight(1f))
                Text(text = "₹ ")
                Box(modifier = Modifier.width(IntrinsicSize.Max)) {
                    if (value.isEmpty()) {
                        Text(
                            text = "0",
                            color = if (isError) {
                                MaterialTheme.colors.error
                            } else {
                                LocalContentColor.current.copy(alpha = ContentAlpha.medium)
                            }
                        )
                    } else {
                        innerTextField()
                    }
                }
            }
        },
        textStyle = LocalTextStyle.current.copy(color = LocalContentColor.current),
        modifier = Modifier
            .clip(MaterialTheme.shapes.medium)
            .background(AppTheme.colors.surface)
            .padding(AppTheme.dimensions.spacingMedium)
            .then(modifier),
        singleLine = true,
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
        maxLines = 1,
        cursorBrush = SolidColor(MaterialTheme.colors.primary),
        readOnly = isReadOnly
    )

}
