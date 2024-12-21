package com.akzhey.contacts.domain.use_cases

import com.akzhey.contacts.data.repository.ContactRepositoryImpl
import com.akzhey.contacts.domain.models.PhoneNumber
import javax.inject.Inject

class SaveNumberToDbUseCase @Inject constructor(private val contactRepository: ContactRepositoryImpl) {
    suspend operator fun invoke(phoneNumber: PhoneNumber) {
        return contactRepository.saveNumbersToDb(phoneNumber)
    }
}