package com.example.finalprojectbinar.view.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.finalprojectbinar.R
import com.example.finalprojectbinar.databinding.FragmentPaymentHistoryBinding
import com.example.finalprojectbinar.model.PaymentHistoryResponse
import com.example.finalprojectbinar.util.Enum
import com.example.finalprojectbinar.util.SharedPreferenceHelper
import com.example.finalprojectbinar.util.Status
import com.example.finalprojectbinar.view.adapters.HistoryPaymentAdapter
import com.example.finalprojectbinar.view.fragments.bottomsheets.BottomSheeetHistoryPaymentNotExistFragment
import com.example.finalprojectbinar.view.fragments.bottomsheets.BottomSheetNotificationNotExistFragment
import com.example.finalprojectbinar.view.ui.PaymentActivity
import com.example.finalprojectbinar.viewmodel.MyViewModel
import org.koin.android.ext.android.inject

class PaymentHistoryFragment : Fragment() {

    private var _binding : FragmentPaymentHistoryBinding? = null
    private val binding get() = _binding!!

    private val viewModel: MyViewModel by inject()

    private lateinit var pref: SharedPreferenceHelper

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPaymentHistoryBinding.inflate(inflater, container, false)

        pref = SharedPreferenceHelper

        val savedToken = pref.read(Enum.PREF_NAME.value).toString()

        binding.ivBack.setOnClickListener {
            findNavController().navigate(R.id.action_paymentHistoryFragment_to_settingFragment)
        }

        fetchHistoryPaymentCoroutines(savedToken)

        return binding.root
    }

    private fun fetchHistoryPaymentCoroutines(token: String) {
        viewModel.getHistoryPayment("Bearer $token").observe(viewLifecycleOwner){
            when (it.status) {
                Status.SUCCESS -> {
                    val length = it.data?.data?.size

                    if (length!! <= 0) {
                        val bottomSheetFragment = BottomSheeetHistoryPaymentNotExistFragment()
                        bottomSheetFragment.show(childFragmentManager, bottomSheetFragment.tag)
                    } else {
                        showHistory(it.data)
                        binding.progressBar.visibility = View.GONE
                    }


                }

                Status.ERROR -> {
                    binding.progressBar.visibility = View.VISIBLE
                    Toast.makeText(requireContext(), R.string.wrongMessage, Toast.LENGTH_SHORT).show()
                }

                Status.LOADING -> {
                    binding.progressBar.visibility = View.VISIBLE
                }
            }
        }
    }

    private fun showHistory(data: PaymentHistoryResponse?){
        val adapter = HistoryPaymentAdapter(onButtonClick = { courseId, paymentId, isPaid ->
            if (isPaid) {
               val bundle = Bundle().apply {
                   putString("courseId", courseId)
               }
                findNavController().navigate(R.id.action_paymentHistoryFragment_to_detailKelasFragment, bundle)
            } else {
                handlePayment(courseId, paymentId)
            }

        })

        adapter.submitHistoryPayment(data?.data ?: emptyList())
        binding.rvContainerPaymentHistory.layoutManager = LinearLayoutManager(requireActivity(), LinearLayoutManager.VERTICAL, false)
        binding.rvContainerPaymentHistory.adapter = adapter
    }
    private fun handlePayment(courseId: String, paymentId: String) {
        val intent = Intent(requireContext(), PaymentActivity::class.java).apply {
            putExtra("courseId", courseId)
            putExtra("paymentId", paymentId)
        }
        startActivity(intent)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}