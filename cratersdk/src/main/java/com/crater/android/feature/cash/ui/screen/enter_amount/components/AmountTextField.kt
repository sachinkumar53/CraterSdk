package com.crater.android.feature.cash.ui.screen.enter_amount.components

import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.LocalTextStyle
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.sp
import com.crater.android.R
import com.crater.android.core.ui.FancyTextField

@Composable
fun AmountTextField(
    value: String,
    onValueChange: (String) -> Unit
) {
    FancyTextField(
        value = value,
        onValueChange = onValueChange,
        placeholder = {
            Text(text = stringResource(id = R.string.enter_amount))
        },
        leadingIcon = {
            Text(
                text = "\u20B9",
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp,
                color = MaterialTheme.colors.onSurface
            )
        },
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
        backgroundColor = Color(0xFF1B2724),
        textStyle = LocalTextStyle.current.copy(
            fontWeight = FontWeight.Bold,
            fontSize = 18.sp,
            color = MaterialTheme.colors.onSurface
        )
    )
}