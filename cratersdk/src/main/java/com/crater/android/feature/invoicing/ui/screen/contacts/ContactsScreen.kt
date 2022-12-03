package com.crater.android.feature.invoicing.ui.screen.contacts

import android.Manifest
import androidx.activity.result.launch
import androidx.compose.material.AlertDialog
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import com.crater.android.R
import com.crater.android.feature.destinations.EditCustomerScreenDestination
import com.crater.android.feature.invoicing.ui.mapper.toCustomer
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import dev.marcelpibi.permissionktx.compose.rememberLauncherForPermissionResult
import dev.marcelpinto.permissionktx.Permission

@Destination
@Composable
fun ContactsScreen(
    viewModel: ContactsViewModel = hiltViewModel(),
    navigator: DestinationsNavigator
) {
    val contactsList by viewModel.contactList.collectAsState()
    val searchQuery by viewModel.searchQuery.collectAsState()

    var showPermissionDialog by rememberSaveable { mutableStateOf(false) }

    val permissionLauncher = rememberLauncherForPermissionResult(
        type = Permission(Manifest.permission.READ_CONTACTS)
    ) { isGranted ->
        if (!isGranted) navigator.navigateUp()
        else viewModel.onPermissionGranted()
    }

    LaunchedEffect(Unit) {
        if (!permissionLauncher.type.status.isGranted()) {
            permissionLauncher.safeLaunch(
                onRequireRational = {
                    showPermissionDialog = true
                }
            )
        } else viewModel.onPermissionGranted()
    }

    ContactsScreenContent(
        onBackClick = navigator::navigateUp,
        onItemClick = { contact ->
            navigator.navigate(EditCustomerScreenDestination(contact.toCustomer()))
        },
        onQueryChanged = viewModel::onSearchQueryChanged,
        searchQuery = searchQuery,
        contacts = contactsList
    )


    if (showPermissionDialog) {
        ContactPermissionDialog(
            onDismissRequest = {
                showPermissionDialog = false
                navigator.navigateUp()
            },
            onAllowClick = {
                showPermissionDialog = false
                permissionLauncher.launch()
            }
        )
    }

}

@Composable
private fun ContactPermissionDialog(
    onDismissRequest: () -> Unit,
    onAllowClick: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismissRequest,
        confirmButton = {
            TextButton(onClick = onAllowClick) {
                Text(text = stringResource(id = R.string.allow_now))
            }
        },
        dismissButton = {
            TextButton(onClick = onDismissRequest) {
                Text(text = stringResource(id = R.string.maybe_later))
            }
        },
        title = {
            Text(text = stringResource(id = R.string.allow_contact_permission))
        },
        text = {
            Text(text = stringResource(id = R.string.allow_contact_permission_desc))
        }
    )
}