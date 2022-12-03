package com.crater.android.core.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.ProvideTextStyle
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Constraints
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.crater.android.core.ui.theme.AppTheme

@Composable
fun FancyAlertDialog(
    modifier: Modifier = Modifier,
    onDismissRequest: () -> Unit,
    positiveButton: @Composable () -> Unit,
    negativeButton: @Composable () -> Unit,
    title: @Composable () -> Unit,
    content: @Composable () -> Unit
) {
    Dialog(
        onDismissRequest = onDismissRequest
    ) {
        Column(
            modifier = Modifier
                .background(
                    color = AppTheme.colors.surface,
                    shape = AppTheme.shapes.large
                )
                .padding(AppTheme.dimensions.spacingMedium)
                .then(modifier)
        ) {
            Box(
                modifier = Modifier.fillMaxWidth(),
                contentAlignment = Alignment.TopCenter
            ) {
                ProvideTextStyle(AppTheme.typography.title.copy(fontWeight = FontWeight.SemiBold)) {
                    title()
                }
            }

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .heightIn(128.dp),
                contentAlignment = Alignment.Center
            ) {
                content()
            }

            DialogButtonLayout(
                positiveButton = positiveButton,
                negativeButton = negativeButton,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(44.dp)
            )
        }
    }
}


@Composable
private fun DialogButtonLayout(
    modifier: Modifier = Modifier,
    positiveButton: @Composable () -> Unit,
    negativeButton: @Composable () -> Unit,
    spacing: Dp = AppTheme.dimensions.spacingMedium
) {
    Layout(
        content = {
            negativeButton()
            positiveButton()
        },
        modifier = modifier
    ) { measurables, constraints ->
        val childWidth = (constraints.maxWidth - spacing.roundToPx()) / 2
        val placeables = measurables.map {
            it.measure(
                Constraints(
                    minWidth = childWidth,
                    maxWidth = childWidth,
                    minHeight = constraints.maxHeight,
                    maxHeight = constraints.maxHeight
                )
            )
        }
        var x = 0
        layout(constraints.maxWidth, constraints.maxHeight) {
            placeables.forEach {
                it.place(x, 0)
                x += it.width + spacing.roundToPx()
            }
        }
    }
}