package com.aryasurya.adoptpet.ui.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aryasurya.adoptpet.data.UserRepository
import com.aryasurya.adoptpet.data.pref.UserModel
import kotlinx.coroutines.launch

class LoginViewModel(private val repository: UserRepository): ViewModel() {
    fun observeDataUser(user: UserModel) {
        viewModelScope.launch {
        }
    }


}