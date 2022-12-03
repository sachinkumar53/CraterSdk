package com.crater.android.feature.invoicing.ui.screen.service.edit

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.crater.android.feature.invoicing.domain.model.Service
import com.crater.android.feature.invoicing.domain.repository.ServiceRepository
import com.crater.android.feature.invoicing.ui.model.EditServiceArgs
import com.crater.android.feature.navArgs
import dagger.hilt.android.lifecycle.HiltViewModel
import de.palm.composestateevents.consumed
import de.palm.composestateevents.triggered
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EditServiceViewModel @Inject constructor(
    private val repository: ServiceRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val args: EditServiceArgs = savedStateHandle.navArgs()

    private val _uiState = MutableStateFlow(EditServiceUiState())
    val uiState = _uiState.asStateFlow()

    init {
        args.service?.let { service->
            _uiState.update {
                it.copy(
                    serviceName = service.name,
                    price = service.price,
                    quantity = service.quantity.toString(),
                    sacCode = service.sac?:""
                )
            }
        }
    }
    fun onServiceNameChanged(value: String) {
        _uiState.update { it.copy(serviceName = value, hasServiceNameError = false) }
    }

    fun onPriceChanged(value: String) {
        _uiState.update { it.copy(price = value, hasPriceError = false) }
    }

    fun onQuantityChanged(value: String) {
        _uiState.update { it.copy(quantity = value, hasQuantityError = false) }
    }

    fun onSacCodeChanged(value: String) {
        _uiState.update { it.copy(sacCode = value) }
    }

    fun onAddClick() {
        if (validate()) {
            addService()
        }
    }

    private fun validate(): Boolean = with(uiState.value) {
        if (serviceName.isBlank()) {
            _uiState.update { it.copy(hasServiceNameError = true) }
            return@with false
        }
        if (price.isBlank()) {
            _uiState.update { it.copy(hasPriceError = true) }
            return@with false
        }
        if (quantity.isBlank()) {
            _uiState.update { it.copy(hasQuantityError = true) }
            return@with false
        }
        return@with true
    }


    private fun addService() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            val service = with(uiState.value) {
                Service(name = serviceName, price = price, quantity = quantity.toInt(), sac = sacCode)
            }
            repository.insertService(service)
            _uiState.update { it.copy(isLoading = false, successEvent = triggered(service)) }
        }
    }

    fun onSuccessEventConsumed() {
        _uiState.update { it.copy(successEvent = consumed()) }
    }

    fun onErrorEventConsumed() {
        _uiState.update { it.copy(errorEvent = consumed()) }
    }
}