package com.crater.android.core.ui

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.heightIn
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.crater.android.core.ui.theme.AppTheme

@Composable
fun FancyButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    text: String,
    trailing: (@Composable () -> Unit)? = null,
    leading: (@Composable () -> Unit)? = null,
    contentPadding: PaddingValues = ButtonDefaults.ContentPadding,
    enabled: Boolean = true
) {
    Button(
        onClick = onClick,
        modifier = modifier.heightIn(48.dp),
        shape = AppTheme.shapes.medium,
        contentPadding = contentPadding,
        enabled = enabled
    ) {
        leading?.invoke()
        Text(text = text)
        trailing?.invoke()
    }
}