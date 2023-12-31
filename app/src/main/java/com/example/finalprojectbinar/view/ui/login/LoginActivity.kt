package com.example.finalprojectbinar.view.ui.login

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.util.Patterns
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.finalprojectbinar.databinding.ActivityLoginBinding
import com.example.finalprojectbinar.model.LoginRequest
import com.example.finalprojectbinar.util.AuthVerification
import com.example.finalprojectbinar.util.Enum
import com.example.finalprojectbinar.util.SharedPreferenceHelper
import com.example.finalprojectbinar.util.Status
import com.example.finalprojectbinar.view.ui.MainActivity
import com.example.finalprojectbinar.view.ui.forgetpassword.ForgetPasswordActivity
import com.example.finalprojectbinar.view.ui.register.RegisterActivity
import com.example.finalprojectbinar.view.ui.register.VerifyPhoneActivity
import com.example.finalprojectbinar.viewmodel.MyViewModel
import com.google.android.material.textfield.TextInputLayout
import org.koin.android.ext.android.inject

class LoginActivity : AppCompatActivity() {

    private var _binding: ActivityLoginBinding? = null
    private val binding get() = _binding!!

    private val viewModel: MyViewModel by inject()
    private lateinit var pref: SharedPreferenceHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        pref = SharedPreferenceHelper // Initialize com.example.finalprojectbinar.util.SharedPreferenceHelper

        binding.tvRegister.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
        }

        binding.tvMasukTanpaLogin.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
        }

        binding.btnLogin.setOnClickListener {
            val email = binding.etEmail.text.toString()
            val password = binding.etPassword.text.toString()

            val emailVerification = AuthVerification.emailVerification(email)
            val passwordVerification = AuthVerification.passwordVerification(password)

            if (!emailVerification && !passwordVerification) {
                Toast.makeText(this, "Email & password tidak valid!", Toast.LENGTH_SHORT).show()
            } else if (!emailVerification) {
                Toast.makeText(this, "Email tidak valid!", Toast.LENGTH_SHORT).show()
            } else if (!passwordVerification) {
                Toast.makeText(this, "Password harus memilki 6 karakter / tidak kosong!", Toast.LENGTH_SHORT).show()
            } else {
                login(email, password)
            }

        }

        binding.materialTextView3.setOnClickListener {
            val intent = Intent(this, VerifyPhoneActivity::class.java)
            startActivity(intent)
            finish()
        }
        binding.materialTextView2.setOnClickListener {
            startActivity(
                Intent(this, ForgetPasswordActivity::class.java)
            )
        }
    }

    private fun login(email: String, password: String) {
        if (validateEmail(binding.etEmail, binding.emailLogLay) &&
            validatePassword(binding.etPassword, binding.passwordLogLay)
        ) {
            val loginRequest = LoginRequest(email, password)
            viewModel.postLogin(loginRequest).observe(this) {
                binding.progressBar.visibility = View.VISIBLE
                when (it.status) {
                    Status.SUCCESS -> {
                        val token = it.data?.data?.token.toString()
                        pref.write(Enum.PREF_NAME.value, token)
                        Log.d("LoginSuccess", "Login berhasil. Token: $token")
                        binding.progressBar.visibility = View.GONE
                        navigateToMainActivity()
                    }
                    Status.ERROR -> {
                        val errorMessage = it.message ?: "Error Occured"
                        binding.progressBar.visibility = View.GONE
                        handleLoginError(errorMessage)
                        binding.btnLogin.isEnabled = true
                        binding.btnLogin.isActivated = true
                        Toast.makeText(this@LoginActivity, "Invalid credentials!", Toast.LENGTH_SHORT).show()
                    }
                    Status.LOADING -> {
                        binding.progressBar.visibility = View.VISIBLE
                        binding.btnLogin.isEnabled = false
                        binding.btnLogin.isActivated = false
                        Log.d("LoadingTEST", "Loading")
                    }
                }
            }
        }
    }

    private fun handleLoginError(errorMessage: String) {
        if (errorMessage.contains("email")) {
            binding.emailLogLay.error = "Email tidak terdaftar"
        } else if (errorMessage.contains("password")) {
            binding.passwordLogLay.error = "Kata sandi salah"
        } else {
            binding.emailLogLay.error = null
            binding.passwordLogLay.error = null
        }
    }

    private fun navigateToMainActivity() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }

    // validasi
    private fun validateEmail(edEmail: EditText, layEmail: TextInputLayout): Boolean {
        return when {
            edEmail.text.toString().trim().isEmpty() -> {
                layEmail.error = "Email tidak boleh kosong"
                false
            }
            !Patterns.EMAIL_ADDRESS.matcher(edEmail.text.toString().trim()).matches() -> {
                layEmail.error = "Email harus unik dan valid"
                false
            }
            else -> {
                layEmail.error = null
                true
            }
        }
    }

    private fun validatePassword(edPass: EditText, layPass: TextInputLayout): Boolean {
        return when {
            edPass.text.toString().trim().isEmpty() -> {
                layPass.error = "Password tidak boleh kosong"
                false
            }
            edPass.text.toString().trim().length < 8 -> {
                layPass.error = "Password harus lebih dari 8 karakter"
                false
            }
            else -> {
                layPass.error = null
                true
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}
