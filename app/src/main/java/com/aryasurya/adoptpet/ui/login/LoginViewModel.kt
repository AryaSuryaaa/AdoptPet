package com.aryasurya.adoptpet.ui.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aryasurya.adoptpet.data.Result
import com.aryasurya.adoptpet.data.UserRepository
import com.aryasurya.adoptpet.data.pref.UserModel
import com.aryasurya.adoptpet.data.remote.response.LoginResponse
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.single
import kotlinx.coroutines.launch

class LoginViewModel(private val repository: UserRepository): ViewModel() {
//    private val _userData = MutableLiveData<UserModel?>()


    private val _loginResult = MutableLiveData<Result<LoginResponse>>()
    val loginResult: LiveData<Result<LoginResponse>> = _loginResult

    fun login(email: String, password: String) {
        viewModelScope.launch {
            repository.login(email, password).collect { result ->
                _loginResult.value = result
            }
        }
    }

//    val userData: LiveData<UserModel?>
//        get() = _userData

//    fun validateCredentials(username: String, password: String) {
//        viewModelScope.launch {
//            val user = repository.observeUserData(username) // atau repository.observeUsers(username)
//
//            if (user != null && user.password == password) {
//                // Validasi berhasil, data pengguna ditemukan dan password cocok
//                val userWithLogin = user.copy(isLogin = true)
//                _userData.postValue(userWithLogin)
//            } else {
//                // Validasi gagal, password salah atau data pengguna tidak ditemukan
//                _userData.postValue(null)
//            }
//        }
//    }

    fun saveSession(user: UserModel) {
        viewModelScope.launch {
            repository.saveSession(user)
        }
    }





}