package com.example.finalprojectbinar.view.fragments

import android.app.Dialog
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.finalprojectbinar.databinding.FilterCoursesBottomsheetBinding
import com.example.finalprojectbinar.model.DataFilter
import com.example.finalprojectbinar.model.ListCategoriesResponse
import com.example.finalprojectbinar.util.Status
import com.example.finalprojectbinar.view.adapters.FilterAdapter
import com.example.finalprojectbinar.view.model_dummy.ListFilter
import com.example.finalprojectbinar.viewmodel.MyViewModel
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog

import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import org.koin.android.ext.android.inject

class FilterCoursesBottomSheetDialog : BottomSheetDialogFragment() {
    private lateinit var _binding: FilterCoursesBottomsheetBinding
    private val binding get() = _binding
    private val viewModel: MyViewModel by inject()
    private val  mBundle = Bundle()
    private var dataListener: DataListener? = null
    private var filter = ArrayList<DataFilter>()
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FilterCoursesBottomsheetBinding.inflate(inflater, container, false)
        fetchCategoryCoroutines()
        binding.btnCloseFilter.setOnClickListener {
            dismiss()
        }
        binding.btnFilter.setOnClickListener {
            sendDataToFragment(filter)
            dismiss()
        }
        binding.tvHapusFilter.setOnClickListener {
            filter.clear()
            clearFilter()
            dismiss()

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
            list.add(ListFilter.CheckboxItem(id, category.name, null))
            id++
        }
        list.add(ListFilter.HeaderItem("Level"))
        list.add(ListFilter.CheckboxItem(null, "Beginner Level", "beginner"))
        list.add(ListFilter.CheckboxItem(null, "Intermediate Level","intermediate"))
        list.add(ListFilter.CheckboxItem(null, "Advanced Level", "advanced"))
        val dataList = arguments?.getParcelableArrayList<DataFilter>(ARG_DATA)
        filter = dataList!!
        val adapter = FilterAdapter(list, dataList)
        binding.rvFilter.adapter = adapter
        binding.rvFilter.layoutManager = LinearLayoutManager(context)
        adapter.setOnItemClickCallback(object : FilterAdapter.OnItemClickCallback{
            override fun onItemClicked(data: ListFilter.CheckboxItem, cbFilter: CheckBox) {
                if (cbFilter.isChecked){
                    if (data.id.toString().isNotEmpty() && data.id!!.toInt() <= 6) {
                        filter.add(DataFilter.Category(data.id.toString()))
                    }else{
                        filter.add(DataFilter.Level(data.tag.toString()))
                    }
                    Log.d("Ditambah", data.id.toString()+data.tag.toString())
                }else{
                    if (data.id.toString().isNotEmpty() && data.id!!.toInt() <= 6) {
                        filter.remove(DataFilter.Category(data.id.toString()))
                    }else{
                        filter.remove(DataFilter.Level(data.tag.toString()))
                    }
                    Log.d("Dihapus", data.id.toString()+data.tag.toString())
                }
            }
        })
        Log.d("Data Yang Mau Dikirim", filter.toString())
    }
    fun setDataListener(listener: DataListener){
        this.dataListener = listener
    }

    private fun sendDataToFragment(dataFilter: ArrayList<DataFilter>){
        dataListener?.oDataReceived(dataFilter)
    }
    private fun clearFilter(){
        dataListener?.onClearFilter()
    }

    companion object{
        const val TAG = "CoursesFilterBottomSheetDialog"
        const val ARG_DATA = "arg_data"
        fun newInstance(dataFilter: ArrayList<DataFilter>):FilterCoursesBottomSheetDialog{
            val fragment = FilterCoursesBottomSheetDialog()
            val args = Bundle()
            args.putParcelableArrayList(ARG_DATA, dataFilter)
            fragment.arguments = args
            return fragment
        }
    }

}

interface DataListener{
    fun oDataReceived(dataFilter: ArrayList<DataFilter>)
    fun onClearFilter()
}
