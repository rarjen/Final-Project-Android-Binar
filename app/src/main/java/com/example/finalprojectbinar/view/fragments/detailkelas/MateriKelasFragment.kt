package com.example.finalprojectbinar.view.fragments.detailkelas

import ViewTypeAdapterDetail
import android.content.Intent
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
import com.example.finalprojectbinar.model.DataCourses
import com.example.finalprojectbinar.util.Enum
import com.example.finalprojectbinar.util.SharedPreferenceHelper
import com.example.finalprojectbinar.util.Status
import com.example.finalprojectbinar.view.fragments.bottomsheets.BottomSheetConfirmOrderFragment
import com.example.finalprojectbinar.view.fragments.bottomsheets.BottomSheetEnrollmentFree
import com.example.finalprojectbinar.view.fragments.bottomsheets.BottomSheetLanjutkanPembayaranFragment
import com.example.finalprojectbinar.view.fragments.bottomsheets.BottomSheetMustLoginFragment
import com.example.finalprojectbinar.view.ui.VideoPlayerActivity
import com.example.finalprojectbinar.viewmodel.MyViewModel
import org.koin.android.ext.android.inject
import org.koin.android.ext.koin.ERROR_MSG

class MateriKelasFragment : Fragment() {

    private var _binding: FragmentMateriKelasBinding? = null
    private val binding get() = _binding!!

    private val savedToken = SharedPreferenceHelper.read(Enum.PREF_NAME.value).toString()

    private var materiList: MutableList<Any> = mutableListOf()
    private lateinit var adapter: ViewTypeAdapterDetail

    private var isPremium: Boolean? = null
    private var isPaid: Boolean? = null

    private val viewModel: MyViewModel by inject()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentMateriKelasBinding.inflate(inflater,container,false)


        val id: String? = arguments?.getString(DetailKelasFragment.MATERI_KELAS)
        val author = arguments?.getString(DetailKelasFragment.AUTHOR)
        val image = arguments?.getString(DetailKelasFragment.IMAGE)
        val savedToken = SharedPreferenceHelper.read(Enum.PREF_NAME.value).toString()

        getModuleByCourseId(savedToken, id!!, author!!, image!!)

