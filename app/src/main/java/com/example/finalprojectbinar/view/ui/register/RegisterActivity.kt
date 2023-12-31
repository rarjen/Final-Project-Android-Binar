package com.example.finalprojectbinar.view.ui.register

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.util.Patterns
import android.widget.EditText
import android.widget.Toast
import com.example.finalprojectbinar.databinding.ActivityRegisterBinding
import com.example.finalprojectbinar.model.RegisterRequest
import com.example.finalprojectbinar.util.Enum
import com.example.finalprojectbinar.util.SharedPreferenceHelper
import com.example.finalprojectbinar.util.Status
import com.example.finalprojectbinar.view.ui.login.LoginActivity
import com.example.finalprojectbinar.viewmodel.MyViewModel
import com.google.android.material.textfield.TextInputLayout
import org.koin.android.ext.android.inject

class RegisterActivity : AppCompatActivity() {

    private var _binding : ActivityRegisterBinding? = null
    private val binding get() = _binding!!

    private val viewModel: MyViewModel by inject()
    private lateinit var pref: SharedPreferenceHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        pref = SharedPreferenceHelper

        binding.ivBack.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
        }

        binding.buttonRegister.setOnClickListener {
            startActivity(Intent(this, VerifyPhoneActivity::class.java))
        }

        binding.tvLoginHere.setOnClickListener{
            startActivity(Intent(this, LoginActivity::class.java))
        }

        binding.buttonRegister.setOnClickListener {
            val fullName = binding.etNama.text.toString()
            val email = binding.etEmail.text.toString()
            val phone = binding.etNoTelp.text.toString()
            val password = binding.etPassword.text.toString()
            register(email, fullName, phone, password)
        }

    }

    private fun register(email: String, fullName: String, phone: String, password: String) {
        if (
            validateEmail(binding.etEmail, binding.txtInputEmail) &&
            validateName(binding.etNama, binding.layInputNama) &&
            validatePhone(binding.etNoTelp, binding.txtInputNotelp) &&
            validatePassword(binding.etPassword, binding.repeatPassword)
            ){
            val registerRequest = RegisterRequest(email, fullName, phone, password)
            viewModel.postRegister(registerRequest).observe(this){
                when (it.status) {
                    Status.SUCCESS -> {
                        val token = it.data?.data?.token.toString()
                        pref.write(Enum.PREF_REGISTER.value, token)
                        Log.d("Register Success", "Register berhasil. Token: $token")
                        navigateToVerifyOTP()
                    }
                    Status.ERROR -> {
                        val errorMessage = it.message ?: "Error Occured"
                        Log.d("registerRequest", registerRequest.toString())
                        Toast.makeText(this@RegisterActivity, "Invalid credentials: $errorMessage", Toast.LENGTH_SHORT).show()
                    }
                    Status.LOADING -> {
                        Log.d("LoadingTEST", "Loading")
                    }
                }
            }
        }
    }

    private fun navigateToVerifyOTP() {
        val intent = Intent(this, VerifyPhoneActivity::class.java)
        intent.putExtra(EMAIL, binding.etEmail.text.toString())
        startActivity(intent)
        finish()
    }

//    private fun handleLoginError(errorMessage: String) {
//        if (errorMessage.contains("email")) {
//            binding.emailLogLay.error = "Email tidak terdaftar"
//        } else if (errorMessage.contains("password")) {
//            binding.passwordLogLay.error = "Kata sandi salah"
//        } else {
//            binding.emailLogLay.error = null
//            binding.passwordLogLay.error = null
//        }
//    }

    private fun validatePhone(edPhone: EditText, layPhone: TextInputLayout): Boolean {
        return when {
            edPhone.text.toString().trim().isEmpty() -> {
                layPhone.error = "Phone tidak boleh kosong"
                false
            }
            edPhone.text.toString().trim().length < 12 -> {
                layPhone.error = "Nomor telepon minimal 12 karakter"
                false
            }
            else -> {
                layPhone.error = null
                true
            }
        }
    }

    private fun validateName(edName: EditText, layName: TextInputLayout): Boolean {
        return when {
            edName.text.toString().trim().isEmpty() -> {
                layName.error = "Nama tidak boleh kosong"
                false
            }
            else -> {
                layName.error = null
                true
            }
        }
    }

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

    companion object {
        const val EMAIL = "email"
    }
}