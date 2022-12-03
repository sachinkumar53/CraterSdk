package com.crater.android.feature.invoicing.data.repository

import android.content.Context
import android.provider.ContactsContract
import com.crater.android.data.model.contact.Contact
import com.crater.android.feature.invoicing.domain.repository.ContactsRepository
import com.crater.android.feature.invoicing.data.helper.ContactHelper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class ContactsRepositoryImpl(private val context: Context) : ContactsRepository {

    override suspend fun searchContacts(query: String): List<Contact> =
        withContext(Dispatchers.IO) {
            if (query.isBlank()) {
                getContacts()
            } else {
                val selection = """${ContactsContract.Data.MIMETYPE} in (?, ?) AND
            |(${ContactsContract.Contacts.DISPLAY_NAME} LIKE ? OR
            |${ContactsContract.CommonDataKinds.Contactables.DATA} LIKE ?)""".trimMargin()

                val selectionArgs = arrayOf(
                    ContactsContract.CommonDataKinds.Email.CONTENT_ITEM_TYPE,
                    ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE,
                    "%$query%",
                    "%$query%"
                )
                fetchContacts(selection, selectionArgs)
            }
        }

    override suspend fun getContacts(): List<Contact> = withContext(Dispatchers.IO) {
        fetchContacts(
            "${ContactsContract.Data.MIMETYPE} in (?, ?)",
            arrayOf(
                ContactsContract.CommonDataKinds.Email.CONTENT_ITEM_TYPE,
                ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE
            )
        )
    }

    private suspend fun fetchContacts(
        selection: String?,
        selectionArgs: Array<String>
    ): List<Contact> = withContext(Dispatchers.IO) {
        val projection = arrayOf(
            ContactsContract.Data.MIMETYPE,
            ContactsContract.Data.CONTACT_ID,
            ContactsContract.Contacts.DISPLAY_NAME,
            ContactsContract.CommonDataKinds.Contactables.DATA
        )

        val contacts = context.contentResolver
            .query(
                ContactsContract.Data.CONTENT_URI,
                projection,
                selection,
                selectionArgs,
                ContactsContract.Contacts.DISPLAY_NAME + " ASC"
            )?.use { cursor ->
                val mimeTypeField = cursor.getColumnIndex(ContactsContract.Data.MIMETYPE)
                val idField = cursor.getColumnIndex(ContactsContract.Data.CONTACT_ID)
                val nameField = cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME)
                val dataField =
                    cursor.getColumnIndex(ContactsContract.CommonDataKinds.Contactables.DATA)

                val contactsById = mutableMapOf<String, Contact>()

                while (cursor.moveToNext()) {
                    //val mimeType = getString(mimeTypeField)
                    val id = cursor.getString(idField)
                    var contact = contactsById.getOrPut(id) {
                        val name = cursor.getString(nameField)
                        Contact(
                            id = id,
                            displayName = name,
                            phoneNumbers = listOf(),
                            emails = listOf()
                        )
                    }

                    val data = cursor.getString(dataField)

                    when (cursor.getString(mimeTypeField)) {
                        ContactsContract.CommonDataKinds.Email.CONTENT_ITEM_TYPE -> {
                            if (ContactHelper.verifyEmail(contact.emails, data))
                                contact = contact.copy(emails = contact.emails + data)
                        }
                        ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE -> {
                            if (ContactHelper.verifyPhone(contact.phoneNumbers, data)) {
                                contact = contact.copy(
                                    phoneNumbers = contact.phoneNumbers + ContactHelper.getFormattedNumber(
                                        data
                                    )
                                )
                            }
                        }
                    }
                    contactsById[id] = contact
                }
                contactsById.values.toList()
            }
        contacts ?: emptyList()
    }
}


//private const val TAG = "ContactsRepository"