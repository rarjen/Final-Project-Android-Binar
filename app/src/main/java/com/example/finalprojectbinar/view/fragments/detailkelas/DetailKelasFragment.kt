package com.example.finalprojectbinar.view.fragments.detailkelas

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import androidx.viewpager.widget.ViewPager
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.example.finalprojectbinar.R
import com.example.finalprojectbinar.databinding.FragmentDetailKelasBinding
import com.example.finalprojectbinar.databinding.FragmentKursusBinding
import com.example.finalprojectbinar.model.CoursesResponsebyName
import com.example.finalprojectbinar.model.DataCourses
import com.example.finalprojectbinar.util.Enum
import com.example.finalprojectbinar.util.SharedPreferenceHelper
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

        val fragmentList = arrayListOf(TentangKelasFragment(null), BankFragment())
        val bottomNavigationView = (requireActivity() as MainActivity).getBottomNavigationView()

        val savedToken = SharedPreferenceHelper.read(Enum.PREF_NAME.value)

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
        binding.viewPagerDetailClass.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                when (position) {
                    0 -> {
                        bottomNavigationView.visibility = View.GONE
                    }
                    1 -> {
                        bottomNavigationView.visibility = View.VISIBLE
                    }
                }
            }
        })

        binding.ivBack.setOnClickListener {
            findNavController().navigate(R.id.action_detailKelasFragment_to_berandaFragment)
        }

        val courseId = arguments?.getString("courseId")

        showDetailCoroutines(savedToken.toString(), courseId.toString())

        return binding.root
    }

    private fun showDetailCoroutines(token: String?, courseId: String){
        viewModel.getDetailByIdCourse("Bearer $token", courseId).observe(viewLifecycleOwner) {
            when (it.status) {
                Status.SUCCESS -> {
                    it.data?.let { data ->
                        showData(data)
                    }
                }

                Status.ERROR -> {
                    Toast.makeText(requireContext(), R.string.wrongMessage, Toast.LENGTH_SHORT).show()
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


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        val bottomNavigationView = (requireActivity() as MainActivity).getBottomNavigationView()
        bottomNavigationView.visibility = View.VISIBLE
    }


}