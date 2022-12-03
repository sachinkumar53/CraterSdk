package com.crater.android.core.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.requiredSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.CheckCircle
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.crater.android.core.ui.icons.Circle
import com.crater.android.core.ui.theme.AppTheme
import com.crater.android.core.ui.theme.CraterTheme

@Composable
fun FancyCheckBox(
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
            .requiredSize(30.dp)
            .clip(CircleShape)
            .clickable {
                onCheckedChange(!checked)
            }
    ) {
        Icon(
            modifier = Modifier.requiredSize(22.dp),
            imageVector = if (checked) {
                Icons.Rounded.CheckCircle
            } else Icons.Outlined.Circle,
            contentDescription = null,
            tint = if (checked) {
                AppTheme.colors.selected
            } else AppTheme.colors.unselected.copy(alpha = 0.6f)
        )
    }
}

@Preview
@Composable
fun CheckBoxPreview() {
    var checked by remember { mutableStateOf(false) }
    CraterTheme {
        FancyCheckBox(
            checked = checked,
            onCheckedChange = { checked = it }
        )
    }
}