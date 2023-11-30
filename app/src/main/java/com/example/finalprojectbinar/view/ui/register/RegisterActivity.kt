package com.example.finalprojectbinar.view.ui.register

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.finalprojectbinar.R
import com.example.finalprojectbinar.databinding.ActivityLoginBinding
import com.example.finalprojectbinar.databinding.ActivityRegisterBinding
import com.example.finalprojectbinar.view.ui.login.LoginActivity

class RegisterActivity : AppCompatActivity() {

    private var _binding : ActivityRegisterBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.ivBack.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
        }

        binding.buttonRegister.setOnClickListener {
            startActivity(Intent(this, VerifyPhoneActivity::class.java))
        }

        binding.tvLoginHere.setOnClickListener{
            startActivity(Intent(this, LoginActivity::class.java))
        }

    }
}