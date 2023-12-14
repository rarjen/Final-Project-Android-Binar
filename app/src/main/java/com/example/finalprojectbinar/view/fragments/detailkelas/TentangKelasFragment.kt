package com.example.finalprojectbinar.view.fragments.detailkelas

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.finalprojectbinar.R
import com.example.finalprojectbinar.databinding.FragmentDetailKelasBinding
import com.example.finalprojectbinar.databinding.FragmentTentangKelasBinding
import com.example.finalprojectbinar.model.CoursesResponsebyName
import com.google.android.material.bottomnavigation.BottomNavigationView

class TentangKelasFragment(private val description: String?) : Fragment() {

    private var _binding: FragmentTentangKelasBinding? = null
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentTentangKelasBinding.inflate(inflater, container, false)

//        binding.textDescription.text = description

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}