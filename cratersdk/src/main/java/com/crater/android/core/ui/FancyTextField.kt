package com.crater.android.core.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import com.crater.android.core.ui.theme.AppTheme
import com.crater.android.core.ui.theme.LocalColors

@Composable
fun FancyTextField(
    modifier: Modifier = Modifier,
    value: String,
    onValueChange: (String) -> Unit,
    placeholder: (@Composable () -> Unit)? = null,
    leadingIcon: (@Composable () -> Unit)? = null,
    trailingIcon: (@Composable () -> Unit)? = null,
    enabled: Boolean = true,
    readOnly: Boolean = false,
    isError: Boolean = false,
    backgroundColor: Color = LocalColors.current.surface,
    textStyle: TextStyle = LocalTextStyle.current,
    colors: TextFieldColors = TextFieldDefaults.textFieldColors(
        backgroundColor = backgroundColor,
        unfocusedIndicatorColor = Color.Transparent,
        focusedIndicatorColor = Color.Transparent,
        placeholderColor = if (isError) MaterialTheme.colors.error
        else LocalContentColor.current.copy(alpha = ContentAlpha.medium),
        errorIndicatorColor = Color.Transparent,
        textColor = if (isError) MaterialTheme.colors.error
        else LocalContentColor.current
    ),
    maxLines: Int = 1,
    keyboardOptions: KeyboardOptions = KeyboardOptions(),
    keyboardActions: KeyboardActions = KeyboardActions()
) {

    val leading: (@Composable () -> Unit)? = if (leadingIcon != null) {
        {
            CompositionLocalProvider(LocalContentAlpha provides ContentAlpha.high) {
                leadingIcon()
            }
        }
    } else null

    val trailing: (@Composable () -> Unit)? = if (trailingIcon != null) {
        {
            CompositionLocalProvider(LocalContentAlpha provides ContentAlpha.high) {
                trailingIcon()
            }
        }
    } else null

    TextField(
        value = value,
        onValueChange = onValueChange,
        colors = colors,
        placeholder = placeholder,
        modifier = Modifier
            .clip(MaterialTheme.shapes.medium)
            .fillMaxWidth()
            .background(backgroundColor)
            //.padding(horizontal = 8.dp)
            .then(modifier),
        trailingIcon = trailing,
        enabled = enabled,
        readOnly = readOnly,
        isError = isError,
        shape = MaterialTheme.shapes.medium,
        leadingIcon = leading,
        keyboardOptions = keyboardOptions,
        keyboardActions = keyboardActions,
        maxLines = maxLines,
        textStyle = textStyle
    )

}

@Composable
fun FancyReadOnlyTextField(
    modifier: Modifier = Modifier,
    value: String,
    placeholder: String,
    trailingIcon: (@Composable () -> Unit)? = null,
    isError: Boolean = false,
    onClick: () -> Unit,
    backgroundColor: Color = LocalColors.current.surface,
    darkenPlaceholder: Boolean = true
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .heightIn(48.dp)
            .clip(MaterialTheme.shapes.medium)
            .background(backgroundColor)
            .clickable(onClick = onClick)
            .padding(
                top = AppTheme.dimensions.spacingMedium,
                start = AppTheme.dimensions.spacingMedium,
                end = AppTheme.dimensions.spacingSmall,
                bottom = AppTheme.dimensions.spacingMedium,
            ),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = value.ifEmpty { placeholder },
            color = when {
                isError -> AppTheme.colors.error
                value.isBlank() && darkenPlaceholder -> LocalContentColor.current.copy(alpha = ContentAlpha.medium)
                else -> LocalContentColor.current
            },
            modifier = Modifier.weight(1f)
        )
        Spacer(modifier = Modifier.width(AppTheme.dimensions.spacingMedium))
        if (trailingIcon != null)
            trailingIcon()

    }
}