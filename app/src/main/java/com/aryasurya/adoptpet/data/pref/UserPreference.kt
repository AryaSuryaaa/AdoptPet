package com.aryasurya.adoptpet.data.pref

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.preferencesOf
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.google.gson.Gson
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map


val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "register")
class UserPreference private constructor(private val dataStore: DataStore<Preferences>) {


//    suspend fun saveUser(user: UserModel) {
//        dataStore.edit { preferences ->
//            preferences[USERNAME_KEY] = user.username
//            preferences[EMAIL_KEY] = user.email
//            preferences[PASSWORD_KEY] = user.password
//        }
//    }

    suspend fun saveUsers(users: List<UserModel>) {
        dataStore.edit { preferences ->
            for (user in users) {
                val userKey = stringPreferencesKey(user.username)

                preferences[stringPreferencesKey("${userKey}_username")] = user.username
                preferences[stringPreferencesKey("${userKey}_email")] = user.email
                preferences[stringPreferencesKey("${userKey}_password")] = user.password
            }
        }
    }
// MASIH GAGAL
//    fun observeUserData(username: String): Flow<UserModel?> {
//        val usernameKey = stringPreferencesKey("username")
//
//        return dataStore.data.map { preferences ->
//            val storedUsername = preferences[usernameKey]
//
//            if (storedUsername == username) {
//                val emailKey = stringPreferencesKey("email")
//                val passwordKey = stringPreferencesKey("password")
//                val tokenKey = stringPreferencesKey("token")
//                val isLoginKey = booleanPreferencesKey("isLogin")
//
//                UserModel(
//                    storedUsername,
//                    preferences[emailKey] ?: "",
//                    preferences[passwordKey] ?: "",
//                    preferences[tokenKey] ?: "",
//                    preferences[isLoginKey] ?: false
//                )
//            } else {
//                null
//            }
//        }
//    }

    suspend fun observeUsers(username: String): UserModel? {
        return dataStore.data.map { preferences ->
            val usernameKey = stringPreferencesKey("${username}_username")
            val emailKey = stringPreferencesKey("${username}_email")
            val passwordKey = stringPreferencesKey("${username}_password")

            val username = preferences[usernameKey]
            val email = preferences[emailKey]
            val password = preferences[passwordKey]

            if (username != null) {
                UserModel(username, email ?: "", password ?: "")
            } else {
                null
            }
        }.first()
    }

    companion object {
        @Volatile
        private var INSTANCE: UserPreference? = null

        private val USERNAME_KEY = stringPreferencesKey("username")
        private val EMAIL_KEY = stringPreferencesKey("email")
        private val PASSWORD_KEY = stringPreferencesKey("password")
        private val TOKEN_KEY = stringPreferencesKey("token")
        private val IS_LOGIN_KEY = booleanPreferencesKey("isLogin")

        fun getInstance(dataStore: DataStore<Preferences>):UserPreference {
            return INSTANCE ?: synchronized(this) {
                val instance = UserPreference(dataStore)
                INSTANCE = instance
                instance
            }
        }
    }
}