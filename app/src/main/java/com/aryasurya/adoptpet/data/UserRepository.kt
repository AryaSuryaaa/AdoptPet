package com.aryasurya.adoptpet.data

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.aryasurya.adoptpet.data.pref.UserModel
import com.aryasurya.adoptpet.data.pref.UserPreference
import com.aryasurya.adoptpet.data.remote.response.CreateUserResponse
import com.aryasurya.adoptpet.data.remote.response.ErrorResponse
import com.aryasurya.adoptpet.data.remote.response.LoginResponse
import com.aryasurya.adoptpet.data.remote.retrofit.ApiService
import com.google.gson.Gson
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
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
//        Log.d("createUser" , "createUser: ${response.message}")
//        if (user != null) emit(Result.Success(user))
    }

//    suspend fun createUser(name: String, email: String, password: String): Flow<Result<UserModel>> = flow {
//        emit(Result.Loading)
//
//        // Panggil metode createUser pada apiService
//        val response = apiService.createUser(name, email, password)
//
//        if (!response.error) {
//            // Jika respons berhasil, Anda dapat mengembalikan UserModel dengan token dan isLogin yang sesuai
//            Log.d("createUser" , "createUser: ${response.message}")
//            emit(Result.Success(UserModel(
//                username = name,
//                email = email,
//                password = password,
//                token = "",
//                isLogin = true
//            )))
//        } else {
//            // Jika ada kesalahan dalam respons, emit Result.Error
//            emit(Result.Error("Error creating user: ${response.message}"))
//            Log.d("createUser" , response.message)
//        }
//    }

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