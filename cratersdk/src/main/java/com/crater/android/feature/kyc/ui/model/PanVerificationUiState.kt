package com.crater.android.feature.kyc.ui.model

import com.crater.android.core.common.UiState
import com.crater.android.feature.kyc.domain.model.PanNumber
import de.palm.composestateevents.StateEventWithContent
import de.palm.composestateevents.consumed

data class PanVerificationUiState(
    val panNumber: PanNumber? = null,
    val isInvalidPanNumber: Boolean = false,
    override val isLoading: Boolean = false,
    override val successEvent: StateEventWithContent<Boolean> = consumed(),
    override val errorEvent: StateEventWithContent<String> = consumed()
) : UiState<Boolean>
