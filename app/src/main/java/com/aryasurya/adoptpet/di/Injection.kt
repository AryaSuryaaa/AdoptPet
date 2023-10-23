package com.aryasurya.adoptpet.di

import android.content.Context
import com.aryasurya.adoptpet.data.StoryRepository
import com.aryasurya.adoptpet.data.UserRepository
import com.aryasurya.adoptpet.data.database.ListStoryDatabase
import com.aryasurya.adoptpet.data.pref.UserPreference
import com.aryasurya.adoptpet.data.pref.dataStore
import com.aryasurya.adoptpet.data.remote.retrofit.ApiConfig
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking

object Injection {
    fun provideRepository(context: Context):UserRepository {
        val pref = UserPreference.getInstance(context.dataStore)
        val user = runBlocking { pref.getSession().first() }
        val apiService = ApiConfig.getApiService(user.token)
        val userPreference = UserPreference.getInstance(context.dataStore)
        return UserRepository.getInstance(apiService, userPreference)
    }

    fun storyRepository(context: Context):StoryRepository {
        val database = ListStoryDatabase.getDatabase(context)
        val pref = UserPreference.getInstance(context.dataStore)
        val user = runBlocking { pref.getSession().first() }
        val apiService = ApiConfig.getApiService(user.token)
        return StoryRepository.getInstance(database, apiService, pref)
    }
}