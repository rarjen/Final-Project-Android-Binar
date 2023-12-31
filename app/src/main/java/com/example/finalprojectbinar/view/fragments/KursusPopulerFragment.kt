package com.example.finalprojectbinar.view.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.finalprojectbinar.databinding.FragmentKursusPopulerBinding

class KursusPopulerFragment : Fragment() {


    private lateinit var binding:FragmentKursusPopulerBinding
    private val tablTitles = arrayListOf("All","Data Science","UI/UX Design")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentKursusPopulerBinding.inflate(layoutInflater)


        return binding.root
    }
}