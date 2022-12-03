package com.crater.android.feature.expense.ui.component

import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import com.crater.android.R
import com.crater.android.feature.destinations.AddExpenseScreenDestination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@Composable
fun AddExpenseFab(
    navigator: DestinationsNavigator
) {
    FloatingActionButton(
        onClick = {
            navigator.navigate(AddExpenseScreenDestination)
        },
        backgroundColor = colorResource(id = R.color.primary)
    ) {
        Icon(
            imageVector = Icons.Rounded.Add,
            contentDescription = null,
            tint = Color.Black
        )
    }
}