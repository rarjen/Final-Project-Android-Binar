package com.example.finalprojectbinar.view.ui


import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import androidx.viewpager2.widget.ViewPager2
import com.example.finalprojectbinar.R
import com.example.finalprojectbinar.databinding.ActivityMainBinding
import com.example.finalprojectbinar.databinding.FragmentDetailKelasBinding
import com.example.finalprojectbinar.util.Enum
import com.example.finalprojectbinar.util.SharedPreferenceHelper
import com.example.finalprojectbinar.view.fragments.bottomsheets.BottomSheetMustLoginFragment
import com.example.finalprojectbinar.view.fragments.detailkelas.DetailKelasFragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlin.math.log

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        SharedPreferenceHelper.init(this)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navHomeFragment = supportFragmentManager.findFragmentById(R.id.container_fragment) as NavHostFragment
        val navController = navHomeFragment.navController
        val navView: BottomNavigationView = binding.bottomNavigation
        navView.setupWithNavController(navController)

        navController.addOnDestinationChangedListener { _, destination, _ ->
            val isLogin = SharedPreferenceHelper.read(Enum.PREF_NAME.value)
            if (isLogin == null) {
                when (destination.id) {
                    R.id.notificationFragment3, R.id.settingFragment, R.id.kelasSayaFragment -> {
                        val bottomSheetFragmentMustLogin = BottomSheetMustLoginFragment()
                        bottomSheetFragmentMustLogin.show(supportFragmentManager, bottomSheetFragmentMustLogin.tag)
                        navController.navigate(R.id.berandaFragment)
                    }
                }
            }
        }
    }

    fun getBottomNavigationView(): BottomNavigationView {
        return binding.bottomNavigation
    }
}   