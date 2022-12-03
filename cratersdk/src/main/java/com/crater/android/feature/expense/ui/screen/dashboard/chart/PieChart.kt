package com.crater.android.feature.expense.ui.screen.dashboard.chart

import android.graphics.Typeface
import android.text.SpannableStringBuilder
import android.text.style.ForegroundColorSpan
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.text.buildSpannedString
import com.crater.android.R
import com.crater.android.core.ui.theme.AppTheme
import com.crater.android.feature.expense.domain.model.CategoryWiseExpense
import com.crater.android.utils.FormatterUtil
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.github.mikephil.charting.formatter.PercentFormatter
import java.time.YearMonth
import java.time.format.DateTimeFormatter


@OptIn(ExperimentalMaterialApi::class)
@Composable
fun PieChartCard(
    modifier: Modifier = Modifier,
    categoryWiseExpenses: List<CategoryWiseExpense>,
    colors: List<Int>,
    selectedMonth: YearMonth,
    onMonthChange: (YearMonth) -> Unit
) {
    val monthsList = rememberSaveable {
        (0 until 4).map { YearMonth.now().minusMonths(it.toLong()) }
    }
    var isDropDownVisible by remember { mutableStateOf(false) }

    @Stable
    fun getFormattedYearMonth(yearMonth: YearMonth): String {
        return yearMonth.format(DateTimeFormatter.ofPattern("MMMM yyyy"))
    }

    Box(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = AppTheme.dimensions.spacingSmall),
        contentAlignment = Alignment.Center
    ) {
        if (categoryWiseExpenses.isNotEmpty()) {
            val total = remember(categoryWiseExpenses) { categoryWiseExpenses.sumOf { it.expense } }
            val totalAmount by remember(total) {
                derivedStateOf { FormatterUtil.getFormattedAmount(total) }
            }
            val pieData by remember(total) {
                derivedStateOf {
                    categoryWiseExpenses.map {
                        PieEntry(it.expense.div(total).toFloat(), it.category.name)
                    }
                }
            }

            PieChartView(pieData, totalAmount, colors)

        } else {
            Text(text = stringResource(R.string.no_transactions))
        }

        ExposedDropdownMenuBox(
            modifier = Modifier
                .align(Alignment.TopEnd)
                .offset(x = AppTheme.dimensions.spacingMedium),
            expanded = isDropDownVisible,
            onExpandedChange = {
                isDropDownVisible = !isDropDownVisible
            }
        ) {
            Row(
                modifier = Modifier
                    .widthIn(144.dp)
                    .background(AppTheme.colors.highlight, CircleShape)
                    .border(1.dp, AppTheme.colors.divider, CircleShape),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.End
            ) {
                CompositionLocalProvider(
                    LocalContentColor provides AppTheme.colors.textSecondary
                ) {
                    Text(
                        text = getFormattedYearMonth(selectedMonth),
                        style = AppTheme.typography.labelLarge
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Icon(
                        painter = painterResource(id = R.drawable.ic_down_arrow_white),
                        contentDescription = null
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                }
            }


            DropdownMenu(
                expanded = isDropDownVisible,
                onDismissRequest = {
                    isDropDownVisible = false
                },
                modifier = Modifier.exposedDropdownSize()
            ) {
                monthsList.forEach { yearMonth ->
                    DropdownMenuItem(
                        onClick = {
                            onMonthChange(yearMonth)
                            isDropDownVisible = false
                        }
                    ) {
                        Text(
                            text = getFormattedYearMonth(yearMonth),
                            style = AppTheme.typography.labelLarge
                        )
                    }
                }
            }
        }
    }
}


@Composable
fun PieChartView(pieData: List<PieEntry>, totalAmount: String, colors: List<Int>) {
    AndroidView(
        modifier = Modifier
            .fillMaxWidth()
            .height(240.dp),
        factory = { context ->
            PieChart(context).apply {
                setUsePercentValues(true)
                setDrawCenterText(true)
                setHoleColor(Color.Transparent.toArgb())
                setCenterTextSize(16f)
                setCenterTextColor(Color.White.toArgb())
                setTransparentCircleAlpha(0)
                setCenterTextTypeface(Typeface.DEFAULT_BOLD)
                animateXY(300, 800)
                description.isEnabled = false
                legend.apply {
                    textColor = Color.White.toArgb()
                    orientation = Legend.LegendOrientation.VERTICAL
                    horizontalAlignment = Legend.LegendHorizontalAlignment.RIGHT
                    verticalAlignment = Legend.LegendVerticalAlignment.CENTER
                    setDrawInside(false)
                }
                setDrawEntryLabels(false)
                //setExtraOffsets(10f,0f,10f,0f)
            }
        },
        update = {
            it.centerText = buildSpannedString {
                appendLine("Total")
                setSpan(
                    ForegroundColorSpan(Color.Gray.toArgb()),
                    0,
                    5,
                    SpannableStringBuilder.SPAN_INCLUSIVE_EXCLUSIVE
                )
                appendLine(totalAmount)
            }
            val data = PieData(
                PieDataSet(
                    pieData, ""
                ).apply {
                    setColors(colors)
                }
            ).apply {
                setValueFormatter(PercentFormatter())
                setValueTextColor(Color.White.toArgb())
                setValueTextSize(11f)
            }
            it.data = data
            it.invalidate()
        }
    )
}

