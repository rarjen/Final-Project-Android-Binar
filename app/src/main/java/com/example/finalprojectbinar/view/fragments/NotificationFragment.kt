package com.example.finalprojectbinar.view.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.finalprojectbinar.databinding.FragmentNotificationBinding
import com.example.finalprojectbinar.model.NotificationResponse
import com.example.finalprojectbinar.util.Enum
import com.example.finalprojectbinar.util.SharedPreferenceHelper
import com.example.finalprojectbinar.util.Status
import com.example.finalprojectbinar.view.adapters.NotificationAdapter
import com.example.finalprojectbinar.view.fragments.bottomsheets.BottomSheetNotificationNotExistFragment
import com.example.finalprojectbinar.view.model_dummy.Notif
import com.example.finalprojectbinar.viewmodel.MyViewModel
import org.koin.android.ext.android.inject

class NotificationFragment : Fragment() {

    private var _binding: FragmentNotificationBinding? = null
    private val binding get() = _binding!!
    private val viewModel: MyViewModel by inject()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentNotificationBinding.inflate(inflater, container, false)
        val isLogin = SharedPreferenceHelper.read(Enum.PREF_NAME.value)
        fetchNotificatioCouroutines(isLogin)
        return binding.root
    }

    private fun fetchNotificatioCouroutines(token: String?) {
        viewModel.getNotification("Bearer $token").observe(viewLifecycleOwner){
            when(it.status){
                Status.SUCCESS -> {

                    val length = it.data?.data?.size
                    if (length!! <= 0) {
                        val bottomSheetFragment = BottomSheetNotificationNotExistFragment()
                        bottomSheetFragment.show(childFragmentManager, bottomSheetFragment.tag)
                    } else {
                        setupRecycleView(it.data)
                    }

                }
                Status.ERROR -> {
                    Log.d("MESSEGE", it.message.toString())
                }
                Status.LOADING ->{

                }
            }
        }
    }

    private fun setupRecycleView(data: NotificationResponse?) {
        val rvNotif = binding.rvContainerNotifcation
        val adapter = NotificationAdapter()
        adapter.submitNotifResponse(data?.data ?: emptyList())
        rvNotif.adapter = adapter
        rvNotif.layoutManager = LinearLayoutManager(context)
    }
    private fun addDummyData():List<Notif>{
        val list = mutableListOf<Notif>()
        list.add(
            Notif("Judul Notif 1",
            "Subtitle Notif 1",
            "content Notif 1",
            "24 January 2024"))
        list.add(
            Notif("Judul Notif 2",
                "Subtitle Notif 2",
                "content Notif 12",
                "26 January 2025"))

        return list
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}