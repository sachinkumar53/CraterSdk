package com.crater.android.core.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.crater.android.R
import com.crater.android.core.ui.theme.AppDialogTheme
import com.crater.android.core.ui.theme.AppTheme
import com.crater.android.core.ui.theme.LocalColors
import com.crater.android.core.ui.theme.LocalShapes

@Composable
fun FancyDialog(
    onDismissRequest: () -> Unit,
    title: String? = null,
    shape: Shape = LocalShapes.current.large,
    properties: DialogProperties = DialogProperties(),
    content: @Composable ColumnScope.() -> Unit
) {
    Dialog(
        onDismissRequest = onDismissRequest,
        properties = properties
    ) {
        AppDialogTheme {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .clip(shape)
                    .fillMaxWidth()
                    .background(LocalColors.current.background)
                    .padding(horizontal = AppTheme.dimensions.spacingMedium)
                    .padding(bottom = AppTheme.dimensions.spacingMedium)
            ) {
                if (title != null) {
                    TitleBar(onBackClick = onDismissRequest, title = title)
                } else {
                    Spacer(modifier = Modifier.height(AppTheme.dimensions.spacingMedium))
                }
                content()
            }
        }
    }
}

@Composable
private fun TitleBar(
    onBackClick: () -> Unit,
    title: String
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(AppTheme.dimensions.appBarHeight),
        contentAlignment = Alignment.CenterEnd
    ) {

        Text(
            text = title,
            modifier = Modifier.align(Alignment.Center),
            style = AppTheme.typography.title,
            fontWeight = FontWeight.SemiBold
        )
        IconButton(
            onClick = onBackClick,
            modifier = Modifier.offset(12.dp)
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_close),
                contentDescription = null,
                modifier = Modifier.size(18.dp),
                tint = Color.White,
            )
        }
    }
}
