package com.aryasurya.adoptpet.data.pref

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map


val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "register")
class UserPreference private constructor(private val dataStore: DataStore<Preferences>) {

    suspend fun saveUsers(users: List<UserModel>) {
        dataStore.edit { preferences ->
            for (user in users) {
                val userKey = stringPreferencesKey(user.username)

                preferences[stringPreferencesKey("${userKey}_username")] = user.username
                preferences[stringPreferencesKey("${userKey}_email")] = user.email
                preferences[stringPreferencesKey("${userKey}_password")] = user.password
                preferences[booleanPreferencesKey("${userKey}_login")] = user.isLogin
            }
        }
    }

    suspend fun observeUsers(username: String): UserModel? {
        return dataStore.data.map { preferences ->
            val usernameKey = stringPreferencesKey("${username}_username")
            val emailKey = stringPreferencesKey("${username}_email")
            val passwordKey = stringPreferencesKey("${username}_password")
            val isLoginKey = booleanPreferencesKey("${username}_isLogin")

            val username = preferences[usernameKey]
            val email = preferences[emailKey]
            val password = preferences[passwordKey]
            val isLogin = preferences[isLoginKey] ?: false

            if (username != null) {
                UserModel(username, email ?: "", password ?: "", isLogin)
            } else {
                null
            }
        }.first()
    }

    suspend fun saveSession(user: UserModel) {
        dataStore.edit { preferences ->
            preferences[USERNAME_KEY] = user.username
            preferences[EMAIL_KEY] = user.email
            preferences[IS_LOGIN_KEY] = true
        }
    }

    fun getSession(): Flow<UserModel> {
        return dataStore.data.map { preferences ->
            UserModel(
                preferences[USERNAME_KEY] ?: "",
                preferences[EMAIL_KEY] ?: "",
                preferences[PASSWORD_KEY] ?: "",
                preferences[IS_LOGIN_KEY] ?: false
            )
        }
    }

    suspend fun logout(user: UserModel) {
        dataStore.edit { preferences ->
            preferences[USERNAME_KEY] = user.username
            preferences[EMAIL_KEY] = user.email
            preferences[IS_LOGIN_KEY] = false
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