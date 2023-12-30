package com.example.finalprojectbinar.view.fragments.bottomsheets

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.finalprojectbinar.databinding.FragmentBottomSheetCheckoutCourseBinding
import com.example.finalprojectbinar.view.ui.MainActivity
import com.example.finalprojectbinar.view.ui.PaymentActivity
import com.google.android.material.bottomsheet.BottomSheetDialogFragment


class BottomSheetCheckoutCourseFragment : BottomSheetDialogFragment() {
    private var _binding: FragmentBottomSheetCheckoutCourseBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentBottomSheetCheckoutCourseBinding.inflate(inflater, container, false)

        binding.imageClose.setOnClickListener {
            dismiss()
        }

        val prep = arguments?.getString(PaymentActivity.PREPARATION)

        binding.tvKembaliBeranda.setOnClickListener {
            val intent = Intent(requireContext(), MainActivity::class.java)
            startActivity(intent)
        }

        binding.buttonMulaiBelajar.setOnClickListener {
            val bottomSheetOnboardingFragment = BottomSheetOnboardingFragment.newInstance(prep.toString())
            bottomSheetOnboardingFragment.show(childFragmentManager, bottomSheetOnboardingFragment.tag)
        }


        return binding.root
    }

    companion object {
        fun newInstance(preparation: String): BottomSheetCheckoutCourseFragment {
            val fragment = BottomSheetCheckoutCourseFragment()
            val args = Bundle()
            args.putString(PaymentActivity.PREPARATION, preparation)
            fragment.arguments = args
            return fragment
        }
    }

}