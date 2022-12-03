package com.crater.android.core.ui.theme

import androidx.compose.runtime.*
import androidx.compose.ui.graphics.Color

@Stable
class AppColors(
    primary: Color = Color(0xFF15FFC7),
    background: Color = Color.Transparent,
    surface: Color = Color(0xFF25302B),
    surfaceVariant: Color = Color(0xFF1E2C28),
    error: Color = Color(0xFFFF4F4F),
    onPrimary: Color = Color.Black,
    textPrimary: Color = Color(0xFFF8FCFB),
    textSecondary: Color = Color(0xA8F8FCFB),
//    textTertiary: Color = Color(0xFFACAEB1),
    selected: Color = primary,
    unselected: Color = Color(0xFF8AAAA2),
    highlight: Color = Color(0xFF243931),
    divider: Color = Color(0xFF335C50)

) {
    var primary by mutableStateOf(primary, structuralEqualityPolicy())
        private set
    var background by mutableStateOf(background, structuralEqualityPolicy())
        private set
    var surface by mutableStateOf(surface, structuralEqualityPolicy())
        private set
    var surfaceVariant by mutableStateOf(surfaceVariant, structuralEqualityPolicy())
        private set
    var error by mutableStateOf(error, structuralEqualityPolicy())
        private set
    var onPrimary by mutableStateOf(onPrimary, structuralEqualityPolicy())
        private set
    var textPrimary by mutableStateOf(textPrimary, structuralEqualityPolicy())
        private set
    var textSecondary by mutableStateOf(textSecondary, structuralEqualityPolicy())
        private set
    /*var textTertiary by mutableStateOf(textTertiary, structuralEqualityPolicy())
        private set*/
    var selected by mutableStateOf(selected, structuralEqualityPolicy())
        private set
    var unselected by mutableStateOf(unselected, structuralEqualityPolicy())
        private set
    var highlight by mutableStateOf(highlight, structuralEqualityPolicy())
        private set
    var divider by mutableStateOf(divider, structuralEqualityPolicy())
        private set

    /**
     * Returns a copy of this Colors, optionally overriding some of the values.
     */
    fun copy(
        primary: Color = this.primary,
        background: Color = this.background,
        surface: Color = this.surface,
        surfaceVariant: Color = this.surfaceVariant,
        error: Color = this.error,
        onPrimary: Color = this.onPrimary,
        textPrimary: Color = this.textPrimary,
        textSecondary: Color = this.textSecondary,
//        textTertiary: Color = this.textTertiary,
        selected: Color = this.selected,
        unSelected: Color = this.unselected,
        highlight: Color = this.highlight,
        divider: Color = this.divider
    ): AppColors = AppColors(
        primary,
        background,
        surface,
        surfaceVariant,
        error,
        onPrimary,
        textPrimary,
        textSecondary,
//        textTertiary,
        selected,
        unSelected,
        highlight,
        divider
    )

    fun updateColorsFrom(other: AppColors) {
        primary = other.primary
        background = other.background
        surface = other.surface
        surfaceVariant = other.surfaceVariant
        error = other.error
        onPrimary = other.onPrimary
        textPrimary = other.textPrimary
        textSecondary = other.textSecondary
//        textTertiary = other.textTertiary
        selected = other.selected
        unselected = other.unselected
        highlight = other.highlight
        divider = other.divider
    }
}


internal val LocalColors = staticCompositionLocalOf { AppColors() }

