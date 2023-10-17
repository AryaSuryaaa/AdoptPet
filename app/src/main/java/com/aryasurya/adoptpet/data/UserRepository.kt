package com.aryasurya.adoptpet.data

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.aryasurya.adoptpet.data.pref.UserModel
import com.aryasurya.adoptpet.data.pref.UserPreference
import com.aryasurya.adoptpet.data.remote.response.CreateUserResponse
import com.aryasurya.adoptpet.data.remote.response.ErrorResponse
import com.aryasurya.adoptpet.data.remote.response.FileUploadResponse
import com.aryasurya.adoptpet.data.remote.response.ListStoryItem
import com.aryasurya.adoptpet.data.remote.response.LoginResponse
import com.aryasurya.adoptpet.data.remote.response.StoriesResponse
import com.aryasurya.adoptpet.data.remote.retrofit.ApiService
import com.google.gson.Gson
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.HttpException

class UserRepository private constructor(
    private val apiService: ApiService,
    private val userPreference: UserPreference
) {

    suspend fun logout() {
        userPreference.logout()
    }

    suspend fun saveSession(user: UserModel) {
        userPreference.saveSession(user)
    }

    fun getSession(): Flow<UserModel> {
        return userPreference.getSession()
    }


    suspend fun createUser(name: String, email: String, password: String): Flow<Result<CreateUserResponse>> = flow {
        emit(Result.Loading)
        try {
            // Panggil metode createUser pada apiService
            val response = apiService.createUser(name, email, password)
            emit(Result.Success(response))
        } catch (t: Throwable) {
            when (t) {
                is HttpException -> {
                    try {
                        val errorResponse = Gson().fromJson(
                            t.response()?.errorBody()?.charStream(),
                            ErrorResponse::class.java
                        )
                        Log.e("TAG_LOGIN", "onFailure: ${errorResponse.message}")
                        emit(Result.Error(errorResponse.message))
                    } catch (e: Exception) {
                        Log.e("TAG_LOGIN", "onFailure: ${e.message.toString()}")
                        emit(Result.Error(e.message.toString()))
                    }
                }

                else -> {
                    Log.e("TAG_LOGIN", "onFailure: ${t.message.toString()}")
                    emit(Result.Error(t.message.toString()))
                }
            }
        }
    }

    suspend fun login(email: String, password: String): Flow<Result<LoginResponse>> = flow {
        emit(Result.Loading)
        try {
            // Panggil metode createUser pada apiService
            val response = apiService.login(email, password)
            emit(Result.Success(response))
        } catch (e: Exception) {
            emit(Result.Error(e.message ?: "An error occurred"))
        }
//        Log.d("createUser" , "createUser: ${response.message}")
//        if (user != null) emit(Result.Success(user))
    }

    fun listStory(): LiveData<Result<List<ListStoryItem>>> = liveData {
        emit(Result.Loading)
        try {
            val response = apiService.getStories()
            val stories = response.listStory
            emit(Result.Success(stories))
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


    companion object {
        @Volatile
        private var instance: UserRepository? = null
        fun getInstance(
            apiService: ApiService ,
            userPreference: UserPreference
        ): UserRepository =
            instance ?: synchronized(this) {
                instance ?: UserRepository(apiService, userPreference)
            }.also { instance = it }
    }
}