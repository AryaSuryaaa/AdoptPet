package com.aryasurya.adoptpet.ui.account

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aryasurya.adoptpet.data.UserRepository
import kotlinx.coroutines.launch

class AccountViewModel(private val repository: UserRepository): ViewModel() {

    fun logout() {
        viewModelScope.launch {
            repository.logout()
        }
    }
}