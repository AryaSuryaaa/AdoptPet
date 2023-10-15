package com.aryasurya.adoptpet.ui.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aryasurya.adoptpet.data.UserRepository
import com.aryasurya.adoptpet.data.pref.UserModel
import kotlinx.coroutines.launch

class LoginViewModel(private val repository: UserRepository): ViewModel() {
    private val _userData = MutableLiveData<UserModel?>()

    val userData: LiveData<UserModel?>
        get() = _userData

    fun validateCredentials(username: String, password: String) {
        viewModelScope.launch {
            val user = repository.observeUserData(username) // atau repository.observeUsers(username)

            if (user != null && user.password == password) {
                // Validasi berhasil, data pengguna ditemukan dan password cocok
                val userWithLogin = user.copy(isLogin = true)
                _userData.postValue(userWithLogin)
            } else {
                // Validasi gagal, password salah atau data pengguna tidak ditemukan
                _userData.postValue(null)
            }
        }
    }

    fun saveSession(user: UserModel) {
        viewModelScope.launch {
            repository.saveSession(user)
        }
    }

}