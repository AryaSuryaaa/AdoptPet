package com.aryasurya.adoptpet.data.pref

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.preferencesOf
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map


val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "register")
class UserPreference private constructor(private val dataStore: DataStore<Preferences>) {


    suspend fun saveUser(user: UserModel) {
        dataStore.edit { preferences ->
            preferences[USERNAME_KEY] = user.username
            preferences[EMAIL_KEY] = user.email
            preferences[PASSWORD_KEY] = user.password
        }
    }


    fun observeUserData(): Flow<UserModel?> {
        val usernameKey = stringPreferencesKey("username")
        return dataStore.data.map { preferences ->
            val username = preferences[usernameKey]
            if (username != null) {
                val emailKey = stringPreferencesKey("email")
                val passwordKey = stringPreferencesKey("password")
                val tokenKey = stringPreferencesKey("token")
                val isLoginKey = booleanPreferencesKey("isLogin")

                UserModel(
                    username,
                    preferences[emailKey] ?: "",
                    preferences[passwordKey] ?: "",
                    preferences[tokenKey] ?: "",
                    preferences[isLoginKey] ?: false
                )
            } else {
                null
            }
        }
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