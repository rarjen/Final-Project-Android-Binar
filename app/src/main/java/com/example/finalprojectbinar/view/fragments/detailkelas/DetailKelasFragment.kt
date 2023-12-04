package com.example.finalprojectbinar.view.fragments.detailkelas

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.viewpager.widget.ViewPager
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.example.finalprojectbinar.R
import com.example.finalprojectbinar.databinding.FragmentDetailKelasBinding
import com.example.finalprojectbinar.databinding.FragmentKursusBinding
import com.example.finalprojectbinar.model.CoursesResponsebyName
import com.example.finalprojectbinar.model.DataCourses
import com.example.finalprojectbinar.util.Status
import com.example.finalprojectbinar.view.adapter.PaymentFragmentPageAdapter
import com.example.finalprojectbinar.view.fragments.payment.BankFragment
import com.example.finalprojectbinar.view.fragments.payment.CreditCardFragment
import com.example.finalprojectbinar.view.ui.MainActivity
import com.example.finalprojectbinar.viewmodel.MyViewModel
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.tabs.TabLayoutMediator
import org.koin.android.ext.android.inject

class DetailKelasFragment : Fragment() {

    private var _binding: FragmentDetailKelasBinding? = null
    private val binding get() = _binding!!

    private val viewModel: MyViewModel by inject()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentDetailKelasBinding.inflate(inflater, container, false)

        val fragmentList = arrayListOf(TentangKelasFragment(), BankFragment())
//        val bottomNavigationView = (requireActivity() as MainActivity).getBottomNavigationView()

        binding.apply {
            viewPagerDetailClass.adapter = PaymentFragmentPageAdapter(fragmentList, requireActivity().supportFragmentManager, lifecycle)
            TabLayoutMediator(tabViewDetailClass, viewPagerDetailClass) { tab, position ->
                when(position) {
                    0 -> {tab.text = "Tentang"}
                    1 -> {tab.text = "Materi Kelas"}
                }
            }.attach()
        }
//
//        binding.viewPagerDetailClass.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
//            override fun onPageSelected(position: Int) {
//                when (position) {
//                    0 -> {
//                        bottomNavigationView.visibility = View.GONE
//                    }
//                    1 -> {
//                        bottomNavigationView.visibility = View.VISIBLE
//                    }
//                    else -> {
//                        bottomNavigationView.visibility = View.VISIBLE
//                    }
//                }
//            }
//        })

        binding.ivBack.setOnClickListener {
            findNavController().navigate(R.id.action_detailKelasFragment_to_berandaFragment)
        }

        val courseId = arguments?.getString("courseId")

        showDetailCoroutines(courseId.toString())

        return binding.root
    }

    private fun showDetailCoroutines(courseId: String){
        viewModel.getDetailByIdCourse(courseId).observe(viewLifecycleOwner) {
            when (it.status) {
                Status.SUCCESS -> {
                    Log.d("TESTGETDATABYID", it.data.toString())
                    it.data?.let { data -> showData(data) }
                }

                Status.ERROR -> {
                    Log.d("Error", "Error Occurred!")
                }

                Status.LOADING -> {
                    Log.d("TESTGETDATA", it.data.toString())
                }
            }
        }
    }

    @SuppressLint("SetTextI18n")
    private fun showData(data: CoursesResponsebyName) {
        val courseData: DataCourses? = data.data

        binding.tvCategoryDetail.text = courseData?.category
        binding.tvTitleDetailCourse.text = courseData?.name
        binding.tvAuthorDetailCourse.text = courseData?.author
        binding.tvRatingDetail.text = courseData?.rating.toString()
        binding.tvLevelDetailCourse.text = "${courseData?.level} Level"
        binding.tvDetailTime.text = "${courseData?.totalMinute} Menit"
        binding.tvDetailModul.text = "${courseData?.totalModule} Modul"
        Glide.with(this)
            .load(courseData?.image)
            .fitCenter()
            .into(binding.coverImage)
    }

//    fun getViewPager(): ViewPager2 {
//        return binding.viewPagerDetailClass
//    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}