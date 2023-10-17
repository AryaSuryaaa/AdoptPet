package com.aryasurya.adoptpet.ui.list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aryasurya.adoptpet.data.Result
import com.aryasurya.adoptpet.data.UserRepository
import com.aryasurya.adoptpet.data.remote.response.DetailStoriesResponse
import com.aryasurya.adoptpet.data.remote.response.FileUploadResponse
import com.aryasurya.adoptpet.data.remote.response.ListStoryItem
import com.aryasurya.adoptpet.data.remote.response.Story
import kotlinx.coroutines.launch

class ListStoryViewModel(private val repository: UserRepository) : ViewModel() {
    val listStory: LiveData<Result<List<ListStoryItem>>> = repository.listStory()


    private val _detailResult = MutableLiveData<Result<Story>>()
    val detailResult: LiveData<Result<Story>> = _detailResult
    fun detailStory(id: String) {
        viewModelScope.launch {
            repository.detailStory(id).collect { result ->
                _detailResult.value = result
            }
        }
    }
}