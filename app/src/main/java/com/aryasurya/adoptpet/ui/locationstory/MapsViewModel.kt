package com.aryasurya.adoptpet.ui.locationstory

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.aryasurya.adoptpet.data.Result
import com.aryasurya.adoptpet.data.StoryRepository
import com.aryasurya.adoptpet.data.remote.response.ListStoryItem

class MapsViewModel(private val repository: StoryRepository): ViewModel() {
    fun getListMap(): LiveData<Result<List<ListStoryItem>>> {
        return repository.getListMap()
    }
}