package com.aryasurya.adoptpet.ui.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import com.aryasurya.adoptpet.data.Result
import com.aryasurya.adoptpet.data.pref.UserModel
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

        viewModel.loginResult.observe(this) { result ->
            when(result) {
                is Result.Loading -> {
                    binding.progressBar.visibility = View.VISIBLE
                }
                is Result.Success -> {
                    binding.progressBar.visibility = View.GONE
                    viewModel.saveSession(UserModel(result.data.loginResult.token))
                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                }
                is Result.Error -> {
                    binding.progressBar.visibility = View.GONE
                    Toast.makeText(this, "Username/password is incorrect", Toast.LENGTH_SHORT).show()
                }
            }
        }




        binding.tvRegisRight.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }

        binding.btnLogin.setOnClickListener {
            val email = binding.tiUsernameLogin.editText?.text.toString()
            val password = binding.tiPasswordLogin.editText?.text.toString()

            // Memeriksa apakah username dan password sesuai dengan yang tersimpan di DataStore
//            viewModel.validateCredentials(username, password)

            viewModel.login(email, password)
        }
    }

}
