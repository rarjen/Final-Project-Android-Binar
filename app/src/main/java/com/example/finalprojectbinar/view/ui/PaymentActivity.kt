package com.example.finalprojectbinar.view.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.finalprojectbinar.databinding.ActivityPaymentBinding
import com.example.finalprojectbinar.view.adapter.PaymentFragmentPageAdapter
import com.example.finalprojectbinar.view.fragments.payment.BankFragment
import com.example.finalprojectbinar.view.fragments.payment.CreditCardFragment
import com.google.android.material.tabs.TabLayoutMediator

class PaymentActivity : AppCompatActivity() {
    private var _binding: ActivityPaymentBinding? = null
    private val binding get() = _binding!!


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityPaymentBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val fragmentList = arrayListOf(CreditCardFragment(), BankFragment())


        binding.apply {
            viewPager.adapter = PaymentFragmentPageAdapter(fragmentList, this@PaymentActivity.supportFragmentManager, lifecycle)

            TabLayoutMediator(tabView, viewPager) { tab, position ->
                when(position){
                   0 -> { tab.text = "Credit Card"

                   }
                   1 -> { tab.text = "Bank Transfer"

                   }
                }

            }.attach()
        }

    }
}