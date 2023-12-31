package com.example.finalprojectbinar.view.ui.forgetpassword

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.util.Patterns
import android.view.View
import android.widget.EditText
import android.widget.Toast
import com.example.finalprojectbinar.databinding.ActivityForgetPasswordBinding
import com.example.finalprojectbinar.util.Status
import com.example.finalprojectbinar.viewmodel.MyViewModel
import com.google.android.material.textfield.TextInputLayout
import org.koin.android.ext.android.inject

class ForgetPasswordActivity : AppCompatActivity() {

    private lateinit var binding: ActivityForgetPasswordBinding
    private val viewModel:MyViewModel by inject()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityForgetPasswordBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnKonfirmasi.setOnClickListener {
            if(validateEmail(binding.etEmail,binding.emailForgotPassword)){
                binding.btnKonfirmasi.visibility=View.GONE
                viewModel.postForgetPassword(binding.etEmail.text.toString()).observe(this
                ){
                    when(it.status){
                        Status.SUCCESS->{
                            Toast.makeText(this,"Periksa Kembali Email Anda",Toast.LENGTH_LONG).show()
                            finish()
                        }
                        else->{
                            Log.d("TESTFP","${binding.etEmail.text.toString()} ${it.message} ${it.message.toString().takeLast(4).trim()}")
                            if(it.message.toString().takeLast(4).trim()=="401"){
                                Toast.makeText(this,"Invalid User",Toast.LENGTH_LONG).show()
                            }
                            binding.btnKonfirmasi.visibility=View.VISIBLE
                        }
                    }
                }
            }
        }
    }

    private fun validateEmail(etEmail: EditText, emailForgotPassword: TextInputLayout):Boolean {
        return when {
            etEmail.text.toString().trim().isEmpty() -> {
                emailForgotPassword.error = "Email tidak boleh kosong"
                false
            }
            !Patterns.EMAIL_ADDRESS.matcher(etEmail.text.toString().trim()).matches() -> {
                emailForgotPassword.error = "Format Email Salah"
                false
            }
            else -> {
                emailForgotPassword.error = null
                true
            }
        }
    }
}