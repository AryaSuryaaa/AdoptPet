package com.aryasurya.adoptpet.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aryasurya.adoptpet.data.UserRepository
import com.aryasurya.adoptpet.data.pref.UserModel
import kotlinx.coroutines.launch
import androidx.lifecycle.asLiveData


class MainViewModel(private val repository: UserRepository): ViewModel() {


    fun getSession(): LiveData<UserModel> {
        return repository.getSession().asLiveData()
    }
}