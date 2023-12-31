package com.aryasurya.adoptpet.ui

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.aryasurya.adoptpet.data.StoryRepository
import com.aryasurya.adoptpet.data.UserRepository
import com.aryasurya.adoptpet.di.Injection
import com.aryasurya.adoptpet.ui.account.AccountViewModel
import com.aryasurya.adoptpet.ui.addpost.AddPostViewModel
import com.aryasurya.adoptpet.ui.list.ListStoryViewModel
import com.aryasurya.adoptpet.ui.locationstory.MapsViewModel
import com.aryasurya.adoptpet.ui.login.LoginViewModel
import com.aryasurya.adoptpet.ui.main.MainViewModel
import com.aryasurya.adoptpet.ui.register.RegisterViewModel

class ViewModelFactory(
    private val repository: UserRepository,
    private val storyRepository: StoryRepository
) : ViewModelProvider.NewInstanceFactory() {

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
                ListStoryViewModel(storyRepository) as T
            }
            modelClass.isAssignableFrom(AccountViewModel::class.java) -> {
                AccountViewModel(storyRepository) as T
            }
            modelClass.isAssignableFrom(AddPostViewModel::class.java) -> {
                AddPostViewModel(storyRepository) as T
            }
            modelClass.isAssignableFrom(MapsViewModel::class.java) -> {
                MapsViewModel(storyRepository) as T
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
                    Injection.provideRepository(context) ,
                    Injection.storyRepository(context)
                )
            }.also { INSTANCE = it }
    }
}