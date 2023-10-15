package com.aryasurya.adoptpet.ui.register

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.util.Patterns
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.viewmodel.viewModelFactory
import com.aryasurya.adoptpet.R
import com.aryasurya.adoptpet.data.pref.UserModel
import com.aryasurya.adoptpet.databinding.ActivityRegisterBinding
import com.aryasurya.adoptpet.ui.ViewModelFactory
import com.aryasurya.adoptpet.ui.login.LoginActivity

class RegisterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterBinding
    private val viewModel by viewModels<RegisterViewModel> {
        ViewModelFactory.getInstance(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

//        SETUP TOOLBAR
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

        binding.tvLoginRight.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }

//        VALIDASDI EMAIL
        validEmail()
//        PASSWORD ERROR
        matchPassword()
        activeButton()

    }

    private fun activeButton() {
        val username = binding.tiUsernameRegister
        val email = binding.tiEmailRegister
        val password = binding.tiPasswordRegister
        val btnRegister = binding.btnRegister

        btnRegister.setOnClickListener {
            val inputUsername = username.editText?.text.toString()
            val inputEmail = email.editText?.text.toString()
            val inputPassword = password.editText?.text.toString()

            if (isUsernameValid(inputUsername) && isEmailValid(inputEmail) && isPasswordValid(inputPassword)) {
                setupUser(inputUsername, inputEmail, inputPassword)
                Toast.makeText(this, "Registration successful", Toast.LENGTH_SHORT).show()
                finish()
            }
            else {
                username.isErrorEnabled = false
                email.isErrorEnabled = false
                password.isErrorEnabled = false

                if (!isUsernameValid(inputUsername)) {
                    username.error = "Fill the username"
                }

                if (!isEmailValid(inputEmail)) {
                    email.error = "Invalid email address"
                }

                if (!isPasswordValid(inputPassword)) {
                    password.error = "Minimal 8 character"
                }
            }
        }
    }

    private fun matchPassword() {
        val passwordLayout = binding.tiPasswordRegister
        val password = passwordLayout.editText

        password?.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence? , p1: Int , p2: Int , p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence? , p1: Int , p2: Int , p3: Int) {
                if (p0 != null && p0.length <= 7) {
                    passwordLayout.apply {
                        isErrorEnabled = true
                        errorIconDrawable = null
                        error = getString(R.string.eror_text)
                    }
                } else {
                    passwordLayout.apply {
                        isErrorEnabled = false
                        error = null
                    }
                }
            }

            override fun afterTextChanged(p0: Editable?) {
            }

        })

        val confirmPasswordLayout = binding.tiConfirmPasswordRegister
        val confirmPassword = confirmPasswordLayout.editText

        confirmPassword?.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence? , p1: Int , p2: Int , p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence? , p1: Int , p2: Int , p3: Int) {
                if (p0.toString() != password?.text.toString()) {
                    confirmPasswordLayout.isErrorEnabled = true
                    confirmPasswordLayout.error = getString(R.string.confirm_password_eror_text)
                } else {
                    confirmPasswordLayout.isErrorEnabled = false
                    confirmPasswordLayout.error = null
                }
            }

            override fun afterTextChanged(p0: Editable?) {
            }
        })
    }

    private fun validEmail() {
        val inputEmailLayout = binding.tiEmailRegister
        val inputEmail = binding.inputEmailRegister
        inputEmail.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence? , p1: Int , p2: Int , p3: Int) {}

            override fun onTextChanged(p0: CharSequence? , p1: Int , p2: Int , p3: Int) {
            }

            override fun afterTextChanged(p0: Editable?) {
                val email = p0.toString()
                val isValid = Patterns.EMAIL_ADDRESS.matcher(email).matches()

                if (!isValid) {
                    inputEmailLayout.error = "Invalid email address"
                } else {
                    // Hapus pesan kesalahan jika email valid
                    inputEmailLayout.isErrorEnabled = false
                    inputEmailLayout.error = null
                }
            }
        })
    }

    private fun isUsernameValid(username: String): Boolean {
        return !username.isEmpty()
    }

    private fun isEmailValid(email: String): Boolean {
        return Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    private fun isPasswordValid(password: String): Boolean {
        return password.length >= 8 // Contoh: Password harus memiliki panjang minimal 6 karakter
    }
    private fun setupUser(username: String, email: String, password: String) {
        viewModel.saveUser(UserModel(username, email, password, "sample_token"))
    }
}