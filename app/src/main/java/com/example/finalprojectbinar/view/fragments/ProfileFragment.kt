package com.example.finalprojectbinar.view.fragments

import android.os.Bundle
import android.text.Editable
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.example.finalprojectbinar.R
import com.example.finalprojectbinar.databinding.FragmentProfileBinding
import com.example.finalprojectbinar.model.ProfileResponse
import com.example.finalprojectbinar.util.Enum
import com.example.finalprojectbinar.util.SharedPreferenceHelper
import com.example.finalprojectbinar.util.Status
import com.example.finalprojectbinar.viewmodel.MyViewModel
import org.koin.android.ext.android.inject

class ProfileFragment : Fragment() {

    private var _binding : FragmentProfileBinding? = null
    private val binding get() = _binding!!

    private val token = SharedPreferenceHelper.read(Enum.PREF_NAME.value).toString()

    private val viewModel: MyViewModel by inject()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)

        binding.ivBack.setOnClickListener {
            findNavController().navigate(R.id.action_profileFragment_to_settingFragment)
        }

        fetchUserProfile(token)

        return binding.root
    }

    private fun fetchUserProfile(token: String) {
        viewModel.getProfileUser("Bearer $token").observe(viewLifecycleOwner) {
            when (it.status) {
                Status.SUCCESS -> {
                    Log.d("TESTLOGIN", it.data.toString())
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
        val city = data.data?.city
        val country = data.data?.country
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
}