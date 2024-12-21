package com.akzhey.contacts.domain.use_cases

import com.akzhey.contacts.common.Response
import com.akzhey.contacts.data.repository.ContactRepositoryImpl
import javax.inject.Inject

class GetPhoneNumberUseCase @Inject constructor(private val contactRepository: ContactRepositoryImpl) {
    suspend operator fun invoke(): Response<String> {
        return contactRepository.getContact()
    }
}