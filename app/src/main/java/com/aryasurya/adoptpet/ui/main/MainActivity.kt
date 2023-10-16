package com.aryasurya.adoptpet.ui.main

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import com.aryasurya.adoptpet.R
import com.aryasurya.adoptpet.databinding.ActivityMainBinding
import com.aryasurya.adoptpet.ui.ViewModelFactory
import com.aryasurya.adoptpet.ui.login.LoginActivity
import com.aryasurya.adoptpet.ui.login.LoginViewModel

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private val loginViewModel by viewModels<LoginViewModel> {
        ViewModelFactory.getInstance(this)
    }
    private val mainViewModel by viewModels<MainViewModel> {
        ViewModelFactory.getInstance(this)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

//        mainViewModel.getSession().observe(this) { user ->
//            Log.d("logout" , "onCreate: ${user.username} ${user?.isLogin}")
//            if (!user?.isLogin!!) {
//                startActivity(Intent(this, LoginActivity::class.java))
//                finish()
//            }
//
//            binding.btnLogout.setOnClickListener {
//                mainViewModel.logout(user)
//            }
//        }

    }
}