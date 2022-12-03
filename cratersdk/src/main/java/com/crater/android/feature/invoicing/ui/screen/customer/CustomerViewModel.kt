package com.crater.android.feature.invoicing.ui.screen.customer

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.crater.android.core.data.Resource
import com.crater.android.feature.invoicing.domain.repository.CustomerRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CustomerViewModel @Inject constructor(
    private val repository: CustomerRepository
) : ViewModel() {

    private val exceptionHandler = CoroutineExceptionHandler { _, throwable ->

    }
    val recentCustomers = repository.getRecentCustomers().stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5000),
        Resource.Loading()
    )

    val allCustomers = repository.getAllCustomers().stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5000),
        Resource.Loading()
    )

    init {
        viewModelScope.launch(exceptionHandler) {
            repository.fetchCustomers()
        }
    }

}