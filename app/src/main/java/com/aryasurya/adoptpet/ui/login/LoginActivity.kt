package com.aryasurya.adoptpet.ui.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.aryasurya.adoptpet.R
import com.aryasurya.adoptpet.databinding.ActivityLoginBinding
import com.aryasurya.adoptpet.databinding.ActivityRegisterBinding
import com.aryasurya.adoptpet.ui.register.RegisterActivity

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.tvRegisRight.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }
    }
}