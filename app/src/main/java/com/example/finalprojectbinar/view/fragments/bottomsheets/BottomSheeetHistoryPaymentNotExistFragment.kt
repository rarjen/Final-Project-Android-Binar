package com.example.finalprojectbinar.view.fragments.bottomsheets

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.finalprojectbinar.databinding.FragmentBottomSheeetHistoryPaymentNotExistBinding
import com.example.finalprojectbinar.view.ui.MainActivity
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class BottomSheeetHistoryPaymentNotExistFragment : BottomSheetDialogFragment() {

    private var _binding: FragmentBottomSheeetHistoryPaymentNotExistBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentBottomSheeetHistoryPaymentNotExistBinding.inflate(inflater, container, false)

        binding.btnToMainActivity.setOnClickListener {
            val intent = Intent(requireContext(), MainActivity::class.java)
            startActivity(intent)
        }

        return binding.root

    }
}