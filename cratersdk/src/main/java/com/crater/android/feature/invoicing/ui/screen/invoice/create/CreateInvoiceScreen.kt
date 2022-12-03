package com.crater.android.feature.invoicing.ui.screen.invoice.create

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import com.crater.android.core.common.UiState
import com.crater.android.core.extension.showToast
import com.crater.android.core.ui.screen.LoadingScreen
import com.crater.android.core.util.DateFormatterUtil
import com.crater.android.feature.destinations.*
import com.crater.android.feature.invoicing.domain.model.Customer
import com.crater.android.feature.invoicing.domain.model.Service
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.ramcosta.composedestinations.navigation.popUpTo
import com.ramcosta.composedestinations.result.NavResult
import com.ramcosta.composedestinations.result.ResultRecipient
import de.palm.composestateevents.EventEffect
import de.palm.composestateevents.StateEvent
import de.palm.composestateevents.StateEventWithContent
import de.palm.composestateevents.consumed
import java.time.LocalDate

@Destination
@Composable
fun CreateInvoiceScreen(
    viewModel: CreateInvoiceViewModel = hiltViewModel(),
    navigator: DestinationsNavigator,
    customerDialogResultRecipient: ResultRecipient<CustomerDialogDestination, Customer>,
    allCustomerResultRecipient: ResultRecipient<AllCustomerScreenDestination, Customer>,
    editCustomerResultRecipient: ResultRecipient<EditCustomerScreenDestination, Customer>,
    serviceResultRecipient: ResultRecipient<EditServiceScreenDestination, Service>
) {
    val context = LocalContext.current
    val uiState by viewModel.uiState.collectAsState()

    val uiActions = remember(viewModel, navigator) {
        CreateInvoiceUiActions(
            onBackClick = navigator::navigateUp,
            onInvoiceNumberChanged = viewModel::onInvoiceNumberChanged,
            onFromuserChanged = viewModel::onFromUserChanged,
            onTaxTypeChanged = viewModel::onTaxTypeChanged,
            onTaxChanged = viewModel::onTaxChanged,
            onCreationDateChanged = viewModel::onCreatedDateChanged,
            onDueDateChanged = viewModel::onDueDateChanged,
            onSaveButtonClick = viewModel::onSaveButtonClick,
            onAmountChanged = viewModel::onAmountChanged,
            onAddServiceClick = {
                navigator.navigate(ServiceDialogDestination())
            },
            onAddCustomerClick = viewModel::onAddCustomerClick,
            onEditCustomerClick = {
                val customer = uiState.toCustomer ?: return@CreateInvoiceUiActions
                navigator.navigate(
                    EditCustomerScreenDestination(
                        customer = customer,
                        isEditMode = true
                    )
                )
            },
            onDeleteCustomerClick = viewModel::onDeleteCustomerClick
        )
    }

    fun onCustomerAdded(result: NavResult<Customer>) {
        when (result) {
            is NavResult.Value -> viewModel.onToCustomerChanged(result.value)
            NavResult.Canceled -> Unit
        }
    }

    customerDialogResultRecipient.onNavResult(::onCustomerAdded)
    allCustomerResultRecipient.onNavResult(::onCustomerAdded)
    editCustomerResultRecipient.onNavResult(::onCustomerAdded)
    serviceResultRecipient.onNavResult { result ->
        when (result) {
            is NavResult.Value -> viewModel.onServiceAdded(result.value)
            NavResult.Canceled -> Unit
        }
    }

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
            navigator.navigate(InvoicePreviewScreenDestination(uiState.invoiceNumber)){
                popUpTo(CreateInvoiceScreenDestination){ inclusive = true }
            }
        }
    )

    CreateInvoiceScreenContent(uiState = uiState, uiActions = uiActions)

    if (uiState.isLoading) LoadingScreen()
}


data class CreateInvoiceUiState(
    val invoiceNumber: String = "",
    val fromUser: String = "",
    val toCustomer: Customer? = null,
    val services: List<Service> = emptyList(),
    val tax: String = "0",
    val taxType: String = "",
    val amount: String = "",
    val creationDate: String = DateFormatterUtil.formatInShortStyle(LocalDate.now()),
    val dueDate: String = "",
    val hasInvoiceNumberError: Boolean = false,
    val hasFromUserError: Boolean = false,
    val hasToCustomerError: Boolean = false,
    val hasServicesError: Boolean = false,
    val hasAmountError: Boolean = false,
    val hasDueDateError: Boolean = false,
    val customerLoadedEvent: StateEvent = consumed,
    override val isLoading: Boolean = false,
    override val successEvent: StateEventWithContent<String> = consumed(),
    override val errorEvent: StateEventWithContent<String> = consumed(),
) : UiState<String>


class CreateInvoiceUiActions(
    val onInvoiceNumberChanged: (String) -> Unit = {},
    val onFromuserChanged: (String) -> Unit = {},
    val onTaxChanged: (String) -> Unit = {},
    val onTaxTypeChanged: (String) -> Unit = {},
    val onCreationDateChanged: (String) -> Unit = {},
    val onDueDateChanged: (String) -> Unit = {},
    val onSaveButtonClick: () -> Unit = {},
    val onAmountChanged: (String) -> Unit = {},
    val onBackClick: () -> Unit = {},
    val onAddServiceClick: () -> Unit = {},
    val onAddCustomerClick: () -> Unit = {},
    val onEditCustomerClick: () -> Unit = {},
    val onDeleteCustomerClick: () -> Unit = {}
)

