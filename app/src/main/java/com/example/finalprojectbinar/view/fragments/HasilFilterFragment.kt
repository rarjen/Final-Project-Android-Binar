package com.example.finalprojectbinar.view.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.finalprojectbinar.R
import com.example.finalprojectbinar.databinding.FragmentHasilFilterBinding
import com.example.finalprojectbinar.databinding.FragmentKursusBinding
import com.example.finalprojectbinar.model.CoursesResponses
import com.example.finalprojectbinar.model.DataFilter
import com.example.finalprojectbinar.util.Status
import com.example.finalprojectbinar.view.adapters.KursusAdapter
import com.example.finalprojectbinar.view.model_dummy.ListFilter
import com.example.finalprojectbinar.viewmodel.MyViewModel
import com.google.android.material.bottomnavigation.BottomNavigationView
import org.koin.android.ext.android.inject
import kotlin.math.log

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [HasilFilterFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class HasilFilterFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private lateinit var _binding: FragmentHasilFilterBinding
    private val binding get() = _binding
    private var bottomNavigation: BottomNavigationView? = null

    private val viewModel: MyViewModel by inject()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentHasilFilterBinding.inflate(inflater, container, false)
        bottomNavigation = activity?.findViewById<BottomNavigationView>(R.id.bottomNavigation)
        bottomNavigation?.visibility = View.GONE
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val bundle = arguments

    }

    private fun fetchCourseCouroutines(categoryId: String?, level: String?, premium: String?, search: String?) {
        viewModel.getAllCourses(categoryId,level, premium, search).observe(viewLifecycleOwner) {
            when (it.status) {
                Status.SUCCESS -> {
                    setUPRecycleView(it.data!!)
                    Log.d("DATATEST", it.data.toString())
                    binding.progressBarCourses.visibility = View.GONE
                }

                Status.ERROR -> {
                    Log.d("Error", "Error Occured!")
                }

                Status.LOADING -> {
                    binding.progressBarCourses.visibility = View.VISIBLE
                }
            }
        }
    }

    private fun setUPRecycleView(data : CoursesResponses?) {
        val adapter = KursusAdapter(null)
        adapter.submitCoursesResponse(data?.data ?: emptyList())
        binding.rvKelas.layoutManager = LinearLayoutManager(requireActivity(), LinearLayoutManager.VERTICAL, false)
        binding.rvKelas.adapter = adapter

    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment HasilFilterFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            HasilFilterFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        bottomNavigation?.visibility = View.VISIBLE

    }
}