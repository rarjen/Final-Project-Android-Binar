package com.example.finalprojectbinar.view.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import com.example.finalprojectbinar.R
import com.example.finalprojectbinar.databinding.FragmentBerandaBinding
import com.example.finalprojectbinar.databinding.FragmentKelasSayaBinding
import com.example.finalprojectbinar.model.DataCategories
import com.example.finalprojectbinar.model.ListCategoriesResponse
import com.example.finalprojectbinar.util.Status
import com.example.finalprojectbinar.view.adapters.CategoryAdapter
import com.example.finalprojectbinar.viewmodel.MyViewModel
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import org.koin.android.ext.android.inject

class KelasSayaFragment : Fragment() {

    private var _binding: FragmentKelasSayaBinding? = null
    private val binding get() = _binding!!

    private val viewModel: MyViewModel by inject()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentKelasSayaBinding.inflate(inflater, container, false)

        fetchCategoryCoroutines()
        setupLayoutLayout()

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

    private fun setupLayoutLayout() {
        val tabLayout = binding.tabLayout

        tabLayout.addTab(tabLayout.newTab().setText("All"))
        tabLayout.addTab(tabLayout.newTab().setText("Premium"))
        tabLayout.addTab(tabLayout.newTab().setText("Kelas Gratis"))

//        tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
//            override fun onTabSelected(tab: TabLayout.Tab?) {
//                tab?.let {
//                    when (it.position) {
//                        0 -> fetchCourseCouroutines(null) // All
//                        1 -> fetchCourseCouroutines("premium") // Premium
//                        2 -> fetchCourseCouroutines("free") // Kelas Gratis
//                    }
//                }
//            }
//
//            override fun onTabUnselected(tab: TabLayout.Tab?) {
//                // Not needed for this example
//            }
//
//            override fun onTabReselected(tab: TabLayout.Tab?) {
//                // Not needed for this example
//            }
//        })
    }

    private fun showCategories(data: ListCategoriesResponse?){
        val adapter = CategoryAdapter(null)

        adapter.submitCategoryMenuResponse(data?.data ?: emptyList())
        binding.gridviewKategori.layoutManager = GridLayoutManager(requireActivity(), 2)
        binding.gridviewKategori.adapter = adapter
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}