package com.aryasurya.adoptpet.ui.register

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aryasurya.adoptpet.data.UserRepository
import com.aryasurya.adoptpet.data.pref.UserModel
import kotlinx.coroutines.launch

class RegisterViewModel(private val repository: UserRepository): ViewModel() {
    fun saveUser(user: UserModel) {
        viewModelScope.launch {
            repository.saveUser(user)
        }
    }
}