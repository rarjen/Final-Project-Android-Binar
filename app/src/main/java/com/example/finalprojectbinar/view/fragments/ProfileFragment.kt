package com.example.finalprojectbinar.view.fragments

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.text.Editable
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.example.finalprojectbinar.R
import com.example.finalprojectbinar.databinding.FragmentProfileBinding
import com.example.finalprojectbinar.model.ProfileResponse
import com.example.finalprojectbinar.model.UpdateProfileRequest
import com.example.finalprojectbinar.util.Enum
import com.example.finalprojectbinar.util.SharedPreferenceHelper
import com.example.finalprojectbinar.util.Status
import com.example.finalprojectbinar.viewmodel.MyViewModel
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import org.koin.android.ext.android.inject

@Suppress("DEPRECATION", "NAME_SHADOWING")
class ProfileFragment : Fragment() {

    private var _binding : FragmentProfileBinding? = null
    private val binding get() = _binding!!

    private lateinit var pref: SharedPreferenceHelper

    private val viewModel: MyViewModel by inject()

    private val PICK_IMAGE_REQUEST = 1

    private var userUuid: String? = null

    private fun setUserUuid(userUuid: String){
        this.userUuid = userUuid
    }

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

        binding.editProfilePicture.setOnClickListener {
            val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            startActivityForResult(intent, PICK_IMAGE_REQUEST)
        }

        binding.emailField.isEnabled = false

        return binding.root
    }

    @Deprecated("Deprecated in Java", ReplaceWith(
        "super.onActivityResult(requestCode, resultCode, data)",
        "androidx.fragment.app.Fragment"
    )
    )
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == Activity.RESULT_OK && data != null && data.data != null){
            val selectedImageUri: Uri? = data.data
            val imageUrl = selectedImageUri.toString()

            val databaseReference = FirebaseDatabase.getInstance().getReference("users").child(userUuid!!)

            databaseReference.addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                   if (snapshot.exists() && snapshot.hasChild("profileImage")) {
                       updateProfileImage(imageUrl, userUuid!!)
                   } else {
                       uploadFirstProfileImage(imageUrl, userUuid!!)
                   }
                }
                override fun onCancelled(error: DatabaseError) {
                    Toast.makeText(requireContext(), "Error: $error", Toast.LENGTH_SHORT).show()
                }

            })

        }
    }

    private fun fetchUserProfile(token: String) {
        viewModel.getProfileUser("Bearer $token").observe(viewLifecycleOwner) {
            when (it.status) {
                Status.SUCCESS -> {
                    it.data?.let { data -> showProfiles(data) }
                    val userUuid = it.data?.data?.userUuid
                    userUuid?.let { userUuid ->
                        setUserUuid(userUuid)
                        getProfileImage(userUuid)
                    }
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

    private fun updateProfileImage(imageUrl: String, userUuid: String){
        val databaseReference = FirebaseDatabase.getInstance().getReference("users").child(userUuid)
        databaseReference.child("profileImageUrl").setValue(imageUrl)
            .addOnSuccessListener {
                Toast.makeText(requireContext(), "Foto profil berhasil diperbaharui", Toast.LENGTH_SHORT).show()
            }
            .addOnFailureListener { exception ->
                Toast.makeText(requireContext(), "Gagal memperbaharaui foto profil: $exception", Toast.LENGTH_SHORT).show()
            }
    }

    private fun uploadFirstProfileImage(imageUrl: String, userUuid: String){
        val databaseReference = FirebaseDatabase.getInstance().getReference("users").child(userUuid)
        databaseReference.child("profileImageUrl").setValue(imageUrl)
            .addOnSuccessListener {
                Toast.makeText(requireContext(), "Foto profil berhasil diunggah", Toast.LENGTH_SHORT).show()
            }
            .addOnFailureListener { exception ->
                Toast.makeText(requireContext(), "Gagal mengunggah foto profil: $exception", Toast.LENGTH_SHORT).show()
            }
    }

    private fun getProfileImage(userUuid: String){
        val userReference = FirebaseDatabase.getInstance().getReference("users").child(userUuid)

        userReference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()){
                    val imageUrl = snapshot.child("profileImageUrl").value.toString()
                    Glide.with(requireContext())
                        .load(imageUrl)
                        .into(binding.profileImage)
                } else {
                    binding.profileImage.setImageResource(R.drawable.profile_default)
                }

            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(requireContext(), "Error: $error", Toast.LENGTH_SHORT).show()
            }

        })
    }


    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}