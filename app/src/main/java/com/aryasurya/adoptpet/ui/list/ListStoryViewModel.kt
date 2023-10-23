package com.aryasurya.adoptpet.ui.list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.aryasurya.adoptpet.data.Result
import com.aryasurya.adoptpet.data.StoryRepository
import com.aryasurya.adoptpet.data.UserRepository
import com.aryasurya.adoptpet.data.pref.UserModel
import com.aryasurya.adoptpet.data.remote.response.ListStoryItem
import com.aryasurya.adoptpet.data.remote.response.Story
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class ListStoryViewModel(private val repository: StoryRepository) : ViewModel() {
//    private val _lisResult = MutableLiveData<Result<List<ListStoryItem>>?>()
//    val listResult: LiveData<Result<List<ListStoryItem>>?> = _lisResult
//
//    fun listStory() {
//        viewModelScope.launch {
//            repository.listStory().collect{ result ->
//                _lisResult.value = result
//            }
//        }
//    }

//    fun listStory(): LiveData<List<ListStoryItem>> = repository.listStory()
    val listStory: LiveData<PagingData<ListStoryItem>> = repository.listStory().cachedIn(viewModelScope)

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