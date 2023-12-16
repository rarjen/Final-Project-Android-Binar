package com.example.finalprojectbinar.view.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.finalprojectbinar.R
import com.example.finalprojectbinar.databinding.FilterCoursesBottomsheetBinding
import com.example.finalprojectbinar.databinding.FragmentKursusBinding
import com.example.finalprojectbinar.model.CoursesResponses
import com.example.finalprojectbinar.model.DataAllCourses
import com.example.finalprojectbinar.model.DataFilter
import com.example.finalprojectbinar.util.Enum
import com.example.finalprojectbinar.util.SharedPreferenceHelper
import com.example.finalprojectbinar.util.Status
import com.example.finalprojectbinar.view.adapters.CourseAdapter
import com.example.finalprojectbinar.view.adapters.FilterAdapter
import com.example.finalprojectbinar.view.adapters.KursusAdapter
import com.example.finalprojectbinar.view.adapters.OnItemClickCallback
import com.example.finalprojectbinar.view.fragments.bottomsheets.BottomSheetConfirmOrderFragment
import com.example.finalprojectbinar.view.fragments.bottomsheets.BottomSheetMustLoginFragment
import com.example.finalprojectbinar.view.model_dummy.DataKelas
import com.example.finalprojectbinar.view.model_dummy.ListFilter
import com.example.finalprojectbinar.viewmodel.MyViewModel
import com.google.android.material.tabs.TabLayout
import org.koin.android.ext.android.inject
import kotlin.math.log

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [KursusFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class KursusFragment : Fragment(), DataListener {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private lateinit var _binding: FragmentKursusBinding
    private val binding get() = _binding
    private var DataFilter =  ArrayList<DataFilter>()
    private var status: String? = "3"
    private val sharedPrefKey = "statusKursus"
    private val viewModel: MyViewModel by inject()
    private var categoryId: ArrayList<String?> = ArrayList(6)
    private var Level: ArrayList<String?> = ArrayList(4)
    var category: String? = null
    var level: String? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentKursusBinding.inflate(inflater, container, false)
        SharedPreferenceHelper.init(requireContext().applicationContext)
        status = SharedPreferenceHelper.read(sharedPrefKey, status)
        Log.d("Status", "onCreateView: ${status}")
        showTab()
        Log.d("DATAAAAA", category + level)
        Log.d("Nama Fragment", "Fragment Kursus")
        binding.tvFilter.setOnClickListener {
            val modal = FilterCoursesBottomSheetDialog.newInstance(DataFilter)
            Log.d("DATA KIRIMAN", DataFilter.toString())
            modal.setDataListener(this)

            childFragmentManager.let { modal.show(it, FilterCoursesBottomSheetDialog.TAG) }
        }
        return binding.root
    }

    private fun fetchCourseCouroutines(categoryId: String?, level: String?, premium: String?, search: String?) {
        viewModel.getAllCourses(categoryId,level, premium, search).observe(viewLifecycleOwner) {
            when (it.status) {
                Status.SUCCESS -> {
                    setUPRecycleView(it.data!!)
                    Log.d("DATATEST", it.data.toString())
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

    private fun setUPRecycleView(data : CoursesResponses?) {
        val adapter = KursusAdapter(null)
        adapter.submitCoursesResponse(data?.data ?: emptyList())
        binding.rvKelas.layoutManager = LinearLayoutManager(requireActivity(), LinearLayoutManager.VERTICAL, false)
        binding.rvKelas.adapter = adapter
        adapter.setOnItemClickCallback(object : OnItemClickCallback{
            override fun onItemClicked(data: DataAllCourses) {
                val isLogin = SharedPreferenceHelper.read(Enum.PREF_NAME.value)
                if(isLogin != null){
                    if (data.isPremium){
                        val bottomSheetFragment = BottomSheetConfirmOrderFragment()
                        bottomSheetFragment.setCourseId(data.id)
                        bottomSheetFragment.show(childFragmentManager, bottomSheetFragment.tag)
                    }else{
                        val bundle = Bundle().apply {
                            putString("courseId", data.id)
                        }
                        findNavController().navigate(R.id.action_kursusFragment_to_detailKelasFragment, bundle)
                    }
                }else {
                    val bottomSheetFragmentMustLogin = BottomSheetMustLoginFragment()
                    bottomSheetFragmentMustLogin.show(childFragmentManager, bottomSheetFragmentMustLogin.tag)
                }

            }

        })

    }
    private fun showTab(){
        val allTab = binding.tabLayout.newTab()
        allTab.text = "All"
        binding.tabLayout.addTab(allTab)

        val premiumTab = binding.tabLayout.newTab()
        premiumTab.text = "Premium"
        binding.tabLayout.addTab(premiumTab)

        val freeTab = binding.tabLayout.newTab()
        freeTab.text = "Free"
        binding.tabLayout.addTab(freeTab)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        fetchCourseCouroutines(category,level,null,null)
        binding.tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener{
            override fun onTabSelected(tab: TabLayout.Tab) {
                if(tab.position == 0){
                    fetchCourseCouroutines(category,level,null,null)
                }else if (tab.position == 1){
                    fetchCourseCouroutines(category,level,"1",null)
                }else{
                    fetchCourseCouroutines(category,level,"0",null)
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {

            }

            override fun onTabReselected(tab: TabLayout.Tab?) {

            }

        })

    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            KursusFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
    override fun oDataReceived(dataFilter:ArrayList<DataFilter>) {
        var i = 0
        categoryId.clear()
        Level.clear()
       for (item in dataFilter){
           when(item){
               is DataFilter.Category ->{
                  categoryId.add(item.categoryId.toString())
               }
               is DataFilter.Level ->{
                   Level.add((item.level.toString()))
               }
           }
           i++
       }
        if (categoryId.isNotEmpty()) {
          category = categoryId.filterNotNull().joinToString(",")
        }
        if (Level.isNotEmpty()){
            level = Level.filterNotNull().joinToString(",")
        }
        Log.d("Data On Received", category+level)
        fetchCourseCouroutines(category,level,null,null)
        DataFilter = dataFilter
    }

    override fun onClearFilter() {
        categoryId.clear()
        Level.clear()
        category = null
        level = null
        fetchCourseCouroutines(null,null,null,null)
    }
}