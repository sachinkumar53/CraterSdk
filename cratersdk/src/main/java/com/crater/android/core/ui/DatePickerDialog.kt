package com.crater.android.core.ui

import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import com.crater.android.R
import com.vanpra.composematerialdialogs.MaterialDialog
import com.vanpra.composematerialdialogs.MaterialDialogState
import com.vanpra.composematerialdialogs.datetime.date.datepicker
import java.time.LocalDate

@Composable
fun DatePickerDialog(
    dialogState: MaterialDialogState,
    onDateSelect: (LocalDate) -> Unit,
    allowedDateValidator: (LocalDate) -> Boolean = { true }
) {
    MaterialTheme(colors = MaterialTheme.colors.copy(surface = Color(0xFF0A0F0E))) {
        MaterialDialog(
            dialogState = dialogState,
            buttons = {
                positiveButton(stringResource(R.string.ok))
                negativeButton(stringResource(R.string.cancel))
            }
        ) {
            datepicker(allowedDateValidator = allowedDateValidator) {
                onDateSelect(it)
            }
        }
    }
}


