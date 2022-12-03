package com.crater.android.feature.invoicing.ui.mapper

import com.crater.android.data.model.contact.Contact
import com.crater.android.feature.invoicing.domain.model.Customer

fun Contact.toCustomer(): Customer {
    return Customer(
        name = displayName ?: "",
        phone = phoneNumbers.firstOrNull() ?: "",
        emailId = emails.firstOrNull(),
        pinCode = null,
        city = null,
        address = null,
        country = null,
        gst = null,
        pan = null,
        state = null
    )
}