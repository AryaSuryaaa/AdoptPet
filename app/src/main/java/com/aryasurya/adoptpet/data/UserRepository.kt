package com.aryasurya.adoptpet.data

import android.util.Log
import com.aryasurya.adoptpet.data.pref.UserModel
import com.aryasurya.adoptpet.data.pref.UserPreference
import com.aryasurya.adoptpet.data.remote.retrofit.ApiService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class UserRepository private constructor(
    private val apiService: ApiService,
    private val userPreference: UserPreference
) {
//    suspend fun saveUser(user: UserModel) {
//        userPreference.saveUser(user)
//    }

    suspend fun saveUser(users: List<UserModel>) {
        userPreference.saveUsers(users)
    }

    suspend fun observeUserData(username: String): UserModel? {
        return userPreference.observeUsers(username)
    }

    suspend fun logout(user: UserModel) {
        userPreference.logout(user)
    }

    suspend fun saveSession(user: UserModel) {
        userPreference.saveSession(user)
    }

    fun getSession(): Flow<UserModel> {
        return userPreference.getSession()
    }


    suspend fun createUser(name: String, email: String, password: String): Flow<Result<UserModel>> = flow {
        emit(Result.Loading)
        var user: UserModel? = null


        try {
            // Panggil metode createUser pada apiService
            val response = apiService.createUser(name, email, password)
            if (!response.error) {
                Log.d("createUser" , "createUser: ${response.message.toString()}")
//                user = UserModel(
//                    username = name,
//                    email = email,
//                    password = password,
//                    token = "",
//                    isLogin = true
//                )

                emit(Result.Success(UserModel(
                    username = name,
                    email = email,
                    password = password,
                    token = "",
                    isLogin = true
                )))
            } else {
                emit(Result.Error("Error creating user: ${response.message}"))
                Log.d("createUser" , response.message)
            }
        } catch (e: Exception) {
            emit(Result.Error(e.message ?: "An error occurred"))
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