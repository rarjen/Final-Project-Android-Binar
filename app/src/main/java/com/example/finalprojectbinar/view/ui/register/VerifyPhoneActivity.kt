package com.example.finalprojectbinar.view.ui.register

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.finalprojectbinar.databinding.ActivityVerifyPhoneBinding
import com.example.finalprojectbinar.model.OTPRequest
import com.example.finalprojectbinar.util.Enum
import com.example.finalprojectbinar.util.SharedPreferenceHelper
import com.example.finalprojectbinar.util.Status
import com.example.finalprojectbinar.view.fragments.bottomsheets.BottomSheetSuccessRegisterFragment
import com.example.finalprojectbinar.view.ui.login.LoginActivity
import com.example.finalprojectbinar.viewmodel.MyViewModel
import org.koin.android.ext.android.inject

class VerifyPhoneActivity : AppCompatActivity() {

    private var _binding : ActivityVerifyPhoneBinding? = null
    private val binding get() = _binding!!

    private val viewModel: MyViewModel by inject()
    private lateinit var pref: SharedPreferenceHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityVerifyPhoneBinding.inflate(layoutInflater)
        setContentView(binding.root)

        pref = SharedPreferenceHelper
        val email = intent.getStringExtra(RegisterActivity.EMAIL)

        binding.emailTV.text = email
        val tokenRegister = pref.read(Enum.PREF_REGISTER.value).toString()

        binding.ivBack.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
        }

        validateJWT(tokenRegister)

        binding.btnOTPVerification.setOnClickListener {
            val otp = binding.otpTextView.text.toString()
            Toast.makeText(this@VerifyPhoneActivity, otp, Toast.LENGTH_SHORT).show()
            validateRegister(tokenRegister, otp)
            val bottomSheetSuccessRegister = BottomSheetSuccessRegisterFragment()
            bottomSheetSuccessRegister.show(supportFragmentManager, bottomSheetSuccessRegister.tag)
        }

    }

    private fun validateJWT(tokenRegister: String?) {
        if (tokenRegister != null) {
            viewModel.validateJWT("Bearer $tokenRegister").observe(this){
                when (it.status) {
                    Status.SUCCESS -> {
                        val message = it.data?.data?.expiredAt.toString()
                        Log.d("MessageTEST", message)
                        binding.validationReminder.text = message
                    }

                    Status.ERROR -> {
                        val message = it.data?.data?.expiredAt.toString()
                        Toast.makeText(this@VerifyPhoneActivity, "Invalid credentials: $message", Toast.LENGTH_SHORT).show()
                    }

                    Status.LOADING -> {
                        Log.d("LoadingTEST", "Loading")
                    }
                }
            }
        } else {
            Toast.makeText(this@VerifyPhoneActivity, "Anda belum terdaftar!", Toast.LENGTH_SHORT).show()
            val intent = Intent(this@VerifyPhoneActivity, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    private fun validateRegister(tokenRegister: String?, otp: String){
        val otpRequest = OTPRequest(otp)
        viewModel.validateRegister("Bearer $tokenRegister", otpRequest).observe(this){
            when (it.status) {
                Status.SUCCESS -> {
                    val message = it.message
                    Toast.makeText(this@VerifyPhoneActivity, message, Toast.LENGTH_SHORT).show()
                    val bottomSheetSuccessRegister = BottomSheetSuccessRegisterFragment()
                    bottomSheetSuccessRegister.show(supportFragmentManager, bottomSheetSuccessRegister.tag)
                }

                Status.ERROR -> {
                    val message = it.message.toString()
                    Log.d("TESTREGISTERVALID", it.toString())
                    Log.d("OTP", otp)
                    Toast.makeText(this@VerifyPhoneActivity, "Invalid credentials: $message", Toast.LENGTH_SHORT).show()
                }

                Status.LOADING -> {
                    Log.d("LoadingTEST", "Loading")
                }
            }
        }
    }

}