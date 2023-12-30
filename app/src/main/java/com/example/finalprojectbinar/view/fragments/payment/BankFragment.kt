package com.example.finalprojectbinar.view.fragments.payment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.finalprojectbinar.R
import com.example.finalprojectbinar.databinding.FragmentBankBinding
import com.example.finalprojectbinar.databinding.FragmentKelasSayaBinding
import com.example.finalprojectbinar.databinding.FragmentPaymentBinding

class BankFragment : Fragment() {

    private var _binding: FragmentBankBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentBankBinding.inflate(inflater, container, false)

        return binding.root
    }

}