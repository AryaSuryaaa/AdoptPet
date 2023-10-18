package com.aryasurya.adoptpet.ui.account

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.aryasurya.adoptpet.data.Result
import com.aryasurya.adoptpet.data.StoryRepository
import com.aryasurya.adoptpet.data.UserRepository
import com.aryasurya.adoptpet.data.pref.UserModel
import com.aryasurya.adoptpet.data.remote.response.ListStoryItem
import kotlinx.coroutines.launch

class AccountViewModel(private val repository: StoryRepository): ViewModel() {

    fun getMyStory(name: String): LiveData<Result<List<ListStoryItem>>> {
        return repository.myStory(name)
    }
}