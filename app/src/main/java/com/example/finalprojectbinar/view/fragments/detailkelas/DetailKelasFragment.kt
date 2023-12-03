package com.example.finalprojectbinar.view.fragments.detailkelas

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.viewpager.widget.ViewPager
import androidx.viewpager2.widget.ViewPager2
import com.example.finalprojectbinar.R
import com.example.finalprojectbinar.databinding.FragmentDetailKelasBinding
import com.example.finalprojectbinar.databinding.FragmentKursusBinding
import com.example.finalprojectbinar.view.adapter.PaymentFragmentPageAdapter
import com.example.finalprojectbinar.view.fragments.payment.BankFragment
import com.example.finalprojectbinar.view.fragments.payment.CreditCardFragment
import com.example.finalprojectbinar.view.ui.MainActivity
import com.google.android.material.tabs.TabLayoutMediator

class DetailKelasFragment : Fragment() {

    private var _binding: FragmentDetailKelasBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentDetailKelasBinding.inflate(inflater, container, false)

        val fragmentList = arrayListOf(TentangKelasFragment(), BankFragment())
        val bottomNavigationView = (requireActivity() as MainActivity).getBottomNavigationView()

        binding.apply {
            viewPagerDetailClass.adapter = PaymentFragmentPageAdapter(fragmentList, requireActivity().supportFragmentManager, lifecycle)
            TabLayoutMediator(tabViewDetailClass, viewPagerDetailClass) { tab, position ->
                when(position) {
                    0 -> {tab.text = "Tentang"}
                    1 -> {tab.text = "Materi Kelas"}
                }
            }.attach()
        }

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




        return binding.root
    }
}