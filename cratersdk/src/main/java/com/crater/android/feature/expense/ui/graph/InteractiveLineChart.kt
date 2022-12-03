package com.crater.android.feature.expense.ui.graph

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.tooling.preview.Preview
import com.crater.android.core.ui.theme.CraterTheme
import com.madrapps.plot.line.DataPoint

@Composable
fun InteractiveLineChart(
    dataPoints: List<DataPoint>,
    popValueFormatter: (Int, DataPoint) -> Pair<String, String>
) {
    var isPopupVisible by remember { mutableStateOf(false) }
    var popUpInfo by remember { mutableStateOf(Pair("", "")) }
    var xOffset by remember { mutableStateOf(0f) }
    var totalWidth by remember { mutableStateOf(0) }
    var popupWidth by remember { mutableStateOf(0) }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .onGloballyPositioned {
                totalWidth = it.size.width
            }
    ) {
        LineChart(
            dataPoints = dataPoints,
            onSelectionStart = {
                isPopupVisible = true
            },
            onSelectionEnd = {
                isPopupVisible = false
            },
            onSelection = { offset, points ->
                val width = popupWidth.toFloat()
                val xCenter = when {
                    offset + width / 2f > totalWidth -> totalWidth - width
                    offset - width / 2f < 0f -> 0f
                    else -> offset - width / 2f
                }
                xOffset = xCenter

                val point = points[0]
                val index = dataPoints.indexOf(point)
                popUpInfo = popValueFormatter(index, point)
            }
        )

        ChartPopup(
            isVisible = isPopupVisible,
            xVal = popUpInfo.first,
            yVal = popUpInfo.second,
            modifier = Modifier
                .onGloballyPositioned {
                    popupWidth = it.size.width
                }
                .graphicsLayer(translationX = xOffset)
        )
    }
}

@Preview
@Composable
fun InteractiveChartPreview() {
    CraterTheme {
        InteractiveLineChart(
            dataPoints = FakeDataPoints,
            popValueFormatter = { _, p ->
                p.x.toString() to p.y.toString()
            }
        )
    }
}