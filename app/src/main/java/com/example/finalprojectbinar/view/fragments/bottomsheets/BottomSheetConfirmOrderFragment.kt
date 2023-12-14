package com.example.finalprojectbinar.view.fragments.bottomsheets

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.example.finalprojectbinar.databinding.FragmentBottomSheetConfirmOrderBinding
import com.example.finalprojectbinar.model.CoursesResponsebyName
import com.example.finalprojectbinar.model.DataCourses
import com.example.finalprojectbinar.util.Enum
import com.example.finalprojectbinar.util.SharedPreferenceHelper
import com.example.finalprojectbinar.util.Status
import com.example.finalprojectbinar.view.ui.PaymentActivity
import com.example.finalprojectbinar.viewmodel.MyViewModel
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import org.koin.android.ext.android.inject

class BottomSheetConfirmOrderFragment : BottomSheetDialogFragment() {
    private var _binding: FragmentBottomSheetConfirmOrderBinding? = null
    private val binding get() = _binding!!

    private var courseId: String? = null
    private val viewModel: MyViewModel by inject()

    fun setCourseId(courseId: String) {
        this.courseId = courseId
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentBottomSheetConfirmOrderBinding.inflate(inflater, container, false)
        val savedToken = SharedPreferenceHelper.read(Enum.PREF_NAME.value)

        binding.imageClose.setOnClickListener {
            dismiss()
        }

        binding.buttonBuy.setOnClickListener {
            val intent = Intent(requireContext(), PaymentActivity::class.java)
            intent.putExtra("courseId", courseId)
            startActivity(intent)
        }

        showDetailCoroutines(savedToken.toString(), courseId.toString())
        return binding.root
    }

    private fun showDetailCoroutines(token: String?, courseId: String){
        viewModel.getDetailByIdCourse("Bearer $token", courseId).observe(viewLifecycleOwner) {
            when (it.status) {
                Status.SUCCESS -> {
                    it.data?.let { data -> showData(data) }
                }
                Status.ERROR -> {
                    Log.d("ErrorTest", it.data?.code.toString())
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

        binding.tvCardCategory.text = courseData?.category
        binding.tvCardTitleCourse.text = courseData?.name
        binding.tvCardAuthorCourse.text = courseData?.author
        binding.tvCardRate.text = courseData?.rating.toString()
        binding.tvCardLevel.text = "${courseData?.level} Level"
        binding.tvCardTotalTime.text = "${courseData?.totalMinute} Menit"
        binding.tvCardTotalModul.text = "${courseData?.totalModule} Modul"
        Glide.with(this)
            .load(courseData?.image)
            .fitCenter()
            .into(binding.ivCardImage)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}