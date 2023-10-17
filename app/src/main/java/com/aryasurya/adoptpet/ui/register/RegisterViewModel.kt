package com.aryasurya.adoptpet.ui.register

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aryasurya.adoptpet.data.Result
import com.aryasurya.adoptpet.data.UserRepository
import com.aryasurya.adoptpet.data.remote.response.CreateUserResponse
import kotlinx.coroutines.launch

class RegisterViewModel(private val repository: UserRepository): ViewModel() {
    private val _createUserResult = MutableLiveData<Result<CreateUserResponse>>()
    val createUserResult: LiveData<Result<CreateUserResponse>> = _createUserResult

    fun createUser(name: String, email: String, password: String) {
        viewModelScope.launch {
            repository.createUser(name, email, password).collect{ result ->
                _createUserResult.value = result
            }
        }
    }
}