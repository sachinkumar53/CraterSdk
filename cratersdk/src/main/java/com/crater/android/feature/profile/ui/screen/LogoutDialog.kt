package com.crater.android.feature.profile.ui.screen

import androidx.compose.material.Button
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.crater.android.R
import com.crater.android.core.ui.FancyAlertDialog
import com.crater.android.feature.profile.ui.navigation.ProfileNavGraph
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.result.ResultBackNavigator
import com.ramcosta.composedestinations.spec.DestinationStyle

@ProfileNavGraph
@Destination(style = DestinationStyle.Dialog::class)
@Composable
fun LogoutDialog(navigator: ResultBackNavigator<Boolean>) {

    FancyAlertDialog(
        onDismissRequest = navigator::navigateBack,
        positiveButton = {
            Button(
                onClick = { navigator.navigateBack(true) },
            ) {
                Text(stringResource(id = R.string.logout))
            }
        },
        negativeButton = {
            OutlinedButton(
                onClick = { navigator.navigateBack(false) }
            ) {
                Text(stringResource(id = R.string.cancel))
            }
        },
        content = {
            Text(stringResource(id = R.string.logout_message))
        },
        title = {
            Text(stringResource(id = R.string.logout))
        }
    )
}