        return binding.root
    }


    private fun getModuleByCourseId(token: String?, courseId: String, author: String, image: String){
        if (token != "null") {
            viewModel.getDetailByIdCourse("Bearer $token", courseId).observe(viewLifecycleOwner) {
                when (it.status) {
                    Status.SUCCESS -> {
                        val data = it.data?.data
                        this.isPremium = data?.isPremium;
                        this.isPaid = data?.isPaid;
                        Log.d("DATAMATERIKELAS", this.isPremium.toString())
                        Log.d("DATAMATERIKELAS", this.isPaid.toString())
                        showData(data!!, author, image)
                        binding.progressBar.visibility = View.GONE
                    }

                    Status.ERROR -> {
                        Toast.makeText(requireContext(), R.string.wrongMessage, Toast.LENGTH_SHORT).show()
                        binding.progressBar.visibility = View.VISIBLE
                    }

                    Status.LOADING -> {
                        binding.progressBar.visibility = View.VISIBLE
                    }
                }
            }
        } else {
            viewModel.getDetailByIdCourse(null, courseId).observe(viewLifecycleOwner) {
                when (it.status) {
                    Status.SUCCESS -> {
                        val data = it.data?.data
                        this.isPremium = data?.isPremium;
                        this.isPaid = data?.isPaid;
                        Log.d("DATAMATERIKELAS", this.isPremium.toString())
                        Log.d("DATAMATERIKELAS", this.isPaid.toString())
                        showData(data!!, author, image)
                        binding.progressBar.visibility = View.GONE
                    }

                    Status.ERROR -> {
                        Toast.makeText(requireContext(), R.string.wrongMessage, Toast.LENGTH_SHORT).show()
                        binding.progressBar.visibility = View.VISIBLE
                    }

                    Status.LOADING -> {
                        binding.progressBar.visibility = View.VISIBLE
                    }
                }
            }
        }
    }
    private fun getVideoLink(token: String, title: String, chapterModuleUuid: String, author: String, image: String){
        viewModel.getVideoLink("Bearer $token", chapterModuleUuid).observe(viewLifecycleOwner){
            when (it.status) {
                Status.SUCCESS -> {
                    val data = it.data?.data
                    val link = data?.courseLink
                    val videoId = extractYouTubeVideoId(link.toString())
                    val intent = Intent(requireContext(), VideoPlayerActivity::class.java)
                    intent.putExtra(VIDEO_ID, videoId)
                    intent.putExtra(TITLE, title)
                    intent.putExtra(AUTHOR, author)
                    intent.putExtra(IMAGE, image)
                    startActivity(intent)
                }

                Status.ERROR -> {
                }

                Status.LOADING -> {
                }
            }
        }
    }

    private fun extractYouTubeVideoId(youtubeUrl: String): String? {
        var vId: String? = null
        val pattern = Regex("^https?://.*(?:youtu.be/|v/|u/\\w/|embed/|watch?v=)([^#&?]*).*$", RegexOption.IGNORE_CASE)
        val matcher = pattern.find(youtubeUrl)
        if (matcher != null && matcher.groupValues.size > 1) {
            vId = matcher.groupValues[1]
        }
        return vId
    }


    private fun showData(data: DataCourses, author: String, image: String) {
        try {
            data.courseModules.forEach { classModule ->
                materiList.add(classModule)
                classModule.module.forEach { module ->
                    materiList.add(module)
                }
            }

            adapter = ViewTypeAdapterDetail(materiList, clickListener = { chapterModuleUuid, title, userChapterModuleUuid ->

                Log.d("TESTUSERUUID", userChapterModuleUuid.toString())

                val isLogin = SharedPreferenceHelper.read(Enum.PREF_NAME.value)

                if (isLogin == null) {
                    val bottomSheetFragmentMustLogin = BottomSheetMustLoginFragment()
                    bottomSheetFragmentMustLogin.show(childFragmentManager, bottomSheetFragmentMustLogin.tag)
                } else {
                    if (userChapterModuleUuid == null) {
                        if (data.isPremium) {
                            val bottomSheetEnrollmentPremium = BottomSheetConfirmOrderFragment()
                            bottomSheetEnrollmentPremium.setCourseId(data.id)
                            bottomSheetEnrollmentPremium.show(childFragmentManager, bottomSheetEnrollmentPremium.tag)
                        }
                        else {
                            val bottomSheetEnrollmentFree = BottomSheetEnrollmentFree()
                            bottomSheetEnrollmentFree.setCourseId(data.id)
                            bottomSheetEnrollmentFree.show(childFragmentManager, bottomSheetEnrollmentFree.tag)
                        }
                    } else {
                        if (isPremium == true && isPaid == false) {
                            val bottomSheetEnrollmentPremium = BottomSheetLanjutkanPembayaranFragment()
                            bottomSheetEnrollmentPremium.setCourseId(data.id)
                            bottomSheetEnrollmentPremium.show(childFragmentManager, bottomSheetEnrollmentPremium.tag)
                        } else {
                            updateCompleted(savedToken, userChapterModuleUuid)
                            getVideoLink(savedToken, title, chapterModuleUuid, author, image)
                        }

                    }

                }

//                if (userChapterModuleUuid == null) {
//                    if (data.isPremium) {
//                        val bottomSheetEnrollmentPremium = BottomSheetConfirmOrderFragment()
//                        bottomSheetEnrollmentPremium.setCourseId(data.id)
//                        bottomSheetEnrollmentPremium.show(childFragmentManager, bottomSheetEnrollmentPremium.tag)
//                    } else {
//                        val bottomSheetEnrollmentFree = BottomSheetEnrollmentFree()
//                        bottomSheetEnrollmentFree.setCourseId(data.id)
//                        bottomSheetEnrollmentFree.show(childFragmentManager, bottomSheetEnrollmentFree.tag)
//                    }
//                } else {
//                    updateCompleted(savedToken, userChapterModuleUuid)
//                    getVideoLink(savedToken, title, chapterModuleUuid, author, image)
//                }



            })
            binding.rvMateriChapter.adapter = adapter
            binding.rvMateriChapter.layoutManager = LinearLayoutManager(
                requireActivity(),
                LinearLayoutManager.VERTICAL,
                false
            )
        } catch (e: Exception) {
            Toast.makeText(requireContext(), R.string.wrongMessage, Toast.LENGTH_SHORT).show()
            Log.d("ERRORTEST", "msg: $e")
        }
    }

    private fun updateCompleted(token: String, userChapterModuleUuid: String){
        viewModel.updateCompletedModule("Bearer $token", userChapterModuleUuid).observe(viewLifecycleOwner){
            when (it.status) {
                Status.SUCCESS -> {
                    it.data?.message?.let { message -> Log.d("SUCCESS", "Success: $message") }
                    Log.d("TESTSUCCESS", it.data.toString())
                }

                Status.ERROR -> {
                    Log.d("ERROR", ERROR_MSG)
                    Log.d("TESTERROR", it.message.toString())
                    Toast.makeText(requireContext(), R.string.wrongMessage, Toast.LENGTH_SHORT).show()
                }

                Status.LOADING -> {
                    Log.d("LOADING", "Loading...")
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        fun newInstance(id: String, author: String, image: String): Fragment {
            val args = Bundle()
            args.putString(DetailKelasFragment.MATERI_KELAS, id)
            args.putString(DetailKelasFragment.AUTHOR, author)
            args.putString(DetailKelasFragment.IMAGE, image)

            val fragment = MateriKelasFragment()
            fragment.arguments = args
            return fragment
        }

        const val AUTHOR = "author"
        const val VIDEO_ID = "videoId"
        const val TITLE = "title"
        const val IMAGE = "image"
    }
}