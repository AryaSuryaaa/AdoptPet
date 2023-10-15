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

    private val userPreference: UserPreference by lazy {
        UserPreference.getInstance(dataStore)
    }

    private val viewModel by viewModels<LoginViewModel> {
        ViewModelFactory.getInstance(this)
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

            val usernameToRetrieve = "surya" // Ganti dengan username yang ingin Anda ambil
            viewModel.observeDataUser(usernameToRetrieve)

            viewModel.userData.observe(this) { user ->
                if (user != null) {
                    // Data ditemukan, gunakan data pengguna sesuai kebutuhan
                    Log.d(
                        "DataStore" ,
                        "Username: ${user.username}, Email: ${user.email}, Password: ${user.password}"
                    )
                } else {
                    // Data tidak ditemukan
                    Log.d("DataStore" , "Data tidak ditemukan untuk username: $usernameToRetrieve")
                }
            }
        }
    }
}
