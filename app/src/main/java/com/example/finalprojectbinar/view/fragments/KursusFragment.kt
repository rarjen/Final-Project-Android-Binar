package com.example.finalprojectbinar.view.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.finalprojectbinar.R
import com.example.finalprojectbinar.databinding.FragmentKursusBinding
import com.example.finalprojectbinar.view.adapters.KursusAdapter
import com.example.finalprojectbinar.view.model.DataKelas
import com.example.finalprojectbinar.view.model.SplashScreen
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [KursusFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class KursusFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private lateinit var _binding: FragmentKursusBinding
    private val binding get() = _binding
    private val list =  ArrayList<DataKelas>()


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
        _binding = FragmentKursusBinding.inflate(inflater, container, false)
        setUPRecycleView()

        return binding.root
    }

    private fun setUPRecycleView() {
        val rvCourses: RecyclerView = binding.rvKelas
        rvCourses.layoutManager = LinearLayoutManager(context)
        val kursusAdapter= KursusAdapter(list)
        rvCourses.adapter = kursusAdapter
        list.addAll(getAllCourses())
    }

    //Fungsi Untuk Mengambil data dummy dari string array
    private fun getAllCourses(): ArrayList<DataKelas> {
        val nama = resources.getStringArray(R.array.nama)
        val author = resources.getStringArray(R.array.author)
        val image = resources.getStringArray(R.array.image)
        val level = resources.getStringArray(R.array.level)
        val rating = resources.getStringArray(R.array.rating)
        val modul = resources.getStringArray(R.array.totalModul)
        val menit = resources.getStringArray(R.array.totalMenit)
        val premium = resources.getStringArray(R.array.premium)
        val category = resources.getStringArray(R.array.category)

        val listKelas = ArrayList<DataKelas>()
        for(i in nama.indices){
            var isPremium = true
            if(premium[i] == "1"){
                isPremium = true
            }else{
                isPremium = false
            }
            val data = DataKelas(nama[i], image[i], author[i], 1000,level[i],rating[i].toDouble(),modul[i].toInt(),menit[i].toInt(), isPremium, "",category[i],"")
            listKelas.add(data)
        }
        return listKelas
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.d("Nama Fragment", "Fragment Kursus")
    }


    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            KursusFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}