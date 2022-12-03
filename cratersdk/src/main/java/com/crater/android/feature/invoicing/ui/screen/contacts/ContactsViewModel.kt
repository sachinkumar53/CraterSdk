package com.crater.android.feature.invoicing.ui.screen.contacts

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.crater.android.data.model.contact.Contact
import com.crater.android.feature.invoicing.domain.repository.ContactsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ContactsViewModel @Inject constructor(
    private val contactsRepository: ContactsRepository
) : ViewModel() {
    private val _searchQuery = MutableStateFlow("")
    val searchQuery = _searchQuery.asStateFlow()

    private val _contactList = MutableStateFlow<List<Contact>>(emptyList())
    val contactList = _contactList

    private fun startObserving() {
        _searchQuery.onEach { query ->
            _contactList.value = if (query.isEmpty()) {
                contactsRepository.getContacts()
            } else {
                contactsRepository.searchContacts(query)
            }
        }.flowOn(Dispatchers.IO)
            .distinctUntilChanged()
            .launchIn(viewModelScope)
    }

    fun onSearchQueryChanged(query: String) {
        _searchQuery.value = query
    }

    fun onPermissionGranted() {
        viewModelScope.launch {
            _contactList.value = contactsRepository.getContacts()
            startObserving()
        }

    }
}