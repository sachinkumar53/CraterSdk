package com.crater.android.feature.invoicing.ui.model

import com.crater.android.feature.invoicing.domain.model.Customer
import de.palm.composestateevents.StateEventWithContent
import de.palm.composestateevents.consumed

data class CustomerListState(
    val isLoading :Boolean = false,
    val data:List<Customer> = emptyList(),
    val errorEvent:StateEventWithContent<String> = consumed()
)