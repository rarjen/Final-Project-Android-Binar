package com.example.finalprojectbinar.view.ui

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.example.finalprojectbinar.databinding.ActivityPaymentBinding
import com.example.finalprojectbinar.model.CoursesResponsebyName
import com.example.finalprojectbinar.model.DataCourses
import com.example.finalprojectbinar.model.PaymentRequest
import com.example.finalprojectbinar.util.Enum
import com.example.finalprojectbinar.util.SharedPreferenceHelper
import com.example.finalprojectbinar.util.Status
import com.example.finalprojectbinar.view.adapter.PaymentFragmentPageAdapter
import com.example.finalprojectbinar.view.fragments.bottomsheets.BottomSheetCheckoutCourseFragment
import com.example.finalprojectbinar.view.fragments.bottomsheets.BottomSheetConfirmOrderFragment
import com.example.finalprojectbinar.view.fragments.payment.BankFragment
import com.example.finalprojectbinar.view.fragments.payment.CreditCardFragment
import com.example.finalprojectbinar.viewmodel.MyViewModel
import com.google.android.material.tabs.TabLayoutMediator
import org.koin.android.ext.android.inject

@Suppress("DEPRECATION")
class PaymentActivity : AppCompatActivity() {
    private var _binding: ActivityPaymentBinding? = null
    private val binding get() = _binding!!

    private val viewModel: MyViewModel by inject()

    private var preparation: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityPaymentBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val fragmentList = arrayListOf(CreditCardFragment(), BankFragment())
        val courseId = intent.getStringExtra(BottomSheetConfirmOrderFragment.COURSE_ID)
        val paymentId = intent.getStringExtra("paymentId")
        val savedToken = SharedPreferenceHelper.read(Enum.PREF_NAME.value).toString()

        showDetailCoroutines(savedToken, courseId.toString())

        binding.apply {
            viewPager.adapter = PaymentFragmentPageAdapter(fragmentList, this@PaymentActivity.supportFragmentManager, lifecycle)

            TabLayoutMediator(tabView, viewPager) { tab, position ->
                when(position){
                   0 -> { tab.text = "Credit Card" }
                   1 -> { tab.text = "Bank Transfer" }
                }
            }.attach()

            buttonCheckout.setOnClickListener{
                performPayment(savedToken, paymentId.toString())
            }

            ivBack.setOnClickListener {
                val intent = Intent(this@PaymentActivity, MainActivity::class.java)
                startActivity(intent)
                finish()
            }
        }

        binding.viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                when (position) {
                    0 -> {
                        binding.buttonCheckout.visibility = View.VISIBLE
                    }
                    1 -> {
                        binding.buttonCheckout.visibility = View.GONE
                    }
                }
            }
        })
    }

    private fun performPayment(savedToken: String, paymentId: String) {
        showButtomSheetSuccessPayment()
        updatePaymentStatus(savedToken, paymentId)
    }

    @SuppressLint("InflateParams")
    private fun showButtomSheetSuccessPayment() {
        val bottomSheetCheckoutCourseFragment = BottomSheetCheckoutCourseFragment.newInstance(preparation!!)
        bottomSheetCheckoutCourseFragment.show(supportFragmentManager, bottomSheetCheckoutCourseFragment.tag)
    }

    private fun showDetailCoroutines(token: String?, courseId: String){
      viewModel.getDetailByIdCourse("Bearer $token", courseId).observe(this, Observer {
          when (it.status) {
              Status.SUCCESS -> {
                  Log.d("TESTPAYMENT", it.data.toString())
                  it.data?.let { data -> showData(data) }
                  preparation = it.data?.data?.onboarding.toString()
              }

              Status.ERROR -> {
                  Log.d("Error", "Error Occurred!")
              }

              Status.LOADING -> {
                  Log.d("TESTGETDATA", it.data.toString())
              }
          }
      })
    }

    @SuppressLint("SetTextI18n")
    private fun showData(data: CoursesResponsebyName) {
        val courseData: DataCourses? = data.data

        val price = courseData?.price
        val ppn = courseData?.price?.times(0.11)
        val total = ppn?.let { price?.plus(it) }

        binding.tvCardCategory.text = courseData?.category
        binding.tvCardTitleCourse.text = courseData?.name
        binding.tvCardAuthorCourse.text = courseData?.author
        binding.tvCoursePrice.text = "Rp $price"
        binding.tvTaxPrice.text = "Rp $ppn"
        binding.tvTotalPrice.text = "Rp $total"
        Glide.with(this@PaymentActivity)
            .load(courseData?.image)
            .fitCenter()
            .into(binding.imgCourseCover)
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

    companion object {
        const val PREPARATION = "preparation"
    }



}