package com.akzhey.contacts.presentation.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.akzhey.contacts.domain.models.PhoneNumber
import com.akzhey.contacts.domain.use_cases.DropNumberTableUseCase
import com.akzhey.contacts.domain.use_cases.SaveNumberToDbUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SaveViewModel @Inject constructor(
    private val saveNumberToDbUseCase: SaveNumberToDbUseCase,
    private val dropNumberTableUseCase: DropNumberTableUseCase
) : ViewModel() {

    var phoneNumberTypeList = mutableListOf("Home", "Work", "Other", "Other", "Select One")
    val selectedTypePositionList = mutableSetOf<Int>()

    /**
     * Save phone numbers to db and on completion send back a function
     */
    fun savePhoneNumbersToDb(phoneNumberList: List<PhoneNumber>, onCompletion: () -> Unit) {
        viewModelScope.launch(Dispatchers.IO) {
            dropNumberTableUseCase()
            phoneNumberList.forEach { phoneNumber ->
                launch {
                    saveNumberToDbUseCase(phoneNumber)
                }
            }
        }.invokeOnCompletion {
            onCompletion()
        }
    }

    /**
     * Validate phone number type
     */
    fun validateType(listOfPhoneNumbersToBeSaved: List<PhoneNumber>) : Boolean{
        listOfPhoneNumbersToBeSaved.forEach {
            if (it.type == "Select One") {
                return false
            }
        }
        return true
    }
}