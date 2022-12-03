package com.crater.android.core.util

import android.content.Context
import androidx.annotation.StringRes
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource

sealed interface UiText {
    class DynamicSting(val value: String) : UiText
    class StringResource(@StringRes val resId: Int) : UiText

    @Composable
    fun asString(): String {
        return when (this) {
            is DynamicSting -> value
            is StringResource -> stringResource(id = resId)
        }
    }

    fun asString(context: Context): String {
        return when (this) {
            is DynamicSting -> value
            is StringResource -> context.getString(resId)
        }
    }

}