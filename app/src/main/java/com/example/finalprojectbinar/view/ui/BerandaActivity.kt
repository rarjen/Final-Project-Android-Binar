package com.example.finalprojectbinar.view.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.finalprojectbinar.R
import com.example.finalprojectbinar.databinding.ActivityBerandaBinding
import com.example.finalprojectbinar.view.adapters.TabLayoutKursusPopulerAdapter

class BerandaActivity : AppCompatActivity() {

    private lateinit var binding: ActivityBerandaBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBerandaBinding.inflate(layoutInflater)

        setContentView(binding.root)
    }

}