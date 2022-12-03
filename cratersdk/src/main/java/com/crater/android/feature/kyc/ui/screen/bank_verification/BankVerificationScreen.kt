package com.crater.android.feature.kyc.ui.screen.bank_verification

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.crater.android.R
import com.crater.android.core.ui.CenteredTopAppBar
import com.crater.android.core.ui.FancyButton
import com.crater.android.core.ui.FancyTextField
import com.crater.android.core.ui.screen.LoadingScreen
import com.crater.android.core.ui.theme.AppTheme
import com.crater.android.core.ui.theme.CraterTheme
import com.crater.android.feature.destinations.BankVerificationResultDestination
import com.crater.android.feature.kyc.ui.model.AccountNumber
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.ramcosta.composedestinations.navigation.EmptyDestinationsNavigator
import de.palm.composestateevents.EventEffect

@Destination
@Composable
fun BankVerificationScreen(
    viewModel: BankVerificationViewModel = hiltViewModel(),
    navigator: DestinationsNavigator
) {
    val uiState by viewModel.uiState.collectAsState()
    val context = LocalContext.current

    EventEffect(
        event = uiState.errorEvent,
        onConsumed = viewModel::onErrorEventConsumed,
        action = {
            Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
        }
    )
    EventEffect(
        event = uiState.successEvent,
        onConsumed = viewModel::onSuccessEventConsumed,
        action = {
            navigator.navigate(BankVerificationResultDestination())
        }
    )

    Scaffold(
        topBar = {
            CenteredTopAppBar(onBackClick = navigator::navigateUp, title = "")
        },
        bottomBar = {
            FancyButton(
                onClick = {
                    viewModel.onContinueClicked()
                },
                text = stringResource(id = R.string.txt_continue),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(AppTheme.dimensions.spacingMedium)
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .padding(AppTheme.dimensions.spacingMedium)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(AppTheme.dimensions.spacingMedium)
        ) {

            Header()

            Spacer(modifier = Modifier.width(0.dp)) // For extra spacing

            FancyTextField(
                value = uiState.accountName.name,
                onValueChange = {
                    viewModel.onNameChanged(it)
                },
                placeholder = {
                    Text(text = stringResource(id = R.string.account_holder_name))
                },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                isError = uiState.isNameError
            )

            FancyTextField(
                value = uiState.accountNumber.accNo,
                onValueChange = {
                    if (it.length <= AccountNumber.ACCOUNT_NUMBER_LENGTH) {
                        viewModel.onAccountNumberChanged(it)
                    }
                },
                placeholder = {
                    Text(text = stringResource(id = R.string.account_number))
                },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                isError = uiState.isAccountNumberError
            )

            FancyTextField(
                value = uiState.ifsc.code,
                onValueChange = viewModel::onIFSCCodeChanged,
                placeholder = { Text(text = stringResource(id = R.string.ifsc_code)) },
                isError = uiState.isIfscCodeError,
                keyboardOptions = KeyboardOptions(
                    capitalization = KeyboardCapitalization.Characters,
                    autoCorrect = false,
                    imeAction = ImeAction.Done
                )
            )
            FancyTextField(
                value = uiState.email.id,
                onValueChange = viewModel::onEmailChanged,
                placeholder = { Text(text = stringResource(id = R.string.email)) },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                isError = uiState.isEmailError
            )

        }
    }

    if (uiState.isLoading)
        LoadingScreen()
}

@Composable
fun Header() {
    Column(verticalArrangement = Arrangement.spacedBy(AppTheme.dimensions.spacingSmall)) {
        Text(
            text = stringResource(id = R.string.enter_bank_account),
            style = MaterialTheme.typography.h5
        )
        Text(text = stringResource(id = R.string.we_will_deposit_1_in_your_account_to_verify_your_identity_for_kyc))
    }
}

@Preview(showSystemUi = true)
@Composable
fun BankVerificationPreview() {
    CraterTheme {
        BankVerificationScreen(
            navigator = EmptyDestinationsNavigator
        )
    }
}