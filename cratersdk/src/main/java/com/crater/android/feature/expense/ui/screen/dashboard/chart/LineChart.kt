package com.crater.android.feature.expense.ui.screen.dashboard.chart

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.crater.android.R
import com.crater.android.core.ui.theme.AppTheme
import com.crater.android.feature.expense.ui.graph.InteractiveLineChart
import com.madrapps.plot.line.DataPoint

@Composable
fun LineChart(
    modifier: Modifier = Modifier,
    dataPoints: List<DataPoint>,
    popValueFormatter: (Int, DataPoint) -> Pair<String, String>
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .border(
                color = AppTheme.colors.divider,
                width = 1.dp,
                shape = MaterialTheme.shapes.large
            )
            .padding(
                horizontal = AppTheme.dimensions.spacingMedium,
                vertical = AppTheme.dimensions.spacingSmall
            )
    ) {
        Text(
            text = stringResource(id = R.string.monthly_expenses),
            fontWeight = FontWeight.SemiBold
        )
        Spacer(modifier = Modifier.height(AppTheme.dimensions.spacingSmall))
        if (dataPoints.isNotEmpty()) {
            InteractiveLineChart(
                dataPoints = dataPoints,
                popValueFormatter = popValueFormatter
            )
        }
    }
}