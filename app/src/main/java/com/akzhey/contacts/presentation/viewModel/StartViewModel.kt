package com.akzhey.contacts.presentation.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.akzhey.contacts.common.Response
import com.akzhey.contacts.domain.use_cases.GetPhoneNumberUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class StartViewModel @Inject constructor(private val getPhoneNumberUseCase: GetPhoneNumberUseCase) :
    ViewModel() {

    private val _phoneNumber = MutableLiveData<Response<List<String>>?>()
    val phoneNumber: LiveData<Response<List<String>>?> = _phoneNumber

    /**
     * Get Phone numbers from api asynchronously
     */
    fun getPhoneNumbers() {
        _phoneNumber.value = Response.Loading()
        viewModelScope.launch(Dispatchers.IO) {
            val taskList = (1..4).map {
                async { getPhoneNumberUseCase() }
            }
            try {
                val result = taskList.awaitAll()
                var errorMessage = ""
                result.forEach {
                    if (it.message?.isNotBlank() == true) {
                        errorMessage = it.message
                    }
                }
                if (errorMessage.isNotBlank()) {
                    _phoneNumber.postValue(
                        Response.Error(
                            message = errorMessage
                        )
                    )
                } else {
                    val phoneNumberList = result.map {
                        it.data ?: ""
                    }
                    _phoneNumber.postValue(Response.Success(data = phoneNumberList))
                }

            } catch (e: Exception) {
                _phoneNumber.postValue(
                    Response.Error(
                        message = e.localizedMessage ?: "Something went wrong"
                    )
                )
            }
        }
    }

    /**
     * Setting response to null after moving to next fragment
     */
    fun setResponseToNull() {
        _phoneNumber.value = null
    }
}