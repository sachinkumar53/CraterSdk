package com.crater.android.feature.authentication.ui.screen.register.filldetails

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringArrayResource
import androidx.compose.ui.text.font.FontWeight
import com.crater.android.R
import com.crater.android.core.ui.theme.AppTheme


@Composable
fun StateListDialog(
    onItemClick: (String) -> Unit,
    onCloseClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val states = stringArrayResource(id = R.array.states)
    Column(
        modifier = Modifier
            .padding(horizontal = AppTheme.dimensions.spacingExtraSmall)
            .then(modifier)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(AppTheme.dimensions.appBarHeight)
                .padding(
                    start = AppTheme.dimensions.spacingMedium,
                    end = AppTheme.dimensions.spacingExtraSmall
                ),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "Select state",
                style = AppTheme.typography.title,
                fontWeight = FontWeight.SemiBold
            )
            IconButton(
                onClick = onCloseClick,
                modifier = Modifier.requiredSize(AppTheme.dimensions.iconDefault)
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_close),
                    contentDescription = null,
                    modifier = Modifier.padding(AppTheme.dimensions.spacingTiny)
                )
            }
        }

        LazyColumn(modifier = Modifier.fillMaxHeight()) {
            states.forEach {
                item {
                    Text(
                        text = it,
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {
                                onItemClick(it)
                                onCloseClick()
                            }
                            .padding(AppTheme.dimensions.spacingMedium)
                    )
                }
            }
        }
    }

}