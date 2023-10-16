package com.aryasurya.adoptpet.ui

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.aryasurya.adoptpet.data.UserRepository
import com.aryasurya.adoptpet.di.Injection
import com.aryasurya.adoptpet.ui.account.AccountViewModel
import com.aryasurya.adoptpet.ui.list.ListStoryViewModel
import com.aryasurya.adoptpet.ui.login.LoginViewModel
import com.aryasurya.adoptpet.ui.main.MainViewModel
import com.aryasurya.adoptpet.ui.register.RegisterViewModel

class ViewModelFactory(private val repository: UserRepository) : ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(RegisterViewModel::class.java) -> {
                RegisterViewModel(repository) as T
            }
            modelClass.isAssignableFrom(LoginViewModel::class.java) -> {
                LoginViewModel(repository) as T
            }
            modelClass.isAssignableFrom(MainViewModel::class.java) -> {
                MainViewModel(repository) as T
            }
            modelClass.isAssignableFrom(ListStoryViewModel::class.java) -> {
                ListStoryViewModel(repository) as T
            }
            modelClass.isAssignableFrom(AccountViewModel::class.java) -> {
                AccountViewModel(repository) as T
            }
            else -> throw IllegalArgumentException("Unknown ViewModel Class: " + modelClass.name)
        }
    }

    companion object {
        @Volatile
        private var INSTANCE: ViewModelFactory? = null

        @JvmStatic
        fun getInstance(context: Context) =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: ViewModelFactory(
                    Injection.provideRepository(context)
                )
            }.also { INSTANCE = it }
    }
}