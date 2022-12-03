package com.crater.android.feature.cash.ui.screen.dashboard.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.LocalContentColor
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.crater.android.R
import com.crater.android.core.ui.theme.AppTheme
import com.crater.android.core.ui.theme.CraterTheme

@Composable
fun CustomButton(
    text: String,
    iconPainter: Painter,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    shape: Shape = AppTheme.shapes.small,
    backgroundColor: Color = AppTheme.colors.primary,
    innerPadding: PaddingValues = PaddingValues(horizontal = 12.dp, vertical = 8.dp),
    textStyle: TextStyle = AppTheme.typography.button
) {
    CompositionLocalProvider(LocalContentColor provides textStyle.color) {
        Row(
            modifier = modifier
                .clip(shape)
                .background(backgroundColor)
                .clickable(onClick = onClick)
                .padding(innerPadding),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Icon(
                painter = iconPainter,
                contentDescription = null,
                modifier = Modifier.size(18.dp)
            )
            Spacer(modifier = Modifier.width(AppTheme.dimensions.spacingExtraSmall))
            Text(text = text, style = textStyle)
        }
    }
}

@Preview
@Composable
fun ButtonPreview() {
    CraterTheme {
        Box {
            CustomButton(
                text = "Request to money",
                iconPainter = painterResource(id = R.drawable.ic_request_money),
                onClick = { /*TODO*/ }
            )
        }
    }
}