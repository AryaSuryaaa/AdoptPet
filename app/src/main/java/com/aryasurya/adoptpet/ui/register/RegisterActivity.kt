package com.aryasurya.adoptpet.ui.register

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.aryasurya.adoptpet.R
import com.aryasurya.adoptpet.databinding.ActivityRegisterBinding

class RegisterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val toolbar = binding.toolbar
        setSupportActionBar(toolbar)

        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            setDisplayHomeAsUpEnabled(true)
            setDisplayShowTitleEnabled(false)
        }

        toolbar.setNavigationOnClickListener{
            onBackPressed()
        }
    }
}