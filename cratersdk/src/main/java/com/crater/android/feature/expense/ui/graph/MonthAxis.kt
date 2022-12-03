package com.crater.android.feature.expense.ui.graph

import androidx.compose.foundation.layout.offset
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.madrapps.plot.line.LinePlot
import java.time.Month
import java.time.format.TextStyle
import java.util.*

@Composable
fun MonthAxis(
    steps: Int = 6,
    unit: Float = 1f,
    extraOffset: Dp = 4.dp,
    color: Color
): LinePlot.XAxis {

    @Stable
    fun getShortMonthName(value: Int): String {
        val month = when {
            value < 1 -> Month.of(1)
            value > 12 -> Month.of(12)
            else -> Month.of(value)
        }
        return month.getDisplayName(TextStyle.SHORT, Locale.ENGLISH)
    }

    return LinePlot.XAxis(
        steps = steps,
        unit = unit,
    ) { min, offset, max ->
        for (it in 0 until steps) {
            val value = it * offset + min
            Text(
                text = getShortMonthName(value.toInt()),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                style = MaterialTheme.typography.caption,
                color = color,
                modifier = Modifier.offset(extraOffset)
            )
            if (value > max) {
                break
            }
        }
    }
}