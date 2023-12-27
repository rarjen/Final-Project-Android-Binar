package com.example.finalprojectbinar.view.fragments.bottomsheets

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.bumptech.glide.Glide
import com.example.finalprojectbinar.R
import com.example.finalprojectbinar.databinding.FragmentBottomSheetConfirmOrderBinding
import com.example.finalprojectbinar.databinding.FragmentBottomSheetEnrollmentFreeBinding
import com.example.finalprojectbinar.model.CoursesResponsebyName
import com.example.finalprojectbinar.model.DataCourses
import com.example.finalprojectbinar.util.Enum
import com.example.finalprojectbinar.util.SharedPreferenceHelper
import com.example.finalprojectbinar.util.Status
import com.example.finalprojectbinar.viewmodel.MyViewModel
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import org.koin.android.ext.android.inject

class BottomSheetEnrollmentFree : BottomSheetDialogFragment() {
    private var _binding: FragmentBottomSheetEnrollmentFreeBinding? = null
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
        // Inflate the layout for this fragment
        _binding = FragmentBottomSheetEnrollmentFreeBinding.inflate(inflater, container, false)
        val savedToken = SharedPreferenceHelper.read(Enum.PREF_NAME.value)

        binding.imageClose.setOnClickListener {
            dismiss()
        }

//        showDetailCoroutines(savedToken)

        return binding.root
    }

    private fun showDetailCoroutines(token: String?, courseId: String){
        viewModel.getDetailByIdCourse("Bearer $token", courseId).observe(viewLifecycleOwner) {
            when (it.status) {
                Status.SUCCESS -> {
                    it.data?.let { data -> showData(data) }
                    binding.buttonBuy.visibility = View.VISIBLE
                    binding.ivCardImage.visibility = View.VISIBLE
                    binding.layoutDetail.visibility = View.VISIBLE
                    binding.progressBar.visibility = View.GONE
                }
                Status.ERROR -> {
                    Toast.makeText(requireContext(), R.string.wrongMessage, Toast.LENGTH_SHORT).show()
                    binding.buttonBuy.visibility = View.GONE
                    binding.ivCardImage.visibility = View.GONE
                    binding.layoutDetail.visibility = View.GONE
                    binding.progressBar.visibility = View.VISIBLE
                    dismiss()
                }
                Status.LOADING -> {
                    binding.buttonBuy.visibility = View.GONE
                    binding.ivCardImage.visibility = View.GONE
                    binding.layoutDetail.visibility = View.GONE
                    binding.progressBar.visibility = View.VISIBLE
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
        binding.tvCardTotalTime.text = "${courseData?.totalMinute}"
        binding.tvCardTotalModul.text = "${courseData?.totalModule}"
        Glide.with(this)
            .load(courseData?.image)
            .fitCenter()
            .into(binding.ivCardImage)
    }
}