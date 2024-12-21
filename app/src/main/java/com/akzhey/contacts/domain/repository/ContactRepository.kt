package com.akzhey.contacts.domain.repository

import com.akzhey.contacts.common.Response
import com.akzhey.contacts.domain.models.PhoneNumber

interface ContactRepository {
    //Remote
    suspend fun getContact(): Response<String>

    //Local DB
    suspend fun saveNumbersToDb(phoneNumber: PhoneNumber)
    suspend fun getNumbersFromDb(): List<PhoneNumber>
    suspend fun dropTable()
}