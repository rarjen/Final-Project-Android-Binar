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
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.finalprojectbinar.R
import com.example.finalprojectbinar.databinding.FragmentMateriKelasBinding
import com.example.finalprojectbinar.model.CoursesResponsebyName
import com.example.finalprojectbinar.model.DataCourses
import com.example.finalprojectbinar.model.EnrollmentRequest
import com.example.finalprojectbinar.util.Enum
import com.example.finalprojectbinar.util.SharedPreferenceHelper
import com.example.finalprojectbinar.util.Status
import com.example.finalprojectbinar.view.fragments.bottomsheets.BottomSheetConfirmOrderFragment
import com.example.finalprojectbinar.view.fragments.bottomsheets.BottomSheetEnrollmentFree
import com.example.finalprojectbinar.view.ui.PaymentActivity
import com.example.finalprojectbinar.view.ui.VideoPlayerActivity
import com.example.finalprojectbinar.viewmodel.MyViewModel
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView
import org.koin.android.ext.android.inject
import org.koin.android.ext.koin.ERROR_MSG

class MateriKelasFragment : Fragment() {

    private var _binding: FragmentMateriKelasBinding? = null
    private val binding get() = _binding!!

    private val savedToken = SharedPreferenceHelper.read(Enum.PREF_NAME.value).toString()

    private var materiList: MutableList<Any> = mutableListOf()
    private lateinit var adapter: ViewTypeAdapterDetail

    private val viewModel: MyViewModel by inject()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {


        _binding = FragmentMateriKelasBinding.inflate(inflater,container,false)


        val id: String? = arguments?.getString(DetailKelasFragment.MATERI_KELAS)
        val savedToken = SharedPreferenceHelper.read(Enum.PREF_NAME.value)

        getModuleByCourseId(savedToken!!, id!!)

        return binding.root
    }


    private fun getModuleByCourseId(token: String, courseId: String){
        viewModel.getDetailByIdCourse("Bearer $token", courseId).observe(viewLifecycleOwner) {
            when (it.status) {
                Status.SUCCESS -> {
                    val data = it.data?.data
                    showData(data!!)
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
    private fun getVideoLink(token: String, chapterModuleUuid: String){
        viewModel.getVideoLink("Bearer $token", chapterModuleUuid).observe(viewLifecycleOwner){
            when (it.status) {
                Status.SUCCESS -> {
                    val data = it.data?.data
                    val link = data?.courseLink
                    val videoId = extractYouTubeVideoId(link.toString())
                    val intent = Intent(requireContext(), VideoPlayerActivity::class.java)
                    intent.putExtra(VIDEO_ID, videoId)
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


    private fun showData(data: DataCourses) {
        try {
            data.courseModules.forEach { classModule ->
                materiList.add(classModule)
                classModule.module.forEach { module ->
                    materiList.add(module)
                }
            }

            adapter = ViewTypeAdapterDetail(materiList, clickListener = { chapterModuleUuid, userChapterModuleUuid ->

                if (userChapterModuleUuid == null) {
                    val bottomSheetEnrollmentFree = BottomSheetEnrollmentFree()
                    bottomSheetEnrollmentFree.setCourseId(data.id)
                    bottomSheetEnrollmentFree.show(childFragmentManager, bottomSheetEnrollmentFree.tag)
                } else {
                    updateCompleted(savedToken, userChapterModuleUuid)
                    getVideoLink(savedToken, chapterModuleUuid)
                }

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

    private fun postEnrollment(token: String?, course_uuid: String){
        val enrollmentRequest = EnrollmentRequest(course_uuid)
        viewModel.postEnrollment("Bearer $token", enrollmentRequest).observe(this){
            when (it.status) {
                Status.SUCCESS -> {
                    Toast.makeText(requireContext(), "Success Enroll Course", Toast.LENGTH_SHORT).show()
                }
                Status.ERROR -> {
                    Toast.makeText(requireContext(), R.string.wrongMessage, Toast.LENGTH_SHORT).show()
                }
                Status.LOADING -> {
                    Log.d("LoadingTEST", "Loading")
                }
            }
        }
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

        const val VIDEO_ID = "videoId"
    }
}