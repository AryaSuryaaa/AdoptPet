package com.aryasurya.adoptpet.di

import android.content.Context
import com.aryasurya.adoptpet.data.UserRepository
import com.aryasurya.adoptpet.data.pref.UserPreference
import com.aryasurya.adoptpet.data.pref.dataStore
import com.aryasurya.adoptpet.data.remote.retrofit.ApiConfig

object Injection {
    fun provideRepository(context: Context):UserRepository {
        val pref = UserPreference.getInstance(context.dataStore)
        val apiService = ApiConfig.getApiService()
        return UserRepository.getInstance(apiService, pref)
    }
}