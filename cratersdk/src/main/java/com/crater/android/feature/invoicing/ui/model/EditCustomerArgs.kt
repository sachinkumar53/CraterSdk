package com.crater.android.feature.invoicing.ui.model

import com.crater.android.feature.invoicing.domain.model.Customer

data class EditCustomerArgs(
    val customer: Customer? = null,
    val isEditMode: Boolean = false
)
