package com.example.finalprojectbinar.view.fragments.bottomsheets

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.finalprojectbinar.databinding.FragmentBottomSheetNotificationNotExistBinding
import com.example.finalprojectbinar.view.ui.MainActivity
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class BottomSheetNotificationNotExistFragment : BottomSheetDialogFragment() {


    private var _binding: FragmentBottomSheetNotificationNotExistBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentBottomSheetNotificationNotExistBinding.inflate(inflater, container, false)

        binding.btnToMainActivity.setOnClickListener {
            val intent = Intent(requireContext(), MainActivity::class.java)
            startActivity(intent)
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        dialog?.setCanceledOnTouchOutside(false)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}