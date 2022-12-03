package com.crater.android.feature.authentication.ui.common

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.ClickableText
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import com.crater.android.core.ui.theme.AppTheme

@Composable
fun PartiallyClickableText(
    simpleText: String,
    clickableText: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val annotatedText = buildAnnotatedString {
        withStyle(SpanStyle(color = AppTheme.colors.textPrimary)) { append(simpleText) }
        append(" ")
        pushStringAnnotation(tag = "clickable", annotation = "clickable")
        withStyle(
            SpanStyle(fontWeight = FontWeight.Bold, color = AppTheme.colors.textPrimary)
        ) {
            append(clickableText)
        }
        pop()
    }

    ClickableText(
        text = annotatedText,
        onClick = { offset ->
            annotatedText.getStringAnnotations(
                tag = "clickable",
                start = offset,
                end = offset
            ).firstOrNull()?.let { onClick() }
        },
        style = AppTheme.typography.labelLarge,
        modifier = Modifier
            .padding(bottom = AppTheme.dimensions.spacingLarge)
            .then(modifier)
    )
}