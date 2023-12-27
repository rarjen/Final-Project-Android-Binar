package com.example.finalprojectbinar.view.ui

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import com.example.finalprojectbinar.R
import com.example.finalprojectbinar.databinding.ActivityPaymentBinding
import com.example.finalprojectbinar.model.CoursesResponsebyName
import com.example.finalprojectbinar.model.DataCourses
import com.example.finalprojectbinar.model.DataEnrollment
import com.example.finalprojectbinar.model.EnrollmentRequest
import com.example.finalprojectbinar.model.PaymentRequest
import com.example.finalprojectbinar.util.Enum
import com.example.finalprojectbinar.util.SharedPreferenceHelper
import com.example.finalprojectbinar.util.Status
import com.example.finalprojectbinar.view.adapter.PaymentFragmentPageAdapter
import com.example.finalprojectbinar.view.fragments.bottomsheets.BottomSheetConfirmOrderFragment
import com.example.finalprojectbinar.view.fragments.payment.BankFragment
import com.example.finalprojectbinar.view.fragments.payment.CreditCardFragment
import com.example.finalprojectbinar.viewmodel.MyViewModel
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.tabs.TabLayoutMediator
import org.koin.android.ext.android.inject
import kotlin.math.log

@Suppress("DEPRECATION")
class PaymentActivity : AppCompatActivity() {
    private var _binding: ActivityPaymentBinding? = null
    private val binding get() = _binding!!

    private val viewModel: MyViewModel by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityPaymentBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val fragmentList = arrayListOf(CreditCardFragment(), BankFragment())
        val courseId = intent.getStringExtra(BottomSheetConfirmOrderFragment.COURSE_ID)
        val paymentId = intent.getStringExtra("paymentId")
        val savedToken = SharedPreferenceHelper.read(Enum.PREF_NAME.value)

        showDetailCoroutines(savedToken.toString(), courseId.toString())
//        showDetailPayment()

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
//                performPayment()
                updatePaymentStatus(savedToken, paymentId.toString())
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
            showButtonSheetOnboarding()
        }
        btnClose.setOnClickListener {
            dialog.dismiss()
        }
        dialog.setCancelable(false)
        dialog.setContentView(view)
        dialog.show()

    }

    @SuppressLint("InflateParams")
    private fun showButtonSheetOnboarding() {
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
                  Log.d("TESTPAYMENT", it.data.toString())
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
    private fun performPayment() {
        val courseId = intent.getStringExtra(BottomSheetConfirmOrderFragment.COURSE_ID)
        val savedToken = SharedPreferenceHelper.read(Enum.PREF_NAME.value)

        if (courseId != null) {
            val enrollmentRequest = EnrollmentRequest(courseId)
            viewModel.postEnrollment(savedToken, enrollmentRequest).observe(this, Observer {
                when (it.status) {
                    Status.SUCCESS -> {
                        // Handle success
                        Toast.makeText(this, "Pendaftaran berhasil", Toast.LENGTH_SHORT).show()

                        // Setelah pendaftaran berhasil, lakukan pembayaran
                        val paymentUuid = it.data?.data?.paymentUuid
                        if (!paymentUuid.isNullOrBlank()) {
                            val paymentRequest = PaymentRequest(payment_method = "credit card")
                            viewModel.putPayment(savedToken, paymentUuid, paymentRequest)
                                .observe(this, Observer { paymentResponse ->
                                    when (paymentResponse.status) {
                                        Status.SUCCESS -> {
                                            Toast.makeText(
                                                this@PaymentActivity,
                                                "Pembayaran berhasil",
                                                Toast.LENGTH_SHORT
                                            ).show()

                                            Log.d("PaymentStatus", "Pembayaran berhasil")
                                            updatePaymentStatus(savedToken, courseId)

                                            savePaymentMethod("credit_card")

                                            showButtomSheetSuccessPayment()
                                        }
                                        Status.ERROR -> {
                                            val errorMessage = paymentResponse.message
                                            Toast.makeText(this, errorMessage, Toast.LENGTH_SHORT).show()
                                        }
                                        Status.LOADING -> {
                                        }
                                    }
                                })
                        }
                    }
                    Status.ERROR -> {
                        // Handle error pendaftaran
                        val errorMessage = it.message
                        Toast.makeText(this, errorMessage, Toast.LENGTH_SHORT).show()
                    }
                    Status.LOADING -> {
                    }
                }
            })
        }
    }
    private fun updatePaymentStatus(token: String?, paymentId: String?) {
        if (token != null && paymentId != null) {
            // Ubah status pembayaran menjadi "paid" setelah pembayaran berhasil
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

    private fun savePaymentMethod(paymentMethod: String) {
        // Simpan metode pembayaran ke SharedPreference
        val sharedPreferenceKey = Enum.PAYMENT_METHOD.value
        SharedPreferenceHelper.write(sharedPreferenceKey, paymentMethod)
    }



}