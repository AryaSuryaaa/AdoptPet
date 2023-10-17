package com.aryasurya.adoptpet.ui.account

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.aryasurya.adoptpet.data.Result
import com.aryasurya.adoptpet.data.UserRepository
import com.aryasurya.adoptpet.data.pref.UserModel
import com.aryasurya.adoptpet.data.remote.response.ListStoryItem
import com.aryasurya.adoptpet.data.remote.response.Story
import kotlinx.coroutines.launch

class AccountViewModel(private val repository: UserRepository): ViewModel() {

    fun logout() {
        viewModelScope.launch {
            repository.logout()
        }
    }

    fun getSession(): LiveData<UserModel> {
        return repository.getSession().asLiveData()
    }

//    private val _fetchResult = MutableLiveData<Result<List<ListStoryItem>>>()
//    val fetchResult: LiveData<Result<List<ListStoryItem>>> = _fetchResult

//    fun fetchMyStory(name: String): LiveData<Result<List<ListStoryItem>>> = repository.myStory(name)

    fun getMyStory(name: String): LiveData<Result<List<ListStoryItem>>> {
        return repository.myStory(name)
    }

    val listStory: LiveData<Result<List<ListStoryItem>>> = repository.listStory()
}