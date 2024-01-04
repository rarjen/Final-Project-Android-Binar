package com.example.finalprojectbinar.view.fragments

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
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
import com.example.finalprojectbinar.util.Enum
import com.example.finalprojectbinar.util.SharedPreferenceHelper
import com.example.finalprojectbinar.util.Status
import com.example.finalprojectbinar.view.adapters.CategoryAdapter
import com.example.finalprojectbinar.view.adapters.CourseAdapter
import com.example.finalprojectbinar.view.fragments.bottomsheets.BottomSheetConfirmOrderFragment
import com.example.finalprojectbinar.view.fragments.bottomsheets.BottomSheetMustLoginFragment
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
    ): View {
        _binding = FragmentBerandaBinding.inflate(inflater, container, false)

        fetchCategoryCoroutines()

        search()

        binding.tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                if (tab.position == 0) {
                    fetchCourseCouroutines(null, null, null, null)
                } else {
                    val categoryId = categories[tab.position - 1].id
                    fetchCourseCouroutines(categoryId, null, null, null)
                }
            }

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

        val dataToken = SharedPreferenceHelper.read(Enum.PREF_NAME.value)

        if (dataToken != null) {
            Log.d("TESTSHARED", dataToken)
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

    private fun fetchCourseCouroutines(
        categoryId: String?,
        level: String?,
        premium: String?,
        search: String?
    ) {
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

    private fun showCategories(data: ListCategoriesResponse?) {
        val adapter = CategoryAdapter(null)

        categories = data?.data ?: emptyList()
        adapter.submitCategoryMenuResponse(data?.data ?: emptyList())

        val allTab = binding.tabLayout.newTab()
        allTab.text = "Semua"
        binding.tabLayout.addTab(allTab)

        binding.gridviewKategori.layoutManager = GridLayoutManager(requireActivity(), 2)
        binding.gridviewKategori.adapter = adapter
    }

    private fun showTabLayout(data: ListCategoriesResponse?) {
        val tabLayout = binding.tabLayout
        data?.data?.forEach { category ->
            val tab = tabLayout.newTab()
            tab.text = category.name
            tabLayout.addTab(tab)
        }
    }

    private fun showCourses(data: CoursesResponses?) {
        val adapter = CourseAdapter(null, onButtonClick = { courseId, isPremium ->
//            val isLogin = SharedPreferenceHelper.read(Enum.PREF_NAME.value)
//            if (isLogin != null) {
//                if (isPremium) {
//                    val bottomSheetFragment = BottomSheetConfirmOrderFragment()
//                    bottomSheetFragment.setCourseId(courseId)
//                    bottomSheetFragment.show(childFragmentManager, bottomSheetFragment.tag)
//                } else {
//                    val bundle = Bundle().apply {
//                        putString("courseId", courseId)
//                    }
//                    findNavController().navigate(
//                        R.id.action_berandaFragment_to_detailKelasFragment,
//                        bundle
//                    )
//                }
//                val bundle = Bundle().apply {
//                    putString("courseId", courseId)
//                }
//                findNavController().navigate(
//                    R.id.action_berandaFragment_to_detailKelasFragment,
//                    bundle
//                )
//            } else {
//                val bottomSheetFragmentMustLogin = BottomSheetMustLoginFragment()
//                bottomSheetFragmentMustLogin.show(
//                    childFragmentManager,
//                    bottomSheetFragmentMustLogin.tag
//                )
//            }

            val bundle = Bundle().apply {
                putString("courseId", courseId)
            }
            findNavController().navigate(
                R.id.action_berandaFragment_to_detailKelasFragment,
                bundle
            )

        })

        adapter.submitCoursesResponse(data?.data ?: emptyList())
        binding.rvCourses.layoutManager =
            LinearLayoutManager(requireActivity(), LinearLayoutManager.HORIZONTAL, false)
        binding.rvCourses.adapter = adapter
    }


    private fun search() {
        val searchEditText = binding.searchEditText

        searchEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (s.isNullOrEmpty()) {
                    binding.rvCourses.visibility = View.VISIBLE
                    binding.gridviewKategori.visibility = View.VISIBLE
                    binding.clKategori.visibility = View.VISIBLE
                    binding.clKursusPopuler.visibility = View.VISIBLE
                    binding.tabLayout.visibility = View.VISIBLE
                    binding.notFoundLayoutCourse.visibility = View.GONE
                    binding.containerSearchRV.visibility = View.GONE
                } else if (s.length >= 3) {
                    binding.rvCourses.visibility = View.GONE
                    binding.gridviewKategori.visibility = View.GONE
                    binding.clKategori.visibility = View.GONE
                    binding.clKursusPopuler.visibility = View.GONE
                    binding.tabLayout.visibility = View.GONE
                    fetchCourseSearch(null, null, null, s.toString())
                } else {
                    binding.rvCourses.visibility = View.VISIBLE
                    binding.gridviewKategori.visibility = View.VISIBLE
                    binding.clKategori.visibility = View.VISIBLE
                    binding.clKursusPopuler.visibility = View.VISIBLE
                    binding.tabLayout.visibility = View.VISIBLE
                    binding.notFoundLayoutCourse.visibility = View.GONE
                    binding.containerSearchRV.visibility = View.GONE
                }
            }

            override fun afterTextChanged(s: Editable?) {
            }
        })
    }

    private fun fetchCourseSearch(
        categoryId: String?,
        level: String?,
        premium: String?,
        search: String?
    ) {
        viewModel.getAllCourses(categoryId, level, premium, search).observe(viewLifecycleOwner) {
            when (it.status) {
                Status.SUCCESS -> {
                    val dataLength = it.data?.data?.size
                    if (dataLength!! < 1) {
                        binding.notFoundLayoutCourse.visibility = View.VISIBLE
                        binding.containerSearchRV.visibility = View.GONE
                    } else {
                        showCoursesSearch(it.data)
                        binding.containerSearchRV.visibility = View.VISIBLE
                        binding.notFoundLayoutCourse.visibility = View.GONE
                    }
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

    private fun showCoursesSearch(data: CoursesResponses?) {
        val adapter = CourseAdapter(null, onButtonClick = { courseId, isPremium ->
            val isLogin = SharedPreferenceHelper.read(Enum.PREF_NAME.value)
            if (isLogin != null) {
//                if (isPremium) {
//                    val bottomSheetFragment = BottomSheetConfirmOrderFragment()
//                    bottomSheetFragment.setCourseId(courseId)
//                    bottomSheetFragment.show(childFragmentManager, bottomSheetFragment.tag)
//                } else {
//                    val bundle = Bundle().apply {
//                        putString("courseId", courseId)
//                    }
//                    findNavController().navigate(
//                        R.id.action_berandaFragment_to_detailKelasFragment,
//                        bundle
//                    )
//                }

                val bundle = Bundle().apply {
                    putString("courseId", courseId)
                }
                findNavController().navigate(
                    R.id.action_berandaFragment_to_detailKelasFragment,
                    bundle
                )
            } else {
                val bottomSheetFragmentMustLogin = BottomSheetMustLoginFragment()
                bottomSheetFragmentMustLogin.show(
                    childFragmentManager,
                    bottomSheetFragmentMustLogin.tag
                )
            }
        })

        adapter.submitCoursesResponse(data?.data ?: emptyList())
        binding.containerSearchRV.layoutManager =
            LinearLayoutManager(requireActivity(), LinearLayoutManager.HORIZONTAL, false)
        binding.containerSearchRV.adapter = adapter
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
