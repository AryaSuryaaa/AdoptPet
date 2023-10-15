package com.aryasurya.adoptpet.data

import com.aryasurya.adoptpet.data.pref.UserModel
import com.aryasurya.adoptpet.data.pref.UserPreference

class UserRepository private constructor(
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



    companion object {
        @Volatile
        private var instance: UserRepository? = null
        fun getInstance(
            userPreference: UserPreference
        ): UserRepository =
            instance ?: synchronized(this) {
                instance ?: UserRepository(userPreference)
            }.also { instance = it }
    }
}