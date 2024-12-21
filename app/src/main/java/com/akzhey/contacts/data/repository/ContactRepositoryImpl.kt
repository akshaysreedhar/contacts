package com.akzhey.contacts.data.repository

import com.akzhey.contacts.common.Response
import com.akzhey.contacts.data.local.db.NumberDao
import com.akzhey.contacts.data.mappers.toNumberItem
import com.akzhey.contacts.data.mappers.toPhoneNumberList
import com.akzhey.contacts.data.remote.RandomNumberApi
import com.akzhey.contacts.domain.models.PhoneNumber
import com.akzhey.contacts.domain.repository.ContactRepository
import javax.inject.Inject

class ContactRepositoryImpl @Inject constructor(
    private val randomNumberApi: RandomNumberApi,
    private val numberDao: NumberDao
) : ContactRepository {

    override suspend fun getContact(): Response<String> {
        try {
            val result = randomNumberApi.getPhoneNumber()
            return Response.Success(data = result[0])
        } catch (e: Exception) {
            return Response.Error(message = e.localizedMessage ?: "Something went wrong")
        }
    }

    override suspend fun saveNumbersToDb(phoneNumber: PhoneNumber) {
        val numberItem = phoneNumber.toNumberItem()
        numberDao.insertNumber(numberItem)
    }

    override suspend fun getNumbersFromDb(): List<PhoneNumber> {
        val numberItemList = numberDao.getAllNumbers()
        return numberItemList.toPhoneNumberList()
    }

    override suspend fun dropTable() {
        numberDao.dropTable()
    }
}