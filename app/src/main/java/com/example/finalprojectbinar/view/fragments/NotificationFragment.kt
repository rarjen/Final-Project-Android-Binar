package com.example.finalprojectbinar.view.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.finalprojectbinar.R
import com.example.finalprojectbinar.databinding.FragmentBerandaBinding
import com.example.finalprojectbinar.databinding.FragmentBottomSheetMustLoginBinding
import com.example.finalprojectbinar.databinding.FragmentNotificationBinding
import com.example.finalprojectbinar.util.Enum
import com.example.finalprojectbinar.util.SharedPreferenceHelper
import com.example.finalprojectbinar.view.fragments.bottomsheets.BottomSheetMustLoginFragment

class NotificationFragment : Fragment() {

    private var _binding: FragmentNotificationBinding? = null
    private val binding get() = _binding!!

//    override fun onStart() {
//        super.onStart()
//
//        val isLogin = SharedPreferenceHelper.read(Enum.PREF_NAME.value)
//        if (isLogin == null) {
//            val bottomSheetFragmentMustLogin = BottomSheetMustLoginFragment()
//            bottomSheetFragmentMustLogin.show(childFragmentManager, bottomSheetFragmentMustLogin.tag)
//        }
//
//    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentNotificationBinding.inflate(inflater, container, false)


        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}