package com.example.finalprojectbinar.view.fragments.detailkelas

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.finalprojectbinar.R
import com.example.finalprojectbinar.databinding.FragmentMateriKelasBinding
import com.example.finalprojectbinar.viewmodel.MyViewModel
import org.koin.android.ext.android.inject


class MateriKelasFragment : Fragment() {
    
    private lateinit var binding: FragmentMateriKelasBinding
    private val viewModel: MyViewModel by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMateriKelasBinding.inflate(inflater,container,false)



        return binding.root
    }
}