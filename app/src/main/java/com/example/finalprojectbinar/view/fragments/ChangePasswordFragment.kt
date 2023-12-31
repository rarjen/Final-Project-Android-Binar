package com.example.finalprojectbinar.view.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.example.finalprojectbinar.R
import com.example.finalprojectbinar.databinding.FragmentChangePasswordBinding
import com.example.finalprojectbinar.model.UpdatePasswordRequest
import com.example.finalprojectbinar.util.Enum
import com.example.finalprojectbinar.util.SharedPreferenceHelper
import com.example.finalprojectbinar.util.Status
import com.example.finalprojectbinar.viewmodel.MyViewModel
import org.koin.android.ext.android.inject

class ChangePasswordFragment : Fragment() {

    private var _binding : FragmentChangePasswordBinding? = null
    private val binding get() = _binding!!

    private lateinit var pref: SharedPreferenceHelper

    private val viewModel: MyViewModel by inject()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentChangePasswordBinding.inflate(inflater, container, false)

        pref = SharedPreferenceHelper
        val savedToken = pref.read(Enum.PREF_NAME.value).toString()

        binding.ivBack.setOnClickListener {
            findNavController().navigate(R.id.action_changePasswordFragment_to_settingFragment)
        }

        binding.btnUpdatePassword.setOnClickListener {
            val oldPassword = binding.passwordLama.editText?.text.toString()
            val newPassword = binding.passwordBaru.editText?.text.toString()
            val repeatPassword = binding.repeatPassword.editText?.text.toString()
            updatePassword(savedToken, oldPassword, newPassword, repeatPassword)
        }



        return binding.root
    }

    private fun updatePassword(token: String, oldPassword: String?, newPasword: String?, confirmPassword: String?){

        if (newPasword != confirmPassword) {
            Toast.makeText(requireContext(), "Password tidak sama!", Toast.LENGTH_SHORT).show()
        } else if (oldPassword == "" ||  newPasword == "" || confirmPassword == "") {
            Toast.makeText(requireContext(), "Field tidak boleh kososng!", Toast.LENGTH_SHORT).show()
        } else {
            val updatePasswordRequest = UpdatePasswordRequest(oldPassword, newPasword)
            viewModel.updatePassword("Bearer $token", updatePasswordRequest).observe(viewLifecycleOwner){
                when (it.status) {
                    Status.SUCCESS -> {
                       Toast.makeText(requireContext(), "Sukses ubah password!", Toast.LENGTH_SHORT).show()
                        findNavController().navigate(R.id.action_changePasswordFragment_to_settingFragment)
                    }
                    Status.ERROR -> {
                        Toast.makeText(requireContext(), "Password lama tidak sama!", Toast.LENGTH_SHORT).show()
                    }
                    Status.LOADING -> {
                        Log.d("Loading", "Loading...")

                        binding.btnUpdatePassword.isEnabled = false
                        binding.btnUpdatePassword.alpha = 0.1f

                    }
                }
            }
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}