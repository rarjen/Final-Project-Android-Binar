package com.example.finalprojectbinar.view.ui.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.finalprojectbinar.api.APIClient
import com.example.finalprojectbinar.databinding.ActivityLoginBinding
import com.example.finalprojectbinar.model.LoginRequest
import com.example.finalprojectbinar.util.Enum
import com.example.finalprojectbinar.util.SharedPreferenceHelper
import com.example.finalprojectbinar.util.Status
import com.example.finalprojectbinar.view.ui.MainActivity
import com.example.finalprojectbinar.view.ui.register.RegisterActivity
import com.example.finalprojectbinar.viewmodel.MyViewModel
import org.koin.android.ext.android.inject

class LoginActivity : AppCompatActivity() {

    private var _binding : ActivityLoginBinding? = null
    private val binding get() = _binding!!

    private val viewModel: MyViewModel by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.tvRegister.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
        }

        binding.tvMasukTanpaLogin.setOnClickListener{
            startActivity(Intent(this, MainActivity::class.java))
        }

        binding.btnLogin.setOnClickListener {
//            Log.d("TESTENUM", Enum.PREF_NAME.value)
            val email = binding.etEmail.toString()
            val password = binding.etPassword.toString()
//            login(email, password)
        }
    }


    private fun login(email: String, password: String) {
        val loginRequest = LoginRequest(email, password)
        viewModel.postLogin(loginRequest).observe(this) {
            when(it.status) {
                Status.SUCCESS -> {
                    val token = it.data?.token.toString()
                    SharedPreferenceHelper.write(Enum.PREF_NAME.value, token)
                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                }
                Status.ERROR -> {
                    Log.d("ErrorTEST", "Error Occured!")
                }
                Status.LOADING -> {
                    Log.d("LoadingTEST", "Loading")
                }
            }
        }
    }
}