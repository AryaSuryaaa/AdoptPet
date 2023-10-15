package com.aryasurya.adoptpet.ui.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.lifecycle.ViewModel
import androidx.lifecycle.lifecycleScope
import com.aryasurya.adoptpet.R
import com.aryasurya.adoptpet.data.pref.dataStore
import com.aryasurya.adoptpet.databinding.ActivityLoginBinding
import com.aryasurya.adoptpet.databinding.ActivityRegisterBinding
import com.aryasurya.adoptpet.ui.register.RegisterActivity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.map

import androidx.lifecycle.viewModelScope
import com.aryasurya.adoptpet.data.pref.UserModel
import com.aryasurya.adoptpet.data.pref.UserPreference
import com.aryasurya.adoptpet.ui.ViewModelFactory
import com.aryasurya.adoptpet.ui.register.RegisterViewModel
import kotlinx.coroutines.launch

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private val viewModel by viewModels<LoginViewModel> {
        ViewModelFactory.getInstance(this)
    }

    private val userPreference: UserPreference by lazy {
        UserPreference.getInstance(dataStore)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.tvRegisRight.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }

        binding.btnLogin.setOnClickListener {
            val userDataFlow = userPreference.observeUserData()

            lifecycleScope.launch {
                userDataFlow.collect { userData ->
                    if (userData != null) {
                        // Data pengguna sudah tersimpan
                        // Lakukan sesuatu dengan data pengguna jika diperlukan
                        Log.d("DataStore" , "User data found: ${userData.username}, ${userData.email}, ${userData.password}")
                    } else {
                        // Data pengguna belum tersimpan
                        Log.d("DataStore" , "User data not found")
                    }
                }
            }
        }
    }
}
