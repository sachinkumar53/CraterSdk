package com.crater.android.feature.invoicing.ui.screen.invoice.component

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.crater.android.R
import com.crater.android.core.ui.FancyReadOnlyTextField
import com.crater.android.core.ui.theme.AppTheme

@Composable
fun CustomerTextField(
    modifier: Modifier = Modifier,
    onAddClick: () -> Unit,
    onEditClick: () -> Unit,
    onDeleteClick: () -> Unit,
    value: String,
    isError: Boolean
) {
    FancyReadOnlyTextField(
        modifier = modifier,
        value = value,
        placeholder = stringResource(id = R.string.add_customer),
        trailingIcon = {
            if (value.isBlank()) {
                IconButton(
                    onClick = onAddClick,
                    modifier = Modifier.size(24.dp)
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_plus),
                        contentDescription = null,
                        modifier = Modifier.size(16.dp)
                    )
                }
            } else {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(AppTheme.dimensions.spacingSmall),
                    modifier = Modifier
                        .padding(end = AppTheme.dimensions.spacingExtraSmall)
                        .animateContentSize()
                ) {
                    IconButton(
                        onClick = onEditClick,
                        modifier = Modifier.requiredSize(24.dp)
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_edit),
                            contentDescription = null,
                            modifier = Modifier.size(18.dp)
                        )
                    }
                    IconButton(
                        onClick = onDeleteClick,
                        modifier = Modifier.requiredSize(24.dp)
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_delete),
                            contentDescription = null,
                            modifier = Modifier.size(18.dp)
                        )
                    }
                }
            }

        },
        isError = isError,
        onClick = onAddClick,
        darkenPlaceholder = false
    )
}
