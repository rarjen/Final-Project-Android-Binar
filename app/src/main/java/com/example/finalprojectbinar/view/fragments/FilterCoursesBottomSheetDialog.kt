package com.example.finalprojectbinar.view.fragments

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.finalprojectbinar.R
import com.example.finalprojectbinar.databinding.FilterCoursesBottomsheetBinding
import com.example.finalprojectbinar.view.adapters.FilterAdapter
import com.example.finalprojectbinar.view.model_dummy.ListFilter
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog

import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class FilterCoursesBottomSheetDialog : BottomSheetDialogFragment() {
    private lateinit var _binding: FilterCoursesBottomsheetBinding
    private val binding get() = _binding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FilterCoursesBottomsheetBinding.inflate(inflater, container, false)
        setupRecycleView()
        binding.btnCloseFilter.setOnClickListener {
            dismiss()
        }
        binding.btnFilter.setOnClickListener {
            findNavController().navigate(R.id.action_kursusFragment_to_hasilFilterFragment)
        }
        return binding.root
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        dialog?.setOnShowListener {it->
            val d = it as BottomSheetDialog
            val bottomSheet = d.findViewById<View>(com.google.android.material.R.id.design_bottom_sheet)
            bottomSheet?.let {
                val behavior = BottomSheetBehavior.from(it)
                behavior.state = BottomSheetBehavior.STATE_EXPANDED
            }
        }
        return super.onCreateDialog(savedInstanceState)
    }

    fun setupRecycleView(){
        val list = listOf<ListFilter>(
            ListFilter.HeaderItem("Header 1"),
            ListFilter.CheckboxItem("Item 1"),
            ListFilter.CheckboxItem("Item 2"),
            ListFilter.CheckboxItem("Item 3"),
            ListFilter.CheckboxItem("Item 4"),
            ListFilter.CheckboxItem("Item 5"),
            ListFilter.HeaderItem("Header 2"),
            ListFilter.CheckboxItem("Item 1"),
            ListFilter.CheckboxItem("Item 2"),
            ListFilter.CheckboxItem("Item 3"),
            ListFilter.HeaderItem("Header 3"),
            ListFilter.CheckboxItem("Item 1"),
            ListFilter.CheckboxItem("Item 2"),
            ListFilter.CheckboxItem("Item 3"),
            ListFilter.CheckboxItem("Item 4"),
            ListFilter.HeaderItem("Header 4"),
            ListFilter.CheckboxItem("Item 1"),
            ListFilter.CheckboxItem("Item 2"),
            ListFilter.CheckboxItem("Item 3"),
            ListFilter.CheckboxItem("Item 4"),
        )
        val adapter = FilterAdapter(list)
        binding.rvFilter.adapter = adapter
        binding.rvFilter.layoutManager = LinearLayoutManager(context)
    }

    companion object{
        const val TAG = "CoursesFilterBottomSheetDialog"
    }

}
