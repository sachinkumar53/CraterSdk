package com.crater.android.feature.expense.ui.graph

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.crater.android.core.ui.theme.AppTheme
import com.crater.android.core.ui.theme.CraterTheme

@Composable
fun ChartPopup(
    modifier: Modifier = Modifier,
    isVisible: Boolean,
    xVal: String,
    yVal: String,
) {
    if (isVisible && xVal.isNotEmpty() && yVal.isNotEmpty()) {
        Column(
            modifier = modifier
                .background(
                    color = Color(0xFF243931),
                    shape = MaterialTheme.shapes.medium
                )
                .border(
                    width = 1.dp,
                    color = AppTheme.colors.divider,
                    shape = AppTheme.shapes.medium
                )
                .padding(AppTheme.dimensions.spacingExtraSmall),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = buildAnnotatedString {
                    withStyle(
                        SpanStyle(
                            color = MaterialTheme.colors.primary,
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold
                        )
                    )
                    {
                        append(xVal)
                    }
                    append("\n")
                    withStyle(
                        SpanStyle(
                            color = MaterialTheme.typography.body2.color,
                            fontSize = 12.sp
                        )
                    )
                    {
                        append(yVal)
                    }
                },
                textAlign = TextAlign.Center,
                lineHeight = 18.sp
            )
        }
    }
}

@Preview
@Composable
fun PopupPreview() {
    CraterTheme {
        ChartPopup(
            isVisible = true,
            xVal = "33.2k",
            yVal = "Feb 2022"
        )
    }
}