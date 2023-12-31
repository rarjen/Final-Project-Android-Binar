package com.example.finalprojectbinar.view.fragments.bottomsheets

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.example.finalprojectbinar.R
import com.example.finalprojectbinar.databinding.FragmentBottomSheetEnrollmentFreeBinding
import com.example.finalprojectbinar.model.CoursesResponsebyName
import com.example.finalprojectbinar.model.DataCourses
import com.example.finalprojectbinar.model.EnrollmentRequest
import com.example.finalprojectbinar.model.PaymentRequest
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
    private val savedToken = SharedPreferenceHelper.read(Enum.PREF_NAME.value)

    fun setCourseId(courseId: String) {
        this.courseId = courseId
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentBottomSheetEnrollmentFreeBinding.inflate(inflater, container, false)


        binding.imageClose.setOnClickListener {
            dismiss()
        }

        showDetailCoroutines(savedToken, courseId.toString())

        binding.buttonBuy.setOnClickListener {
            postEnrollment(savedToken, courseId.toString())
        }

        return binding.root
    }

    private fun showDetailCoroutines(token: String?, courseId: String){
        viewModel.getDetailByIdCourse("Bearer $token", courseId).observe(viewLifecycleOwner) {
            when (it.status) {
                Status.SUCCESS -> {
                    it.data?.let { data -> showData(data) }
                }
                Status.ERROR -> {
                    Toast.makeText(requireContext(), R.string.wrongMessage, Toast.LENGTH_SHORT).show()
                    dismiss()
                }
                Status.LOADING -> {
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

    private fun postEnrollment(token: String?, course_uuid: String){
        val enrollmentRequest = EnrollmentRequest(course_uuid)
        viewModel.postEnrollment("Bearer $token", enrollmentRequest).observe(this){
            when (it.status) {
                Status.SUCCESS -> {
                    val paymentUuid = it.data?.data?.paymentUuid
                    updatePaymentStatus(savedToken, paymentUuid)
                    Toast.makeText(requireContext(), "Berhasil Enroll Course!", Toast.LENGTH_SHORT).show()
                    binding.progressBar.visibility = View.GONE
                    dismiss()
                    findNavController().navigate(R.id.nav)
                }
                Status.ERROR -> {
                    Toast.makeText(requireContext(), R.string.wrongMessage, Toast.LENGTH_SHORT).show()
                    binding.progressBar.visibility = View.VISIBLE
                }
                Status.LOADING -> {
                    binding.buttonBuy.isEnabled = false
                    binding.progressBar.visibility = View.VISIBLE
                }
            }
        }
    }

    private fun updatePaymentStatus(token: String?, paymentId: String?) {
        if (token != null && paymentId != null) {
            val paymentRequest = PaymentRequest(payment_method = "credit card")
            viewModel.putPayment("Bearer $token", paymentId, paymentRequest).observe(this) { resource ->
                when (resource.status) {
                    Status.SUCCESS -> {
                        Log.d("PaymentStatus", "Pembayaran berhasil diupdate")
                    }
                    Status.ERROR -> {
                        Log.e("PaymentStatus", "Gagal mengupdate pembayaran: ${resource.message}")
                    }
                    Status.LOADING -> {
                        Log.d("PaymentStatus", "Sedang memproses pembayaran...")
                    }
                }
            }
        } else {
            Log.e("PaymentStatus", "Token atau courseId null")
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}