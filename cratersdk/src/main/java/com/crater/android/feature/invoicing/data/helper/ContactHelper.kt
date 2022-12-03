package com.crater.android.feature.invoicing.data.helper

import android.telephony.PhoneNumberUtils
import android.util.Patterns
import com.crater.android.data.model.contact.Contact

object ContactHelper {
    private const val COUNTRY_CODE = "IN"

    /*fun verifyAndAddContact(contacts: ArrayList<Contact>, contact: Contact) {
        if (verifyContact(contact)) {
            contacts.add(contact)
        }
    }*/

    private fun verifyContact(contact: Contact): Boolean {
        return ((contact.emails.isNotEmpty()
                || contact.phoneNumbers.isNotEmpty())
                && !contact.displayName.isNullOrEmpty())
    }

    fun getFormattedNumber(number: String?): String {
        return PhoneNumberUtils.formatNumber(
            number,
            COUNTRY_CODE
        ).replace(
            "\\s+".toRegex(),
            ""
        )
    }

    fun verifyEmail(emails: List<String>, email: String): Boolean {
        return email.isNotEmpty() &&
                Patterns.EMAIL_ADDRESS.matcher(email).matches() &&
                isUniqueEmail(emails, email)
    }

    private fun isUniqueEmail(emails: List<String>, email: String): Boolean {
        for (e in emails) {
            if (email == e)
                return false
        }
        return true
    }

    fun verifyPhone(phoneNumbers: List<String>, phone: String): Boolean {
        return phone.isNotEmpty() &&
                Patterns.PHONE.matcher(phone).matches() &&
                isUniquePhoneNumber(phoneNumbers, phone)
    }

    private fun isUniquePhoneNumber(phoneNumbers: List<String>, phone: String): Boolean {
        for (i in phoneNumbers.indices) {
            if (matchPhoneNumber(phoneNumbers[i], phone /*null*/)) {
                return false
            }
        }
        return true
    }

    private fun matchPhoneNumber(
        listPhone: String,
        singlePhone: String,
        //countryCode: String?
    ): Boolean {
        //val code = countryCode ?: ""
        var lPhone = listPhone
        var sPhone = singlePhone

        if (lPhone == sPhone)
            return true

//        lPhone = lPhone.replace(code, "")
//        sPhone = sPhone.replace(code, "")

        if (lPhone == sPhone)
            return true

        return if (listPhone.length <= singlePhone.length) {
            sPhone = sPhone.substring(singlePhone.length - listPhone.length)
            sPhone == lPhone
        } else {
            lPhone = lPhone.substring(lPhone.length - sPhone.length)
            sPhone == lPhone
        }
    }
}