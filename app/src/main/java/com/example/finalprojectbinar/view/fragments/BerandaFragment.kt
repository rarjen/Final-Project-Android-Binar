package com.example.finalprojectbinar.view.fragments

import android.content.Intent
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
import com.example.finalprojectbinar.databinding.FragmentBerandaBinding
import com.example.finalprojectbinar.model.CoursesResponses
import com.example.finalprojectbinar.model.DataCategories
import com.example.finalprojectbinar.model.ListCategoriesResponse
import com.example.finalprojectbinar.util.Status
import com.example.finalprojectbinar.view.adapters.CategoryAdapter
import com.example.finalprojectbinar.view.adapters.CourseAdapter
import com.example.finalprojectbinar.view.ui.PaymentActivity
import com.example.finalprojectbinar.viewmodel.MyViewModel
import com.google.android.material.tabs.TabLayout
import org.koin.android.ext.android.inject

class BerandaFragment : Fragment() {

    private var _binding: FragmentBerandaBinding? = null
    private val binding get() = _binding!!

    private var categories: List<DataCategories> = emptyList()

    private val viewModel: MyViewModel by inject()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentBerandaBinding.inflate(inflater, container, false)

        fetchCategoryCoroutines()


        binding.tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                if (tab.position == 0) {
//                     Panggil fungsi untuk menampilkan semua data
                    fetchCourseCouroutines(null, null, null, null)
                } else {
                    // Panggil fungsi filter dengan ID kategori yang sesuai
                    val categoryId = categories[tab.position - 1].id
                    fetchCourseCouroutines(categoryId, null, null, null)
                }
            }

            // Implementasikan fungsi onTabUnselected dan onTabReselected sesuai kebutuhan
            override fun onTabUnselected(tab: TabLayout.Tab) {
                // ...
            }

            override fun onTabReselected(tab: TabLayout.Tab) {
                // ...
            }
        })

        binding.lihatSemuaKursus.setOnClickListener {
            findNavController().navigate(R.id.action_berandaFragment_to_kursusFragment)
        }

        return binding.root
    }


    private fun fetchCategoryCoroutines() {
        viewModel.getCourseCategories().observe(viewLifecycleOwner) {
            when (it.status) {
                Status.SUCCESS -> {
                    showCategories(it.data!!)
                    showTabLayout(it.data)
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

    private fun fetchCourseCouroutines(categoryId: String?, level: String?, premium: String?, search: String?) {
        viewModel.getAllCourses(categoryId, level, premium, search).observe(viewLifecycleOwner) {
            when (it.status) {
                Status.SUCCESS -> {
                    showCourses(it.data!!)
                    binding.progressBarCourse.visibility = View.GONE
                }

                Status.ERROR -> {
                    Log.d("Error", "Error Occured!")
                }

                Status.LOADING -> {
                    binding.progressBarCourse.visibility = View.VISIBLE
                }
            }
        }
    }

    private fun showCategories(data: ListCategoriesResponse?){
        val adapter = CategoryAdapter(null)

        categories = data?.data ?: emptyList()
        adapter.submitCategoryMenuResponse(data?.data ?: emptyList())

        val allTab = binding.tabLayout.newTab()
        allTab.text = "All"
        binding.tabLayout.addTab(allTab)

        binding.gridviewKategori.layoutManager = GridLayoutManager(requireActivity(), 2)
        binding.gridviewKategori.adapter = adapter
    }

    private fun showTabLayout(data: ListCategoriesResponse?){
        val tabLayout = binding.tabLayout
        data?.data?.forEach { category ->
            val tab = tabLayout.newTab()
            tab.text = category.name
            tabLayout.addTab(tab)
        }
    }

    private fun showCourses(data: CoursesResponses?){
        val adapter = CourseAdapter(null, onButtonClick = { courseId, isPremium ->
            if (isPremium) {
                val intent = Intent(requireContext(), PaymentActivity::class.java)
                intent.putExtra("courseId", courseId)
                startActivity(intent)
            } else {
                val bundle = Bundle().apply {
                    putString("courseId", courseId)
                }
                findNavController().navigate(R.id.action_berandaFragment_to_detailKelasFragment, bundle)
            }
        })
        adapter.submitCoursesResponse(data?.data ?: emptyList())
        binding.rvCourses.layoutManager = LinearLayoutManager(requireActivity(), LinearLayoutManager.HORIZONTAL, false)
        binding.rvCourses.adapter = adapter
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}