package com.crater.android.feature.invoicing.domain.repository

import com.crater.android.data.model.contact.Contact

interface ContactsRepository {

    suspend fun searchContacts(query: String): List<Contact>

    suspend fun getContacts(): List<Contact>
}