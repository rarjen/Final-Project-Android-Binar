package com.example.finalprojectbinar.view.fragments.detailkelas

import ViewTypeAdapterDetail
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.finalprojectbinar.R
import com.example.finalprojectbinar.databinding.FragmentMateriKelasBinding
import com.example.finalprojectbinar.model.CoursesResponsebyName
import com.example.finalprojectbinar.model.DataCourses
import com.example.finalprojectbinar.util.Enum
import com.example.finalprojectbinar.util.SharedPreferenceHelper
import com.example.finalprojectbinar.util.Status
import com.example.finalprojectbinar.viewmodel.MyViewModel
import org.koin.android.ext.android.inject

class MateriKelasFragment : Fragment() {

    private var _binding: FragmentMateriKelasBinding? = null
    private val binding get() = _binding!!

    private var materiList: MutableList<Any> = mutableListOf()
    private lateinit var adapter: ViewTypeAdapterDetail

    private val viewModel: MyViewModel by inject()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {


        Log.d("DATASILABUS",viewModel.classModules.value.toString())
        binding.rvMateriChapter.layoutManager = LinearLayoutManager(requireContext())
        _binding = FragmentMateriKelasBinding.inflate(inflater,container,false)


        val id: String? = arguments?.getString(DetailKelasFragment.MATERI_KELAS)
        val savedToken = SharedPreferenceHelper.read(Enum.PREF_NAME.value)

        getModuleByCourseId(savedToken!!, id!!)

        return binding.root
    }

    private fun showMateriKelas(items: List<Any>) {
        val adapter = ViewTypeAdapterDetail(items, null)
        binding.rvMateriChapter.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        binding.rvMateriChapter.adapter = adapter
    }


    private fun getModuleByCourseId(token: String, courseId: String){
        viewModel.getDetailByIdCourse("Bearer $token", courseId).observe(viewLifecycleOwner) {
            when (it.status) {
                Status.SUCCESS -> {
                    val data = it.data?.data
                    showData(data!!)
                }

                Status.ERROR -> {
                    Toast.makeText(requireContext(), R.string.wrongMessage, Toast.LENGTH_SHORT).show()
//                    binding.progressBar.visibility = View.VISIBLE
                }

                Status.LOADING -> {
//                    binding.layoutVideoPlayer.visibility = View.GONE
//                    binding.cardDetail.visibility = View.GONE
//                    binding.nestedView.visibility = View.GONE
                }
            }
        }
    }

    private fun showData(data: DataCourses) {
        data.courseModules.forEach { classModule ->
            if (classModule != null) {
                materiList.add(classModule)
                classModule.module.forEach { module ->
                    materiList.add(module)
                }
            }
        }

        adapter = ViewTypeAdapterDetail(materiList, null)
        binding.rvMateriChapter.adapter = adapter
        binding.rvMateriChapter.layoutManager = LinearLayoutManager(
            requireActivity(),
            LinearLayoutManager.VERTICAL,
            false
        )



    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        fun newInstance(id: String): Fragment {
            val args = Bundle()
            args.putString(DetailKelasFragment.MATERI_KELAS, id)

            val fragment = MateriKelasFragment()
            fragment.arguments = args
            return fragment
        }
    }
}