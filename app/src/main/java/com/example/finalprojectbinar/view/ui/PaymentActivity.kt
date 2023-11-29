package com.example.finalprojectbinar.view.ui

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import com.example.finalprojectbinar.R
import com.example.finalprojectbinar.databinding.ActivityPaymentBinding
import com.example.finalprojectbinar.view.adapter.PaymentFragmentPageAdapter
import com.example.finalprojectbinar.view.fragments.payment.BankFragment
import com.example.finalprojectbinar.view.fragments.payment.CreditCardFragment
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.tabs.TabLayoutMediator

class PaymentActivity : AppCompatActivity() {
    private var _binding: ActivityPaymentBinding? = null
    private val binding get() = _binding!!


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityPaymentBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val fragmentList = arrayListOf(CreditCardFragment(), BankFragment())


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


}