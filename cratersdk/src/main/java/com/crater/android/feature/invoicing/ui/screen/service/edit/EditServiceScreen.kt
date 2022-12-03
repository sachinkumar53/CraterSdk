package com.crater.android.feature.invoicing.ui.screen.service.edit

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import com.crater.android.core.common.UiState
import com.crater.android.feature.invoicing.domain.model.Service
import com.crater.android.feature.invoicing.ui.model.EditServiceArgs
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.result.ResultBackNavigator
import de.palm.composestateevents.EventEffect
import de.palm.composestateevents.StateEventWithContent
import de.palm.composestateevents.consumed

@Destination(navArgsDelegate = EditServiceArgs::class)
@Composable
fun EditServiceScreen(
    viewModel: EditServiceViewModel = hiltViewModel(),
    navigator: ResultBackNavigator<Service>
) {
    val uiState by viewModel.uiState.collectAsState()

    EventEffect(
        event = uiState.successEvent,
        onConsumed = viewModel::onSuccessEventConsumed,
        action = navigator::navigateBack
    )

    EditServiceScreenContent(
        uiState = uiState,
        uiActions = EditServiceUiActions(
            onServiceNameChanged = viewModel::onServiceNameChanged,
            onPriceChanged = viewModel::onPriceChanged,
            onQuantityChanged = viewModel::onQuantityChanged,
            onSacCodeChanged = viewModel::onSacCodeChanged,
            onAddClick = viewModel::onAddClick,
            onBackClick = navigator::navigateBack
        )
    )
}


data class EditServiceUiState(
    val serviceName: String = "",
    val price: String = "",
    val quantity: String = "",
    val sacCode: String = "",
    val hasServiceNameError: Boolean = false,
    val hasPriceError: Boolean = false,
    val hasQuantityError: Boolean = false,
    override val isLoading: Boolean = false,
    override val successEvent: StateEventWithContent<Service> = consumed(),
    override val errorEvent: StateEventWithContent<String> = consumed()
) : UiState<Service>

data class EditServiceUiActions(
    val onServiceNameChanged: (String) -> Unit = {},
    val onPriceChanged: (String) -> Unit = {},
    val onQuantityChanged: (String) -> Unit = {},
    val onSacCodeChanged: (String) -> Unit = {},
    val onAddClick: () -> Unit = {},
    val onBackClick: () -> Unit = {}
)



