package com.crater.android.core.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

fun Modifier.gradientCard(): Modifier = background(
    brush = Brush.horizontalGradient(listOf(Color(0x6684FFCB), Color(0x3376FFC6))),
    shape = RoundedCornerShape(16.dp)
)