package com.crater.android.core.ui.components

import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource
import com.crater.android.R

@Composable
fun QuickAccessMenuIcon(
    onClick: () -> Unit
) {
    IconButton(
        onClick = onClick
    ) {
        Icon(painter = painterResource(id = R.drawable.ic_menu), contentDescription = null)
    }
}