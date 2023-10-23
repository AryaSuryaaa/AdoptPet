package com.aryasurya.adoptpet.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.liveData
import com.aryasurya.adoptpet.data.database.ListStoryDatabase
import com.aryasurya.adoptpet.data.pref.UserPreference
import com.aryasurya.adoptpet.data.remote.response.FileUploadResponse
import com.aryasurya.adoptpet.data.remote.response.ListStoryItem
import com.aryasurya.adoptpet.data.remote.response.Story
import com.aryasurya.adoptpet.data.remote.retrofit.ApiConfig
import com.aryasurya.adoptpet.data.remote.retrofit.ApiService
import com.aryasurya.adoptpet.data.paging.StoryPagingSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import okhttp3.MultipartBody
import okhttp3.RequestBody

class StoryRepository private constructor(
    private val apiService: ApiService ,
    private val userPreference: UserPreference ,
) {
//    fun listStory(): LiveData<List<ListStoryItem>> = liveData {
//        emit(emptyList())
//        val token = userPreference.getToken()
//        val apiService = ApiConfig.getApiService(token.toString())
//
//        try {
//            val response = apiService.getStories(1, 5)
//            val stories = response.listStory
//            if (!response.error) {
//                emit(stories)
//            } else {
//                emit(emptyList())
//            }
//        } catch (e: Exception) {
//            emit(emptyList())
//        }
//    }
    fun listStory(): LiveData<PagingData<ListStoryItem>> {
        return Pager(
            config = PagingConfig(
                pageSize = 5
            ),
            pagingSourceFactory = {
                StoryPagingSource(userPreference)
            }
        ).liveData
    }

    suspend fun detailStory(id: String): Flow<Result<Story>> = flow {
        emit(Result.Loading)
        try {
            val response = apiService.getDetailStory(id)
            val story = response.story
            emit(Result.Success(story))
        } catch (e: Exception) {
            emit(Result.Error(e.message ?: "An error occurred"))
        }
    }

    suspend fun postStory(multipartBody: MultipartBody.Part, description: RequestBody): Flow<Result<FileUploadResponse>> = flow {
        emit(Result.Loading)
        try {
            val response = apiService.postStory(multipartBody, description)
            emit(Result.Success(response))
        } catch (e: Exception) {
            emit(Result.Error(e.message ?: "An error occurred"))
        }
    }

    fun myStory(name: String): LiveData<Result<List<ListStoryItem>>> = liveData {
        emit(Result.Loading)
        try {
            val response = apiService.getStories(1, 5)
            val result = response.listStory

            val filterName = result.filter { story ->
                story.name == name
            }

            emit(Result.Success(filterName))
        } catch (e: Exception) {
            emit(Result.Error(e.message ?: "An error occurred"))
        }
    }

    companion object {
        @Volatile
        private var instance: StoryRepository? = null
        fun getInstance(
            database: ListStoryDatabase ,
            apiService: ApiService ,
            userPreference: UserPreference ,
        ) = instance ?: synchronized(this) {
            instance ?: StoryRepository(apiService, userPreference)
        }.also { instance = it }
    }
}