package com.aryasurya.adoptpet.ui.addpost

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aryasurya.adoptpet.data.Result
import com.aryasurya.adoptpet.data.StoryRepository
import com.aryasurya.adoptpet.data.remote.response.FileUploadResponse
import kotlinx.coroutines.launch
import okhttp3.MultipartBody
import okhttp3.RequestBody

class AddPostViewModel(private val repository: StoryRepository): ViewModel() {

    private val _postResult = MutableLiveData<Result<FileUploadResponse>>()
    val postResult: LiveData<Result<FileUploadResponse>> = _postResult

    fun postStory(multipartBody: MultipartBody.Part, description: RequestBody) {
        viewModelScope.launch {
            repository.postStory(multipartBody, description).collect { result ->
                _postResult.value = result
            }
        }
    }

    fun postStoryWithLocation(multipartBody: MultipartBody.Part, description: RequestBody, lat: Double, lon: Double) {
        viewModelScope.launch {
            repository.postStoryWithLocation(multipartBody, description, lat, lon).collect { result ->
                _postResult.value = result
            }
        }
    }
}