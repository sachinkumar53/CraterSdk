package com.crater.android.core.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import com.crater.android.R
import com.crater.android.core.ui.theme.AppTheme

@Composable
fun CenteredTopAppBar(
    title: String,
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier,
    backgroundColor: Color = Color.Transparent,
    menu: (@Composable RowScope.() -> Unit)? = null
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(AppTheme.dimensions.appBarHeight)
            .background(backgroundColor)
            .padding(horizontal = AppTheme.dimensions.spacingExtraSmall)
            .then(modifier),
        contentAlignment = Alignment.Center
    ) {
        Row(
            modifier = Modifier.matchParentSize(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            IconButton(
                onClick = onBackClick,
                modifier = Modifier.size(AppTheme.dimensions.iconLarge)
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_left_arrow_white),
                    contentDescription = null
                )
            }
            if (menu != null) menu()
        }

        Text(
            text = title,
            style = AppTheme.typography.title,
            fontWeight = FontWeight.SemiBold
        )
    }
}

@Preview
@Composable
fun ToolbarPreview() {
    CenteredTopAppBar(
        onBackClick = {},
        title = "Title"
    )
}