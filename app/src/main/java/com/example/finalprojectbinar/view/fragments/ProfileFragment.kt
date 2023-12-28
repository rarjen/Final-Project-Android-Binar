package com.example.finalprojectbinar.view.fragments

import android.os.Bundle
import android.text.Editable
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.example.finalprojectbinar.R
import com.example.finalprojectbinar.databinding.FragmentProfileBinding
import com.example.finalprojectbinar.model.ProfileResponse
import com.example.finalprojectbinar.model.UpdateProfileRequest
import com.example.finalprojectbinar.util.Enum
import com.example.finalprojectbinar.util.SharedPreferenceHelper
import com.example.finalprojectbinar.util.Status
import com.example.finalprojectbinar.viewmodel.MyViewModel
import org.koin.android.ext.android.inject

class ProfileFragment : Fragment() {

    private var _binding : FragmentProfileBinding? = null
    private val binding get() = _binding!!

    private lateinit var pref: SharedPreferenceHelper

    private val viewModel: MyViewModel by inject()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        activity?.window?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN)

        pref = SharedPreferenceHelper
        val savedToken = pref.read(Enum.PREF_NAME.value).toString()

        binding.ivBack.setOnClickListener {
            findNavController().navigate(R.id.action_profileFragment_to_settingFragment)
        }

        fetchUserProfile(savedToken)

        binding.updateProfileBtn.setOnClickListener {
            val nama = binding.nameField.editText?.text.toString()
            val phone = binding.mobileField.editText?.text.toString()
            val country = binding.countryField.editText?.text.toString()
            val city = binding.cityField.editText?.text.toString()

            updateProfile(savedToken, nama, phone, "", country, city)
        }

        binding.emailField.isEnabled = false

        return binding.root
    }

    private fun fetchUserProfile(token: String) {
        viewModel.getProfileUser("Bearer $token").observe(viewLifecycleOwner) {
            when (it.status) {
                Status.SUCCESS -> {
                    it.data?.let { data -> showProfiles(data) }
                    binding.progressBar.visibility = View.GONE
                }
                Status.ERROR -> {
                    Toast.makeText(requireContext(), R.string.wrongMessage, Toast.LENGTH_SHORT).show()
                    binding.progressBar.visibility = View.GONE
                }
                Status.LOADING -> {
                    binding.progressBar.visibility = View.VISIBLE
                }
            }
        }
    }

    private fun showProfiles(data: ProfileResponse){
        val name = data.data?.name
        val phoneNumber = data.data?.phone
        val city = data.data?.city ?: ""
        val country = data.data?.country ?: ""
        val email = data.data?.email

        val nameEditable = Editable.Factory.getInstance().newEditable(name)
        val phoneNumberEditable = Editable.Factory.getInstance().newEditable(phoneNumber)
        val cityEditable = Editable.Factory.getInstance().newEditable(city)
        val countryEditable = Editable.Factory.getInstance().newEditable(country)
        val emailEditable = Editable.Factory.getInstance().newEditable(email)

        binding.nameField.editText?.text = nameEditable
        binding.mobileField.editText?.text = phoneNumberEditable
        binding.emailField.editText?.text = emailEditable
        binding.cityField.editText?.text = cityEditable
        binding.countryField.editText?.text = countryEditable

    }

    private fun updateProfile(token: String, name: String?, phone: String?, address: String?, country: String?, city: String?) {
        val updateProfileRequest = UpdateProfileRequest(name, phone, address, country, city)
        viewModel.updateProfile("Bearer $token", updateProfileRequest).observe(viewLifecycleOwner){
            when (it.status) {
                Status.SUCCESS -> {
                    Toast.makeText(requireContext(), "Berhasil update profile", Toast.LENGTH_SHORT).show()
                }
                Status.ERROR -> {
                    Toast.makeText(requireContext(), R.string.wrongMessage, Toast.LENGTH_SHORT).show()
                }
                Status.LOADING -> {
                    Log.d("LoadingTEST", "Loading")
                }
            }
        }
    }


    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}