package com.crater.android.core.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.material.DropdownMenu
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.window.PopupProperties
import com.crater.android.core.ui.theme.AppDialogTheme
import com.crater.android.core.ui.theme.AppTheme

@Composable
fun FancyDropDownMenu(
    modifier: Modifier = Modifier,
    expanded: Boolean,
    onDismissRequest: () -> Unit,
    offset: DpOffset = DpOffset.Zero,
    properties: PopupProperties = PopupProperties(),
    content: @Composable ColumnScope.() -> Unit
) {
    AppDialogTheme {
        DropdownMenu(
            expanded = expanded,
            modifier = Modifier
                .background(AppTheme.colors.background)
                .then(modifier),
            onDismissRequest = onDismissRequest,
            content = content,
            offset = offset,
            properties = properties
        )
    }
}