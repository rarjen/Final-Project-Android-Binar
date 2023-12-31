package com.example.finalprojectbinar.view.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.finalprojectbinar.R
import com.example.finalprojectbinar.databinding.FragmentKelasSayaBinding
import com.example.finalprojectbinar.model.ListCategoriesResponse
import com.example.finalprojectbinar.model.MyClassResponse
import com.example.finalprojectbinar.util.Enum
import com.example.finalprojectbinar.util.SharedPreferenceHelper
import com.example.finalprojectbinar.util.Status
import com.example.finalprojectbinar.view.adapters.CategoryAdapter
import com.example.finalprojectbinar.view.adapters.MyClassAdapter
import com.example.finalprojectbinar.view.fragments.bottomsheets.BottomSheetMyClassNotExistFragment
import com.example.finalprojectbinar.viewmodel.MyViewModel
import com.google.android.material.tabs.TabLayout
import org.koin.android.ext.android.inject

class KelasSayaFragment : Fragment() {

    private var _binding: FragmentKelasSayaBinding? = null
    private val binding get() = _binding!!

    private lateinit var pref: SharedPreferenceHelper

    private val viewModel: MyViewModel by inject()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentKelasSayaBinding.inflate(inflater, container, false)
        pref = SharedPreferenceHelper
        val savedToken = pref.read(Enum.PREF_NAME.value).toString()
        fetchCategoryCoroutines()
        setupLayoutLayout(savedToken)
        fetchMyClassCoroutines(savedToken, null)

        return binding.root
    }

    private fun fetchCategoryCoroutines() {
        viewModel.getCourseCategories().observe(viewLifecycleOwner) {
            when (it.status) {
                Status.SUCCESS -> {
                    showCategories(it.data!!)
                    binding.progressBarCategory.visibility = View.GONE
                }
                Status.ERROR -> {
                    Log.d("Error", "Error Occured!")
                }
                Status.LOADING -> {
                    binding.progressBarCategory.visibility = View.VISIBLE
                }
            }
        }
    }

    private fun showCategories(data: ListCategoriesResponse?){
        val adapter = CategoryAdapter(null)

        adapter.submitCategoryMenuResponse(data?.data ?: emptyList())
        binding.gridviewKategori.layoutManager = GridLayoutManager(requireActivity(), 2)
        binding.gridviewKategori.adapter = adapter
    }

    private fun setupLayoutLayout(token: String) {
        val tabLayout = binding.tabLayout

        tabLayout.addTab(tabLayout.newTab().setText("Semua"))
        tabLayout.addTab(tabLayout.newTab().setText("Sedang Dipelajari"))
        tabLayout.addTab(tabLayout.newTab().setText("Selesai"))

        tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                tab?.let {
                    when (it.position) {
                        0 -> fetchMyClassCoroutines(token, null)
                        1 -> fetchMyClassCoroutines(token, "0")
                        2 -> fetchMyClassCoroutines(token, "1")
                    }
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
                // Not needed for this example
            }

            override fun onTabReselected(tab: TabLayout.Tab?) {
                // Not needed for this example
            }
        })
    }


    private fun fetchMyClassCoroutines(token: String, isCompleted: String?){
        viewModel.getMyClass("Bearer $token", isCompleted).observe(viewLifecycleOwner){
            when (it.status) {
                Status.SUCCESS -> {
                    binding.progressBarClass.visibility = View.GONE
                    val length = it.data?.data?.size
                    if (length!! <= 0) {
                        val bottomSheetFragment = BottomSheetMyClassNotExistFragment()
                        bottomSheetFragment.show(childFragmentManager, bottomSheetFragment.tag)
                    } else {
                        showClass(it.data)
                    }
                }

                Status.ERROR -> {
                    Log.d("Error", "Error Occured!")
                }

                Status.LOADING -> {
                    binding.progressBarClass.visibility = View.VISIBLE
                }
            }
        }
    }

    private fun showClass(data: MyClassResponse?){
        val adapter = MyClassAdapter(onButtonClick = { courseId ->
            val bundle = Bundle().apply {
                putString("courseId", courseId)
            }
            findNavController().navigate(
                R.id.action_kelasSayaFragment_to_detailKelasFragment,
                bundle
            )
        })

        adapter.submitMyClass(data?.data ?: emptyList())
        binding.rvCourses.layoutManager = LinearLayoutManager(requireActivity(), LinearLayoutManager.HORIZONTAL, false)
        binding.rvCourses.adapter = adapter
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}