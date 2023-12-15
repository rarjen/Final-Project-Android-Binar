package com.example.finalprojectbinar.view.ui

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import com.example.finalprojectbinar.R
import com.example.finalprojectbinar.databinding.ActivityPaymentBinding
import com.example.finalprojectbinar.model.CoursesResponsebyName
import com.example.finalprojectbinar.model.DataCourses
import com.example.finalprojectbinar.util.Enum
import com.example.finalprojectbinar.util.SharedPreferenceHelper
import com.example.finalprojectbinar.util.Status
import com.example.finalprojectbinar.view.adapter.PaymentFragmentPageAdapter
import com.example.finalprojectbinar.view.fragments.payment.BankFragment
import com.example.finalprojectbinar.view.fragments.payment.CreditCardFragment
import com.example.finalprojectbinar.viewmodel.MyViewModel
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.tabs.TabLayoutMediator
import org.koin.android.ext.android.inject

class PaymentActivity : AppCompatActivity() {
    private var _binding: ActivityPaymentBinding? = null
    private val binding get() = _binding!!

    private val viewModel: MyViewModel by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityPaymentBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val fragmentList = arrayListOf(CreditCardFragment(), BankFragment())
        val courseId = intent.getStringExtra("courseId")
        val savedToken = SharedPreferenceHelper.read(Enum.PREF_NAME.value)


        showDetailCoroutines(savedToken.toString(), courseId.toString())


        binding.apply {
            viewPager.adapter = PaymentFragmentPageAdapter(fragmentList, this@PaymentActivity.supportFragmentManager, lifecycle)

            TabLayoutMediator(tabView, viewPager) { tab, position ->
                when(position){
                   0 -> { tab.text = "Credit Card" }
                   1 -> { tab.text = "Bank Transfer" }
                }
            }.attach()

            buttonCheckout.setOnClickListener{
                showButtomSheetSuccessPayment()
            }

            ivBack.setOnClickListener {
                val intent = Intent(this@PaymentActivity, MainActivity::class.java)
                startActivity(intent)
                finish()
            }

        }


    }

    @SuppressLint("InflateParams")
    private fun showButtomSheetSuccessPayment() {
        val dialog = BottomSheetDialog(this)
        val view = layoutInflater.inflate(R.layout.bottom_sheet_dialog_success_payment, null)
        val btnClose = view.findViewById<ImageView>(R.id.imageClose)

        val btnMulaiKelas = view.findViewById<Button>(R.id.buttonMulaiBelajar)
        btnMulaiKelas.setOnClickListener {
            dialog.dismiss()
            showButtomSheetOnboarding()
        }
        btnClose.setOnClickListener {
            dialog.dismiss()
        }
        dialog.setCancelable(false)
        dialog.setContentView(view)
        dialog.show()

    }

    @SuppressLint("InflateParams")
    private fun showButtomSheetOnboarding() {
        val dialog = BottomSheetDialog(this)
        val view = layoutInflater.inflate(R.layout.bottom_sheet_onboarding, null)
        val btnClose = view.findViewById<ImageView>(R.id.imageClose)
        btnClose.setOnClickListener {
            dialog.dismiss()
        }
        dialog.setCancelable(false)
        dialog.setContentView(view)
        dialog.show()
    }

    private fun showDetailCoroutines(token: String?, courseId: String){
      viewModel.getDetailByIdCourse("Bearer $token", courseId).observe(this, Observer {
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
      })
    }

    @SuppressLint("SetTextI18n")
    private fun showData(data: CoursesResponsebyName) {
        val courseData: DataCourses? = data.data
        // Update UI elements directly with course details
        binding.tvCardCategory.text = courseData?.category
        binding.tvCardTitleCourse.text = courseData?.name
        binding.tvCardAuthorCourse.text = courseData?.author
        binding.tvCoursePrice.text = "Rp ${courseData?.price}"
        binding.tvTaxPrice.text = "Rp ${courseData?.price?.times(0.11)}"
        binding.tvTotalPrice.text = "Rp ${courseData?.price?.plus(courseData.price?.times(0.11) ?: 0.0)}"
        Glide.with(this@PaymentActivity)
            .load(courseData?.image)
            .fitCenter()
            .into(binding.imgCourseCover)
    }



}