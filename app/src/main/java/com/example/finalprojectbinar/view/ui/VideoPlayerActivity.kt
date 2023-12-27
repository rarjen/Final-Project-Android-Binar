package com.example.finalprojectbinar.view.ui

import android.content.pm.ActivityInfo
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.OnBackPressedCallback
import com.example.finalprojectbinar.R
import com.example.finalprojectbinar.databinding.ActivityPaymentBinding
import com.example.finalprojectbinar.databinding.ActivityVideoPlayerBinding
import com.example.finalprojectbinar.view.fragments.detailkelas.MateriKelasFragment
import com.example.finalprojectbinar.viewmodel.MyViewModel
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.FullscreenListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.options.IFramePlayerOptions
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView
import org.koin.android.ext.android.inject

class VideoPlayerActivity : AppCompatActivity() {

    private var _binding: ActivityVideoPlayerBinding? = null
    private val binding get() = _binding!!

    private lateinit var youTubePlayer: YouTubePlayer

    private var isFullscreen = false
    private val onBackPressedCallback = object : OnBackPressedCallback(true) {
        override fun handleOnBackPressed() {
            if (isFullscreen) {
                // if the player is in fullscreen, exit fullscreen
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
            .fullscreen(1) // enable full screen button
            .build()

        youTubePlayerView.enableAutomaticInitialization = false

        youTubePlayerView.addFullscreenListener(object : FullscreenListener {
            override fun onEnterFullscreen(fullscreenView: View, exitFullscreen: () -> Unit) {
                isFullscreen = true

                // the video will continue playing in fullscreenView
                youTubePlayerView.visibility = View.GONE
                fullscreenViewContainer.visibility = View.VISIBLE
                fullscreenViewContainer.addView(fullscreenView)

                // optionally request landscape orientation
//                 requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
            }

            override fun onExitFullscreen() {
                isFullscreen = false

                // the video will continue playing in the player
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

        binding.ivBack.setOnClickListener {
            navigateBack()
        }
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