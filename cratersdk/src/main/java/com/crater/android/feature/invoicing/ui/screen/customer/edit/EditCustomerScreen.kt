package com.crater.android.feature.invoicing.ui.screen.addeditcustomer

import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import com.crater.android.core.common.UiState
import com.crater.android.core.extension.showToast
import com.crater.android.feature.invoicing.domain.model.Customer
import com.crater.android.feature.invoicing.ui.model.EditCustomerArgs
import com.crater.android.feature.invoicing.ui.viewmodel.EditCustomerViewModel
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.result.ResultBackNavigator
import de.palm.composestateevents.EventEffect
import de.palm.composestateevents.StateEventWithContent
import de.palm.composestateevents.consumed

@Destination(navArgsDelegate = EditCustomerArgs::class)
@Composable
fun EditCustomerScreen(
    viewModel: EditCustomerViewModel = hiltViewModel(),
    resultBackNavigator: ResultBackNavigator<Customer>
) {
    val uiState by viewModel.uiState.collectAsState()
    val context = LocalContext.current

    EventEffect(
        event = uiState.successEvent,
        onConsumed = viewModel::onSuccessEventConsumed
    ) { customer ->
        val msg = if (uiState.isEditMode) {
            "Customer updated successfully!"
        } else {
            "Customer added successfully!"
        }
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()
        resultBackNavigator.navigateBack(customer)
    }

    EventEffect(
        event = uiState.errorEvent,
        onConsumed = viewModel::onErrorEventConsumed,
        action = context::showToast
    )

    EditCustomerScreenContent(
        onBackClick = resultBackNavigator::navigateBack,
        uiState = uiState,
        uiActions = EditCustomerUiActions(
            onNameChanged = viewModel::onNameChanged,
            onPhoneChanged = viewModel::onPhoneChanged,
            onEmailChanged = viewModel::onEmailChanged,
            onGstChanged = viewModel::onGstChanged,
            onAddressChanged = viewModel::onAddressChanged,
            onCityChanged = viewModel::onCityChanged,
            onStateChanged = viewModel::onStateChanged,
            onPinCodeChanged = viewModel::onPinCodeChanged,
            onCountryChanged = viewModel::onCountryChanged,
            onUpdateClick = viewModel::onUpdateClick
        )
    )
}

data class EditCustomerUiState(
    val name: String = "",
    val phone: String = "",
    val emailId: String? = null,
    val gst: String? = null,
    val city: String? = null,
    val pinCode: String? = null,
    val pan: String? = null,
    val address: String? = null,
    val state: String? = null,
    val country: String? = null,
    val isNameError: Boolean = false,
    val isEmailError: Boolean = false,
    override val isLoading: Boolean = false,
    override val successEvent: StateEventWithContent<Customer> = consumed(),
    override val errorEvent: StateEventWithContent<String> = consumed(),
    val isEditMode: Boolean = false
) : UiState<Customer>

data class EditCustomerUiActions(
    val onNameChanged: (String) -> Unit = {},
    val onPhoneChanged: (String) -> Unit = {},
    val onEmailChanged: (String) -> Unit = {},
    val onGstChanged: (String) -> Unit = {},
    val onAddressChanged: (String) -> Unit = {},
    val onCityChanged: (String) -> Unit = {},
    val onStateChanged: (String) -> Unit = {},
    val onPinCodeChanged: (String) -> Unit = {},
    val onCountryChanged: (String) -> Unit = {},
    val onUpdateClick: () -> Unit = {}
)


