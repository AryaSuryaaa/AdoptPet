package com.aryasurya.adoptpet.ui.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import com.aryasurya.adoptpet.databinding.ActivityLoginBinding
import com.aryasurya.adoptpet.ui.register.RegisterActivity
import com.aryasurya.adoptpet.ui.ViewModelFactory
import com.aryasurya.adoptpet.ui.main.MainActivity

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding

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
            val username = binding.tiUsernameLogin.editText?.text.toString()
            val password = binding.tiPasswordLogin.editText?.text.toString()

            // Memeriksa apakah username dan password sesuai dengan yang tersimpan di DataStore
            viewModel.validateCredentials(username, password)
        }

        viewModel.userData.observe(this) { user ->
            Log.d("DataStore" , "Username: ${user?.username}, Email: ${user?.email}, Password: ${user?.password}" )
            if (user != null) {
                // Data pengguna ditemukan, izinkan pengguna untuk masuk
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
            } else {
                // Data pengguna tidak ditemukan atau username/password salah
                Toast.makeText(this, "Username/password is incorrect", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
