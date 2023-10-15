package com.aryasurya.adoptpet.di

import android.content.Context
import com.aryasurya.adoptpet.data.UserRepository
import com.aryasurya.adoptpet.data.pref.UserPreference
import com.aryasurya.adoptpet.data.pref.dataStore

object Injection {
    fun provideRepository(context: Context):UserRepository {
        val pref = UserPreference.getInstance(context.dataStore)
        return UserRepository.getInstance(pref)
    }
}