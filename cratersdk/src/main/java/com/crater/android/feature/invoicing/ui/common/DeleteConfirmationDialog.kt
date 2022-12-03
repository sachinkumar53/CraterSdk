package com.crater.android.feature.invoicing.ui.common

import androidx.compose.material.Button
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.crater.android.R
import com.crater.android.core.ui.FancyAlertDialog
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.result.ResultBackNavigator
import com.ramcosta.composedestinations.spec.DestinationStyle

@Destination(style = DestinationStyle.Dialog::class)
@Composable
fun DeleteConfirmationDialog(
    resultBackNavigator: ResultBackNavigator<Boolean>
) {
    FancyAlertDialog(
        onDismissRequest = resultBackNavigator::navigateBack,
        positiveButton = {
            Button(
                onClick = { resultBackNavigator.navigateBack(true) }
            ) {
                Text(text = stringResource(id = R.string.delete))
            }
        },
        negativeButton = {
            OutlinedButton(
                onClick = { resultBackNavigator.navigateBack(false) }
            ) {
                Text(text = stringResource(id = R.string.cancel))
            }
        },
        title = {
            Text(text = stringResource(id = R.string.delete))
        },
        content = {
            Text(text = stringResource(id = R.string.delete_invoice_message))
        }
    )
}