package com.crater.android.core.ui.theme

import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Color


@Composable
fun CraterTheme(
    colors: AppColors = AppTheme.colors,
    typography: AppTypography = AppTheme.typography,
    shapes: AppShapes = AppTheme.shapes,
    dimensions: AppDimensions = AppTheme.dimensions,
    content: @Composable () -> Unit
) {
    val rememberedColors = remember {
        // Explicitly creating a new object here so we don't mutate the initial [colors]
        // provided, and overwrite the values set in it.
        colors.copy()
    }.apply { updateColorsFrom(colors) }

    MaterialTheme(
        colors = darkColors(
            background = Color.Transparent,
            primary = colors.primary,
            surface = colors.surface,
            error = colors.error,
            onPrimary = colors.onPrimary,
            onSurface = colors.textPrimary
        ),
        shapes = Shapes(
            small = shapes.small,
            medium = shapes.medium,
            large = shapes.large
        ),
        typography = Typography(
            defaultFontFamily = typography.defaultFontFamily,
            button = typography.button,
            body1 = typography.bodyMedium,
            body2 = typography.bodySmall
        )
    ) {
        CompositionLocalProvider(
            LocalColors provides rememberedColors,
            LocalTypography provides typography,
            LocalShapes provides shapes,
            LocalDimensions provides dimensions,
            LocalContentColor provides rememberedColors.textPrimary
        ) {
            ProvideTextStyle(LocalTypography.current.bodyMedium) {
                content()
            }
        }
    }
}