package com.crater.android.feature.invoicing.ui.screen.service

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.crater.android.core.data.Resource
import com.crater.android.feature.invoicing.domain.repository.ServiceRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class ServiceViewModel @Inject constructor(
    repository: ServiceRepository
) : ViewModel() {

    val recentServices = repository.getRecentServices().stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5000),
        Resource.Loading()
    )

    val allServices = repository.getAllServices().stateIn(
        viewModelScope, SharingStarted.WhileSubscribed(5000),
        Resource.Success(emptyList())
    )
}
