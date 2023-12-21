package com.example.finalprojectbinar.view.fragments.detailkelas

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.os.bundleOf
import androidx.fragment.app.setFragmentResult
import com.example.finalprojectbinar.R
import com.example.finalprojectbinar.databinding.FragmentDetailKelasBinding
import com.example.finalprojectbinar.databinding.FragmentTentangKelasBinding
import com.example.finalprojectbinar.model.CoursesResponsebyName
import com.example.finalprojectbinar.viewmodel.MyViewModel
import com.google.android.material.bottomnavigation.BottomNavigationView
import org.koin.android.ext.android.inject

class TentangKelasFragment : Fragment() {

    private var _binding: FragmentTentangKelasBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTentangKelasBinding.inflate(inflater, container, false)


        val description = arguments?.getString(DetailKelasFragment.DETAIL_KELAS)
        binding.textDescription.text = description

        val classTarget = arguments?.getStringArrayList(DetailKelasFragment.KELAS_TARGET)
        showClassTarget(classTarget)

        return binding.root
    }

    @SuppressLint("SetTextI18n")
    private fun showClassTarget(classTarget: List<String>?){
        if (classTarget != null && classTarget.isNotEmpty()) {
            val formattedClassTarget = StringBuilder()

            for ((index, item) in classTarget.withIndex()) {
                formattedClassTarget.append("${index + 1}. $item\n")
            }

            binding.classTarget.text = formattedClassTarget.toString().trim()
        } else {
            // Handle the case when classTarget is null or empty
            binding.classTarget.text = "No data available"
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        fun newInstance(description: String, classTarget: List<String>): Fragment {
            val args = Bundle()
            args.putString(DetailKelasFragment.DETAIL_KELAS, description)
            args.putStringArrayList(DetailKelasFragment.KELAS_TARGET, ArrayList(classTarget))

            val fragment = TentangKelasFragment()
            fragment.arguments = args
            return fragment
        }
    }
}