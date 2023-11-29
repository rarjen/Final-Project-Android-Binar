package com.example.finalprojectbinar.view.ui.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.finalprojectbinar.R
import com.example.finalprojectbinar.databinding.ActivityLoginBinding
import com.example.finalprojectbinar.databinding.ActivitySplashScreenBinding
import com.example.finalprojectbinar.view.ui.register.RegisterActivity

class LoginActivity : AppCompatActivity() {

    private var _binding : ActivityLoginBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.tvRegister.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
        }
    }
}