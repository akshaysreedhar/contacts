package com.akzhey.contacts.domain.use_cases

import com.akzhey.contacts.data.repository.ContactRepositoryImpl
import javax.inject.Inject

class DropNumberTableUseCase @Inject constructor(private val contactRepository: ContactRepositoryImpl) {
    suspend operator fun invoke() {
        return contactRepository.dropTable()
    }
}