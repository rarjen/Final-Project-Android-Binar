package com.example.finalprojectbinar.view.fragments.bottomsheets

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.finalprojectbinar.R
import com.example.finalprojectbinar.databinding.ActivityPaymentBinding
import com.example.finalprojectbinar.databinding.FragmentBottomSheetCheckoutCourseBinding
import com.example.finalprojectbinar.databinding.FragmentBottomSheetOnboardingBinding
import com.example.finalprojectbinar.view.fragments.detailkelas.DetailKelasFragment
import com.example.finalprojectbinar.view.fragments.detailkelas.TentangKelasFragment
import com.example.finalprojectbinar.view.ui.MainActivity
import com.example.finalprojectbinar.view.ui.PaymentActivity
import com.example.finalprojectbinar.viewmodel.MyViewModel
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import org.koin.android.ext.android.inject

class BottomSheetOnboardingFragment : BottomSheetDialogFragment() {

    private var _binding: FragmentBottomSheetOnboardingBinding? = null
    private val binding get() = _binding!!
    

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentBottomSheetOnboardingBinding.inflate(inflater, container, false)

        binding.imageClose.setOnClickListener {
            dismiss()
        }

        val preparation = arguments?.getString(PaymentActivity.PREPARATION)
        binding.tvRequirement.text = preparation.toString()

        binding.buttonMulaiBelajar.setOnClickListener {
            val intent = Intent(requireContext(), MainActivity::class.java)
            startActivity(intent)
        }


        return binding.root
    }

    companion object {
        fun newInstance(preparation: String): BottomSheetOnboardingFragment {
            val fragment = BottomSheetOnboardingFragment()
            val args = Bundle()
            args.putString(PaymentActivity.PREPARATION, preparation)
            fragment.arguments = args
            return fragment
        }
    }

}