package com.example.finalprojectbinar.view.fragments.payment

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.Toast
import com.example.finalprojectbinar.R
import com.example.finalprojectbinar.databinding.FragmentBankBinding
import com.example.finalprojectbinar.databinding.FragmentCreditCardBinding

class CreditCardFragment : Fragment() {

    private var _binding: FragmentCreditCardBinding? = null
    private val binding get() = _binding!!

        override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentCreditCardBinding.inflate(inflater, container, false)

//        binding.apply {
//
//            if (creditCardEt.text.isBlank() || cardHolderEt.text.isBlank() || cvvEt.text.isBlank() || expiryDateEt.text.isBlank()) {
//                Toast.makeText(requireContext(), "Field tidak boleh kosong", Toast.LENGTH_SHORT).show()
//            } else {
//                // Melakukan payment
//            }
//        }

        activity?.window?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN)

        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}