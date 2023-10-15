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

    fun observeDataUser(username: String) {
        viewModelScope.launch {
            val user = repository.observeUserData(username)
            _userData.postValue(user)
        }
    }


}