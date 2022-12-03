package com.crater.android.feature.kyc.ui.model

import com.crater.android.core.common.UiState
import com.crater.android.core.common.model.Email
import com.crater.android.core.common.model.Name
import de.palm.composestateevents.StateEventWithContent
import de.palm.composestateevents.consumed

data class BankVerificationUiState(
    val accountName: Name = Name(""),
    val accountNumber: AccountNumber = AccountNumber(""),
    val ifsc: IFSC = IFSC(""),
    val email: Email = Email(""),
    val isNameError: Boolean = false,
    val isAccountNumberError: Boolean = false,
    val isIfscCodeError: Boolean = false,
    val isEmailError: Boolean = false,
    override val isLoading: Boolean = false,
    override val successEvent: StateEventWithContent<Boolean> = consumed(),
    override val errorEvent: StateEventWithContent<String> = consumed(),
) : UiState<Boolean>