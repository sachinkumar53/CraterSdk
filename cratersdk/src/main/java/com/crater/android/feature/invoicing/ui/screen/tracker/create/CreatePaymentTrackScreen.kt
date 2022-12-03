package com.crater.android.feature.invoicing.ui.screen.tracker.create

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import com.crater.android.core.common.UiState
import com.crater.android.core.extension.showToast
import com.crater.android.feature.destinations.*
import com.crater.android.feature.invoicing.domain.model.Customer
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.ramcosta.composedestinations.navigation.popUpTo
import com.ramcosta.composedestinations.result.NavResult
import com.ramcosta.composedestinations.result.ResultRecipient
import de.palm.composestateevents.EventEffect
import de.palm.composestateevents.StateEvent
import de.palm.composestateevents.StateEventWithContent
import de.palm.composestateevents.consumed

@Destination
@Composable
fun CreatePaymentTrackScreen(
    viewModel: CreatePaymentTrackerViewModel = hiltViewModel(),
    navigator: DestinationsNavigator,
    customerDialogResultRecipient: ResultRecipient<CustomerDialogDestination, Customer>,
    allCustomerResultRecipient: ResultRecipient<AllCustomerScreenDestination, Customer>,
    editCustomerResultRecipient: ResultRecipient<EditCustomerScreenDestination, Customer>
) {
    val context = LocalContext.current
    val uiState by viewModel.uiState.collectAsState()

    fun onCustomerAdded(result: NavResult<Customer>) {
        when (result) {
            is NavResult.Value -> viewModel.onToCustomerChanged(result.value)
            NavResult.Canceled -> Unit
        }
    }

    customerDialogResultRecipient.onNavResult(::onCustomerAdded)
    allCustomerResultRecipient.onNavResult(::onCustomerAdded)
    editCustomerResultRecipient.onNavResult(::onCustomerAdded)

    EventEffect(
        event = uiState.errorEvent,
        onConsumed = viewModel::onErrorEventConsumed,
        action = context::showToast
    )

    EventEffect(
        event = uiState.customerLoadedEvent,
        onConsumed = viewModel::onCustomerLoadedEventConsumed,
        action = {
            navigator.navigate(CustomerDialogDestination())
        }
    )

    EventEffect(
        event = uiState.successEvent,
        onConsumed = viewModel::onSuccessEventConsumed,
        action = {
            context.showToast(it)
            navigator.navigate(PaymentTrackerPreviewScreenDestination(uiState.trackerNumber)){
                popUpTo(CreatePaymentTrackScreenDestination){ inclusive = true }
            }
        }
    )

    CreatePaymentTrackerScreenContent(
        uiState = uiState,
        uiActions = CreatePaymentTrackerUiActions(
            onBackClick = navigator::navigateUp,
            onTackerNumberChanged = viewModel::onTrackerNumberChanged,
            onFromUserChanged = viewModel::onFromUserChanged,
            onSaveButtonClick = viewModel::onSaveButtonClick,
            onAmountChanged = viewModel::onAmountChanged,
            onAddCustomerClick = viewModel::onAddCustomerClick,
            onEditCustomerClick = {
                val customer = uiState.toCustomer ?: return@CreatePaymentTrackerUiActions
                navigator.navigate(
                    EditCustomerScreenDestination(
                        customer = customer,
                        isEditMode = true
                    )
                )
            },
            onDeleteCustomerClick = viewModel::onDeleteCustomerClick
        )
    )

}


data class CreatePaymentTrackerUiState(
    val trackerNumber: String = "",
    val fromUser: String = "",
    val toCustomer: Customer? = null,
    val amount: String = "",
    val hasTrackerNumberError: Boolean = false,
    val hasFromUserError: Boolean = false,
    val hasToCustomerError: Boolean = false,
    val hasAmountError: Boolean = false,
    val customerLoadedEvent: StateEvent = consumed,
    override val isLoading: Boolean = false,
    override val successEvent: StateEventWithContent<String> = consumed(),
    override val errorEvent: StateEventWithContent<String> = consumed(),
) : UiState<String>


class CreatePaymentTrackerUiActions(
    val onTackerNumberChanged: (String) -> Unit = {},
    val onFromUserChanged: (String) -> Unit = {},
    val onSaveButtonClick: () -> Unit = {},
    val onAmountChanged: (String) -> Unit = {},
    val onBackClick: () -> Unit = {},
    val onAddCustomerClick: () -> Unit = {},
    val onEditCustomerClick: () -> Unit = {},
    val onDeleteCustomerClick: () -> Unit = {}
)