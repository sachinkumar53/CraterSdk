package com.crater.android.core.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.crater.android.R
import com.crater.android.core.ui.theme.AppTheme

@Composable
fun PhoneTextField(
    value: String,
    onValueChange: (String) -> Unit,
    isError: Boolean = false
) {
    FancyTextField(
        value = value,
        onValueChange = { if (it.length <= 10) onValueChange(it) },
        placeholder = { Text(text = stringResource(id = R.string.phone_number)) },
        leadingIcon = {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(
                    start = AppTheme.dimensions.spacingMedium,
                    end = AppTheme.dimensions.spacingSmall
                ),
                horizontalArrangement = Arrangement.spacedBy(AppTheme.dimensions.spacingSmall)
            ) {
                Text(text = "+91")
                Divider(
                    modifier = Modifier
                        .width(1.dp)
                        .height(24.dp)
                )
            }
        },
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
        isError = isError
    )
}