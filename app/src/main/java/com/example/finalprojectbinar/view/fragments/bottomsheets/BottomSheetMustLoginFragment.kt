package com.example.finalprojectbinar.view.fragments.bottomsheets

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.finalprojectbinar.R
import com.example.finalprojectbinar.databinding.FragmentBottomSheetConfirmOrderBinding
import com.example.finalprojectbinar.databinding.FragmentBottomSheetMustLoginBinding
import com.example.finalprojectbinar.view.ui.login.LoginActivity
import com.example.finalprojectbinar.viewmodel.MyViewModel
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import org.koin.android.ext.android.inject

class BottomSheetMustLoginFragment : BottomSheetDialogFragment() {

    private var _binding: FragmentBottomSheetMustLoginBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentBottomSheetMustLoginBinding.inflate(inflater, container, false)

        binding.imageClose.setOnClickListener {
            dismiss()
        }

        binding.btnToLoginActivity.setOnClickListener {
            val intent = Intent(context, LoginActivity::class.java)
            startActivity(intent)
        }

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}