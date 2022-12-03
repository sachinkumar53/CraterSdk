package com.crater.android.feature.cash.ui.screen.features

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.crater.android.core.data.cache.CacheManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class FeaturesViewModel @Inject constructor(
    cacheManager: CacheManager
) : ViewModel() {

    var isPanKycDone = false
        private set
    var isBankKycDone = false
        private set
    var isBankAccountCreated = false
        private set

    init {
        cacheManager.getUserDetails().onEach {
            isPanKycDone = !it.pan.isNullOrBlank()
            isBankKycDone = it.isBankKycDone
            isBankAccountCreated = it.isVirtualAccountCreated
        }.launchIn(viewModelScope)
    }
}