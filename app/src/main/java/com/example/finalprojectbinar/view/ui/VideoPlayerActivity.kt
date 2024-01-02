package com.example.finalprojectbinar.view.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.OnBackPressedCallback
import com.bumptech.glide.Glide
import com.example.finalprojectbinar.databinding.ActivityVideoPlayerBinding
import com.example.finalprojectbinar.view.fragments.detailkelas.MateriKelasFragment
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.FullscreenListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.options.IFramePlayerOptions

class VideoPlayerActivity : AppCompatActivity() {

    private var _binding: ActivityVideoPlayerBinding? = null
    private val binding get() = _binding!!

    private lateinit var youTubePlayer: YouTubePlayer

    private var isFullscreen = false
    private val onBackPressedCallback = object : OnBackPressedCallback(true) {
        override fun handleOnBackPressed() {
            if (isFullscreen) {
                youTubePlayer.toggleFullscreen()
            } else {
                finish()
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityVideoPlayerBinding.inflate(layoutInflater)
        setContentView(binding.root)

        onBackPressedDispatcher.addCallback(onBackPressedCallback)

        val youTubePlayerView = binding.youtubePlayerView
        val fullscreenViewContainer = binding.fullScreenViewContainer

        val iFramePlayerOptions = IFramePlayerOptions.Builder()
            .controls(1)
            .fullscreen(1)
            .build()

        youTubePlayerView.enableAutomaticInitialization = false

        youTubePlayerView.addFullscreenListener(object : FullscreenListener {
            override fun onEnterFullscreen(fullscreenView: View, exitFullscreen: () -> Unit) {
                isFullscreen = true

                binding.cardCredentials.visibility = View.GONE
                binding.cardKomentar.visibility = View.GONE

                youTubePlayerView.visibility = View.GONE
                fullscreenViewContainer.visibility = View.VISIBLE
                fullscreenViewContainer.addView(fullscreenView)

            }

            override fun onExitFullscreen() {
                isFullscreen = false

                binding.cardCredentials.visibility = View.VISIBLE
                binding.cardKomentar.visibility = View.VISIBLE

                youTubePlayerView.visibility = View.VISIBLE
                fullscreenViewContainer.visibility = View.GONE
                fullscreenViewContainer.removeAllViews()
            }
        })

        val videoId = intent.getStringExtra(MateriKelasFragment.VIDEO_ID).toString()

        youTubePlayerView.initialize(object : AbstractYouTubePlayerListener() {
            override fun onReady(youTubePlayer: YouTubePlayer) {
                this@VideoPlayerActivity.youTubePlayer = youTubePlayer
                youTubePlayer.loadVideo(videoId, 0f)

                val enterFullscreenButton = binding.enterFullscreenButton
                enterFullscreenButton.setOnClickListener {
                    youTubePlayer.toggleFullscreen()
                }
            }
        }, iFramePlayerOptions)

        lifecycle.addObserver(youTubePlayerView)

        showCredentials()

        binding.ivBack.setOnClickListener {
            navigateBack()
        }
    }

    private fun showCredentials(){
        val title = intent.getStringExtra(MateriKelasFragment.TITLE).toString()
        val author = intent.getStringExtra(MateriKelasFragment.AUTHOR).toString()
        val image = intent.getStringExtra(MateriKelasFragment.IMAGE).toString()
        binding.authorTv.text = author
        binding.titleVideo.text = title
        Glide.with(this)
            .load(image)
            .into(binding.thumbnailView)
    }

    private fun navigateBack() {
        val fragmentManager = supportFragmentManager
        if (fragmentManager.backStackEntryCount > 0) {
            fragmentManager.popBackStack()
        } else {
            finish()
        }
    }


    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}