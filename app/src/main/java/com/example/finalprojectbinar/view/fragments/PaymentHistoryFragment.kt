package com.example.finalprojectbinar.view.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.finalprojectbinar.R
import com.example.finalprojectbinar.databinding.FragmentPaymentHistoryBinding
import com.example.finalprojectbinar.databinding.FragmentProfileBinding

class PaymentHistoryFragment : Fragment() {

    private var _binding : FragmentPaymentHistoryBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPaymentHistoryBinding.inflate(inflater, container, false)

        binding.ivBack.setOnClickListener {
            findNavController().navigate(R.id.action_paymentHistoryFragment_to_settingFragment)
        }

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}