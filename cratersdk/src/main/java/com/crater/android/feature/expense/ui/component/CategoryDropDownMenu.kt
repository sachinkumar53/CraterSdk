package com.crater.android.feature.expense.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import com.crater.android.R
import com.crater.android.core.ui.theme.AppTheme
import com.crater.android.feature.expense.domain.model.ExpenseCategory


@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ExposedDropdownMenuBoxScope.CategoryDropDownMenu(
    modifier: Modifier = Modifier,
    expanded: Boolean,
    onDismissRequest: () -> Unit,
    onItemClick: (String) -> Unit
) {
    DropdownMenu(
        expanded = expanded,
        onDismissRequest = onDismissRequest,
        modifier = modifier
            .exposedDropdownSize()
            .heightIn(max = 240.dp)
            .background(AppTheme.colors.surfaceVariant)
    ) {
        ExpenseCategory.values().forEach {
            CategoryDropDownMenuItem(
                iconImageVector = it.iconImageVector,
                text = it.name,
                onClick = {
                    onItemClick(it.name)
                    onDismissRequest()
                }
            )
        }
    }
}


@Composable
fun CategoryDropDownMenuItem(
    iconImageVector: ImageVector,
    text: String,
    onClick: () -> Unit
) {
    DropdownMenuItem(
        onClick = onClick
    ) {
        Box(
            modifier = Modifier
                .clip(CircleShape)
                .size(28.dp)
                .background(colorResource(id = R.color.primary)),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = iconImageVector,
                contentDescription = null,
                modifier = Modifier.size(16.dp),
                tint = Color.Black
            )
        }
        Spacer(modifier = Modifier.width(16.dp))
        Text(
            text = text,
            modifier = Modifier.weight(1f)
        )
    }
}
