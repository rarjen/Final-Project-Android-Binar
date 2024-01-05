package com.example.finalprojectbinar.view.fragments.bottomsheets

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.finalprojectbinar.databinding.FragmentBottomSheetSuccessRegisterBinding
import com.example.finalprojectbinar.util.Enum
import com.example.finalprojectbinar.util.SharedPreferenceHelper
import com.example.finalprojectbinar.view.ui.MainActivity
import com.example.finalprojectbinar.view.ui.login.LoginActivity
import com.example.finalprojectbinar.view.ui.register.VerifyPhoneActivity
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class BottomSheetSuccessRegisterFragment : BottomSheetDialogFragment()  {

    private var _binding: FragmentBottomSheetSuccessRegisterBinding? = null
    private val binding get() = _binding!!

    private lateinit var pref: SharedPreferenceHelper

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentBottomSheetSuccessRegisterBinding.inflate(inflater, container, false)

        binding.btnToLoginActivity.setOnClickListener {
            pref.write(Enum.PREF_REGISTER.value, null)
            dismiss()
            val intent = Intent(requireActivity(), MainActivity::class.java)
            startActivity(intent)
        }

        binding.imageClose.setOnClickListener {
            pref.write(Enum.PREF_REGISTER.value, null)
            dismiss()
            val intent = Intent(requireActivity(), MainActivity::class.java)
            startActivity(intent)
        }
        return binding.root
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}