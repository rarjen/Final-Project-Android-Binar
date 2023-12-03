package com.example.finalprojectbinar.view.ui.register

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.widget.Toast
import com.example.finalprojectbinar.databinding.ActivityVerifyPhoneBinding

class VerifyPhoneActivity : AppCompatActivity() {

    private var _binding : ActivityVerifyPhoneBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityVerifyPhoneBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.ivBack.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
        }

//        countDown()

    }

    private fun countDown() {
        object : CountDownTimer(60000, 1000){
            override fun onTick(millisUntilFinished: Long) {
                binding.tvTimer.text = "${millisUntilFinished / 1000}"
            }

            override fun onFinish() {
                Toast.makeText(this@VerifyPhoneActivity, "Time out", Toast.LENGTH_SHORT).show()
            }
        }.start()
    }
}