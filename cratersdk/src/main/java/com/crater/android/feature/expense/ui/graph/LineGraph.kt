package com.crater.android.feature.expense.ui.graph

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.crater.android.core.ui.theme.AppTheme
import com.crater.android.core.ui.theme.CraterTheme
import com.crater.android.utils.FormatterUtil
import com.madrapps.plot.line.DataPoint
import com.madrapps.plot.line.LineGraph
import com.madrapps.plot.line.LinePlot

@Composable
fun LineChart(
    modifier: Modifier = Modifier,
    dataPoints: List<DataPoint>,
    onSelectionStart: () -> Unit = {},
    onSelectionEnd: () -> Unit = {},
    onSelection: ((Float, List<DataPoint>) -> Unit)? = null
) {
    val primaryColor = AppTheme.colors.primary
    val axisColor = AppTheme.colors.unselected

    LineGraph(
        plot = LinePlot(
            listOf(
                LinePlot.Line(
                    dataPoints = dataPoints,
                    connection = LinePlot.Connection(
                        color = primaryColor,
                        strokeWidth = 2.5.dp
                    ),
                    intersection = LinePlot.Intersection(
                        color = primaryColor,
                        radius = 5.dp
                    ) { center, _ ->
                        drawCircle(primaryColor, 5.dp.toPx(), center)
                        drawCircle(Color.White, 3.dp.toPx(), center)
                    },
                    highlight = LinePlot.Highlight(
                        color = primaryColor,
                        radius = 4.dp
                    ) { center ->
                        drawCircle(primaryColor.copy(alpha = 0.4f), 7.dp.toPx(), center)
                        drawCircle(primaryColor, 5.dp.toPx(), center)
                        drawCircle(Color.White, 3.dp.toPx(), center)
                    },
                    areaUnderLine = LinePlot.AreaUnderLine { path ->
                        drawPath(
                            path = path,
                            brush = Brush.verticalGradient(
                                colors = listOf(
                                    primaryColor.copy(alpha = 0.2f),
                                    Color.Transparent
                                )
                            )
                        )
                    }
                )
            ),
            grid = DashedGrid(color = AppTheme.colors.divider),
            isZoomAllowed = false,
            yAxis = LinePlot.YAxis(
                steps = 5,
                paddingStart = 4.dp
            ) { min, offset, max ->
                for (it in 0 until 5) {
                    val value = it * offset + min
                    Text(
                        text = FormatterUtil.getFormattedNumber(value),
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        style = MaterialTheme.typography.caption,
                        color = axisColor
                    )
                }
            },
            xAxis = MonthAxis(
                unit = 0.39f,
                steps = 6,
                color = axisColor
            ),
            selection = LinePlot.Selection(
                highlight = LinePlot.Connection(
                    color = Color.LightGray.copy(alpha = 0.5f),
                    strokeWidth = 2.dp,
                    pathEffect = PathEffect.dashPathEffect(floatArrayOf(40f, 20f))
                )
            )
        ),
        modifier = modifier
            .fillMaxWidth()
            .requiredHeight(240.dp),
        onSelectionStart = onSelectionStart,
        onSelectionEnd = onSelectionEnd,
        onSelection = onSelection
    )
}


@Preview
@Composable
fun ChartPreview() {
    CraterTheme {
        LineChart(
            dataPoints = FakeDataPoints
        )
    }
}

@Stable
val FakeDataPoints = listOf(
    DataPoint(1f, 1085f),
    DataPoint(2f, 6274f),
    DataPoint(3f, 5996f),
    DataPoint(4f, 8545f),
    DataPoint(5f, 7578f),
)