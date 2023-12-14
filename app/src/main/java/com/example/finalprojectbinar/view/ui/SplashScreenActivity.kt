package com.example.finalprojectbinar.view.ui

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.core.content.ContextCompat
import androidx.core.view.get
import androidx.viewpager2.widget.ViewPager2
import com.example.finalprojectbinar.R
import com.example.finalprojectbinar.databinding.ActivitySplashScreenBinding
import com.example.finalprojectbinar.model.SplashScreen
import com.example.finalprojectbinar.util.Enum
import com.example.finalprojectbinar.util.SharedPreferenceHelper
import com.example.finalprojectbinar.view.adapters.SplashScreenAdapter
import com.example.finalprojectbinar.view.ui.login.LoginActivity


class SplashScreenActivity : AppCompatActivity() {
    private val list =  ArrayList<SplashScreen>()
    private val splashScreenAdapter = SplashScreenAdapter(list)
    private var _binding : ActivitySplashScreenBinding? = null
    private val binding get() = _binding!!
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivitySplashScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)
        SharedPreferenceHelper.init(this)
        val savedToken = SharedPreferenceHelper.read(Enum.PREF_NAME.value)
        if (savedToken != null) {
            Intent(applicationContext, MainActivity::class.java).also {
                startActivity(it)
                finish()
            }
        } else {
            val splashScreenSlider = binding.splashScreenSlider
            splashScreenSlider.adapter = splashScreenAdapter
            list.addAll(getAllSplashData())
            setupIndicator()
            setCurrentIndicator(1)
            splashScreenSlider.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback(){
                override fun onPageSelected(position: Int) {
                    super.onPageSelected(position)
                    setCurrentIndicator(position)
                }
            })
            binding.imageButton.setOnClickListener {
                if(splashScreenSlider.currentItem + 1 < splashScreenAdapter.itemCount){
                    splashScreenSlider.currentItem += 1
                }else{
                    Intent(applicationContext, MainActivity::class.java).also {
                        startActivity(it)
                        finish()
                    }
                }
            }

            binding.loginButton.setOnClickListener {
                startActivity(Intent(this, LoginActivity::class.java))
            }
        }

    }

    private fun setCurrentIndicator(index: Int) {
        val indicatorContainer = binding.indicatorContainer
        val childCount = indicatorContainer.childCount
        for(i in 0 until childCount){
            val imageView = indicatorContainer[i] as ImageView
            if(i == index){
                imageView.setImageDrawable(
                    ContextCompat.getDrawable(
                        applicationContext,
                        R.drawable.indicator_active
                    )
                )
            }else{
                imageView.setImageDrawable(
                    ContextCompat.getDrawable(
                        applicationContext,
                        R.drawable.indicator_inactive
                    )
                )

            }
        }

    }

    private fun setupIndicator() {
        val indicatorContainer = binding.indicatorContainer
        val indicator = arrayOfNulls<ImageView>(splashScreenAdapter.itemCount)
        val layoutParams: LinearLayout.LayoutParams =
            LinearLayout.LayoutParams(WRAP_CONTENT, WRAP_CONTENT)
        layoutParams.setMargins(8,0,8, 0)
        for(i in indicator.indices){
            indicator[i] = ImageView(applicationContext)
            indicator[i].apply {
                this?.setImageDrawable(
                    ContextCompat.getDrawable(
                        applicationContext,
                        R.drawable.indicator_inactive
                    )
                )
                this?.layoutParams = layoutParams
            }
            indicatorContainer.addView(indicator[i])
        }
    }



    @SuppressLint("Recycle")
    private fun getAllSplashData(): ArrayList<SplashScreen> {
        val desk = resources.getStringArray(R.array.splash_desk)
        val image = resources.obtainTypedArray(R.array.splash_img)
        val listSplash = ArrayList<SplashScreen>()
        for(i in desk.indices){
            val splash = SplashScreen(desk[i], image.getResourceId(i, -1))
            listSplash.add(splash)
        }
        return listSplash
    }
}