package com.example.finalprojectbinar.view.fragments.bottomsheets

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.bumptech.glide.Glide
import com.example.finalprojectbinar.R
import com.example.finalprojectbinar.databinding.FragmentBottomSheetConfirmOrderBinding
import com.example.finalprojectbinar.model.CoursesResponsebyName
import com.example.finalprojectbinar.model.DataCourses
import com.example.finalprojectbinar.model.EnrollmentRequest
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

    private var materiList: MutableList<Any> = mutableListOf()

    private var userChapterModuleUuid: String? = null

    fun setCourseId(courseId: String) {
        this.courseId = courseId
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentBottomSheetConfirmOrderBinding.inflate(inflater, container, false)
        val savedToken = SharedPreferenceHelper.read(Enum.PREF_NAME.value).toString()

        binding.imageClose.setOnClickListener {
            dismiss()
        }

        binding.buttonBuy.setOnClickListener {
            postEnrollment(savedToken, courseId.toString())
        }

        showDetailCoroutines(savedToken ,courseId.toString())
        return binding.root
    }

    private fun postEnrollment(token: String?, course_uuid: String){
        val enrollmentRequest = EnrollmentRequest(course_uuid)
        viewModel.postEnrollment("Bearer $token", enrollmentRequest).observe(this){
            when (it.status) {
                Status.SUCCESS -> {
                    Log.d("TESTPAYMENTUUID", it.data?.data?.paymentUuid.toString())
                    val paymentId = it.data?.data?.paymentUuid.toString()
                    val intent = Intent(requireContext(), PaymentActivity::class.java)
                    intent.putExtra("paymentId", paymentId)
                    intent.putExtra(COURSE_ID, courseId)
                    startActivity(intent)
                }
                Status.ERROR -> {
                    Toast.makeText(requireContext(), R.string.wrongMessage, Toast.LENGTH_SHORT).show()
                }
                Status.LOADING -> {
                    Log.d("LoadingTEST", "Loading")
                }
            }
        }
    }

    private fun showDetailCoroutines(token: String, courseId: String){
        viewModel.getDetailByIdCourse("Bearer $token", courseId).observe(viewLifecycleOwner) {
            when (it.status) {
                Status.SUCCESS -> {
                    it.data?.let { data -> showData(data) }
                    val classModule = it.data?.data?.courseModules

                    classModule!!.forEach { classModule ->
                        materiList.add(classModule)
                        classModule.module.forEach { module ->
                            userChapterModuleUuid = module.userChapterModuleUuid
                        }
                    }

                    Log.d("DATASUCESS", this.userChapterModuleUuid.toString())

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

    companion object {
        const val ENROLLMENT_DATA = "enrollmentData"
        const val COURSE_ID = "courseId"
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}