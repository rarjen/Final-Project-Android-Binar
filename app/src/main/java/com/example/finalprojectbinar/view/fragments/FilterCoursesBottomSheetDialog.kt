package com.example.finalprojectbinar.view.fragments

import android.app.Dialog
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.finalprojectbinar.R
import com.example.finalprojectbinar.databinding.FilterCoursesBottomsheetBinding
import com.example.finalprojectbinar.model.ListCategoriesResponse
import com.example.finalprojectbinar.util.Status
import com.example.finalprojectbinar.view.adapters.FilterAdapter
import com.example.finalprojectbinar.view.model_dummy.ListFilter
import com.example.finalprojectbinar.viewmodel.MyViewModel
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog

import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

class FilterCoursesBottomSheetDialog : BottomSheetDialogFragment() {
    private lateinit var _binding: FilterCoursesBottomsheetBinding
    private val binding get() = _binding
    private val viewModel: MyViewModel by inject()
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FilterCoursesBottomsheetBinding.inflate(inflater, container, false)
        fetchCategoryCoroutines()
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
    private fun fetchCategoryCoroutines() {
        viewModel.getCourseCategories().observe(viewLifecycleOwner) {
            when (it.status) {
                Status.SUCCESS -> {
                    setupRecycleView(it.data!!)
                    binding.progressBarCourses.visibility = View.GONE
                }

                Status.ERROR -> {
                    Log.d("Error", "Error Occured!")
                }

                Status.LOADING -> {
                    binding.progressBarCourses.visibility = View.VISIBLE
                }
            }
        }
    }
    fun setupRecycleView(data: ListCategoriesResponse?){
        var id = 1
        val list = mutableListOf<ListFilter>()

// Menambahkan item header
        list.add(ListFilter.HeaderItem("Categories"))
// Menambahkan item checkbox untuk setiap kategori dalam data
        data?.data?.forEach { category ->
            list.add(ListFilter.CheckboxItem(id, category.name))
            id++
        }
        list.add(ListFilter.HeaderItem("Level"))
        list.add(ListFilter.CheckboxItem(id, "Semua Level"))
        list.add(ListFilter.CheckboxItem(id, "Beginner Level"))
        list.add(ListFilter.CheckboxItem(id, "Intermediate Level"))
        list.add(ListFilter.CheckboxItem(id, "Advanced Level"))
        val adapter = FilterAdapter(list)
        binding.rvFilter.adapter = adapter
        binding.rvFilter.layoutManager = LinearLayoutManager(context)
    }

    companion object{
        const val TAG = "CoursesFilterBottomSheetDialog"
    }

}
