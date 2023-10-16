package com.aryasurya.adoptpet.ui.list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aryasurya.adoptpet.data.Result
import com.aryasurya.adoptpet.data.UserRepository
import com.aryasurya.adoptpet.data.remote.response.ListStoryItem
import kotlinx.coroutines.launch

class ListStoryViewModel(repository: UserRepository) : ViewModel() {
    val listStory: LiveData<Result<List<ListStoryItem>>> = repository.listStory()
}