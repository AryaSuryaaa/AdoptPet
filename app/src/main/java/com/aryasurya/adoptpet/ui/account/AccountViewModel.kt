package com.aryasurya.adoptpet.ui.account

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.aryasurya.adoptpet.data.Result
import com.aryasurya.adoptpet.data.StoryRepository
import com.aryasurya.adoptpet.data.remote.response.ListStoryItem

class AccountViewModel(private val repository: StoryRepository): ViewModel() {

    fun getMyStory(name: String): LiveData<Result<List<ListStoryItem>>> {
        return repository.myStory(name)
    }
}