package com.akzhey.contacts.presentation.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.akzhey.contacts.domain.models.PhoneNumber
import com.akzhey.contacts.domain.use_cases.GetNumberFromDbUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ShowViewModel @Inject constructor(private val getNumberFromDbUseCase: GetNumberFromDbUseCase) :
    ViewModel() {

    private val _phoneNumberData = MutableLiveData<List<PhoneNumber>>()
    val phoneNumberData: LiveData<List<PhoneNumber>> = _phoneNumberData

    fun getPhoneNumbersFromDb() {
        viewModelScope.launch(Dispatchers.IO) {
            val phoneNumber = getNumberFromDbUseCase()
            _phoneNumberData.postValue(phoneNumber)
        }
    }
}