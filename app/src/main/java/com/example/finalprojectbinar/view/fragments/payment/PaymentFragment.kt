package com.example.finalprojectbinar.view.fragments.payment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.finalprojectbinar.R
import com.example.finalprojectbinar.databinding.FragmentPaymentBinding
import com.example.finalprojectbinar.view.adapter.PaymentFragmentPageAdapter
import com.google.android.material.tabs.TabLayoutMediator

@Suppress("DEPRECATION")
class PaymentFragment : Fragment() {

    private var _binding: FragmentPaymentBinding? = null
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentPaymentBinding.inflate(inflater, container, false)

        val fragmentList = arrayListOf(BankFragment(), CreditCardFragment())

        binding.viewPager.adapter = PaymentFragmentPageAdapter(fragmentList, this@PaymentFragment.requireFragmentManager(), lifecycle)

        return binding.root
    }

}