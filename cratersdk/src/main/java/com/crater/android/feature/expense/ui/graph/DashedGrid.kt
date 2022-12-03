package com.crater.android.feature.expense.ui.graph

import androidx.compose.runtime.Stable
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.madrapps.plot.line.LinePlot

@Stable
fun DashedGrid(
    color: Color,
    steps: Int = 5,
    lineWidth: Dp = 2.dp
): LinePlot.Grid {
    return LinePlot.Grid(color = color) { rect, fl, fl2 ->
        val (left, top, right, bottom) = rect
        val availableHeight = bottom - top
        val offset = availableHeight / 4
        repeat(steps) {
            val y = bottom - (it * offset)
            drawLine(
                color = color,
                start = Offset(left, y),
                end = Offset(right, y),
                strokeWidth = lineWidth.toPx(),
                pathEffect = PathEffect.dashPathEffect(floatArrayOf(40f, 20f, 0f))
            )
        }
    }
}