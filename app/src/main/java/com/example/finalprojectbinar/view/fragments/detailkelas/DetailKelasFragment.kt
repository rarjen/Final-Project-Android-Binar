package com.example.finalprojectbinar.view.fragments.detailkelas

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.viewpager2.widget.ViewPager2
import com.example.finalprojectbinar.R
import com.example.finalprojectbinar.databinding.FragmentDetailKelasBinding
import com.example.finalprojectbinar.model.CoursesResponsebyName
import com.example.finalprojectbinar.model.DataCourses
import com.example.finalprojectbinar.util.Enum
import com.example.finalprojectbinar.util.SharedPreferenceHelper
import com.example.finalprojectbinar.util.Status
import com.example.finalprojectbinar.view.adapter.PaymentFragmentPageAdapter
import com.example.finalprojectbinar.view.ui.MainActivity
import com.example.finalprojectbinar.viewmodel.MyViewModel
import com.google.android.material.tabs.TabLayoutMediator
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView
import org.koin.android.ext.android.inject
import kotlin.math.log


class DetailKelasFragment : Fragment() {

    private var _binding: FragmentDetailKelasBinding? = null
    private val binding get() = _binding!!

    private val viewModel: MyViewModel by inject()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentDetailKelasBinding.inflate(inflater, container, false)

        val savedToken = SharedPreferenceHelper.read(Enum.PREF_NAME.value).toString();
        val courseId = arguments?.getString("courseId")

        binding.ivBack.setOnClickListener {
            findNavController().navigate(R.id.action_detailKelasFragment_to_berandaFragment)
        }

        showDetailCoroutines(savedToken, courseId.toString())

        return binding.root
    }

    private fun showDetailCoroutines(token: String, courseId: String){
        if (token != "null") {
//            Log.d("INIEKSESKUSI", "1")
            viewModel.getDetailByIdCourse("Bearer $token", courseId).observe(viewLifecycleOwner) {
                when (it.status) {
                    Status.SUCCESS -> {
                        it.data?.let { data ->

                            Log.d("DATAVIDEO", data.data!!.introVideo.toString())
                            showData(data)
                            binding.progressBar.visibility = View.GONE
                            binding.layoutVideoPlayer.visibility = View.VISIBLE
                            binding.cardDetail.visibility = View.VISIBLE
                            binding.nestedView.visibility = View.VISIBLE
                        }
                    }

                    Status.ERROR -> {
                        Toast.makeText(requireContext(), R.string.wrongMessage, Toast.LENGTH_SHORT).show()
                        binding.progressBar.visibility = View.VISIBLE
                    }

                    Status.LOADING -> {
                        binding.layoutVideoPlayer.visibility = View.GONE
                        binding.cardDetail.visibility = View.GONE
                        binding.nestedView.visibility = View.GONE
                    }
                }
            }
        } else {
//            Log.d("INIEKSESKUSI", "2")
            viewModel.getDetailByIdCourse(null, courseId).observe(viewLifecycleOwner) {
                when (it.status) {
                    Status.SUCCESS -> {
                        it.data?.let { data ->

                            Log.d("DATAVIDEO", data.data!!.introVideo.toString())
                            showData(data)
                            binding.progressBar.visibility = View.GONE
                            binding.layoutVideoPlayer.visibility = View.VISIBLE
                            binding.cardDetail.visibility = View.VISIBLE
                            binding.nestedView.visibility = View.VISIBLE

                        }
                    }

                    Status.ERROR -> {
                        Toast.makeText(requireContext(), R.string.wrongMessage, Toast.LENGTH_SHORT).show()
                        binding.progressBar.visibility = View.VISIBLE
                    }

                    Status.LOADING -> {
                        binding.layoutVideoPlayer.visibility = View.GONE
                        binding.cardDetail.visibility = View.GONE
                        binding.nestedView.visibility = View.GONE
                    }
                }
            }
        }

    }

    @SuppressLint("SetTextI18n")
    private fun showData(data: CoursesResponsebyName) {
        val courseData: DataCourses? = data.data
        
        val youTubePlayerView: YouTubePlayerView = binding.youtubePlayerView
        lifecycle.addObserver(youTubePlayerView)
        youTubePlayerView.addYouTubePlayerListener(object: AbstractYouTubePlayerListener(){
            override fun onReady(youTubePlayer: YouTubePlayer) {
                super.onReady(youTubePlayer)
                val urlVideo = courseData?.introVideo
                val videoId = extractYouTubeVideoId(urlVideo!!)
                Log.d("VIDEOID", videoId.toString())
                youTubePlayer.loadVideo(videoId.toString(), 0F)
            }
        })

        binding.tvCategoryDetail.text = courseData?.category
        binding.tvTitleDetailCourse.text = courseData?.name
        binding.tvAuthorDetailCourse.text = courseData?.author
        binding.tvRatingDetail.text = courseData?.rating.toString()
        binding.tvLevelDetailCourse.text = "${courseData?.level} Level"
        binding.tvDetailTime.text = "${courseData?.totalMinute} Menit"
        binding.tvDetailModul.text = "${courseData?.totalModule} Modul"


        if (courseData != null) {
            showTabLayout(courseData.description, courseData.classTarget, courseData.id, courseData.author, courseData.image)
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


    private fun showTabLayout(description: String, classTarget: List<String>, id: String, author: String, image: String) {
        val fragmentList = arrayListOf(TentangKelasFragment.newInstance(description, classTarget), MateriKelasFragment.newInstance(id, author, image))
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
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        val bottomNavigationView = (requireActivity() as MainActivity).getBottomNavigationView()
        bottomNavigationView.visibility = View.VISIBLE
    }


    companion object {
        const val DETAIL_KELAS = "detailKelas"
        const val KELAS_TARGET = "kelasTarget"
        const val MATERI_KELAS = "materiKelas"
        const val AUTHOR = "author"
        const val IMAGE = "image"
    }

}

