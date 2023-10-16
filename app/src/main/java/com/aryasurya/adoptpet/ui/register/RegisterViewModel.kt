package com.aryasurya.adoptpet.ui.register

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aryasurya.adoptpet.data.Result
import com.aryasurya.adoptpet.data.UserRepository
import com.aryasurya.adoptpet.data.pref.UserModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class RegisterViewModel(private val repository: UserRepository): ViewModel() {
    private val _createUserResult = MutableLiveData<Result<UserModel>>()
    val createUserResult: LiveData<Result<UserModel>> = _createUserResult

    fun saveUser(users: List<UserModel>) {
        viewModelScope.launch {
            repository.saveUser(users)
        }
    }

    fun createUser(name: String, email: String, password: String) {
        viewModelScope.launch {
            try {
                _createUserResult.value = Result.Loading

                repository.createUser(name, email, password).collect { result ->
                    _createUserResult.value = result
                }

            } catch (e: Exception) {
                _createUserResult.value = Result.Error(e.message ?: "An error occurred")
            }
        }
    }
}