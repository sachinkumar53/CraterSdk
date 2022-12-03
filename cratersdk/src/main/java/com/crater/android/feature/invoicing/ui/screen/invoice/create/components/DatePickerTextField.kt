package com.crater.android.feature.invoicing.ui.screen.createinvoice.components

import androidx.compose.foundation.layout.Box
import androidx.compose.material.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource
import com.crater.android.R
import com.crater.android.core.ui.DatePickerDialog
import com.crater.android.core.ui.FancyReadOnlyTextField
import com.crater.android.core.util.DateFormatterUtil
import com.vanpra.composematerialdialogs.rememberMaterialDialogState

@Composable
fun DatePickerTextField(
    date: String,
    onDateChanged: (String) -> Unit,
    label: String,
    isError:Boolean = false
) {
    val dialogState = rememberMaterialDialogState()
    Box {
        FancyReadOnlyTextField(
            value = date,
            trailingIcon = {
                Icon(
                    painter = painterResource(id = R.drawable.ic_calendar),
                    contentDescription = null
                )
            },
            onClick = {
                dialogState.show()
            },
            placeholder = label,
            isError = isError
        )

        DatePickerDialog(
            dialogState = dialogState,
            onDateSelect = {
                onDateChanged(DateFormatterUtil.formatInShortStyle(it))
            }
        )
    }
}