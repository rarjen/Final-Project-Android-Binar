package com.example.finalprojectbinar.view.ui


import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.example.finalprojectbinar.R
import com.example.finalprojectbinar.databinding.ActivityMainBinding
import com.example.finalprojectbinar.util.Enum
import com.example.finalprojectbinar.util.SharedPreferenceHelper
import com.example.finalprojectbinar.util.Status
import com.example.finalprojectbinar.view.fragments.bottomsheets.BottomSheetMustLoginFragment
import com.example.finalprojectbinar.view.ui.login.LoginActivity
import com.example.finalprojectbinar.viewmodel.MyViewModel
import com.google.android.material.bottomnavigation.BottomNavigationView
import org.koin.android.ext.android.inject

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val viewModel: MyViewModel by inject()
    private lateinit var pref: SharedPreferenceHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        SharedPreferenceHelper.init(this)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        pref = SharedPreferenceHelper
        val savedToken = pref.read(Enum.PREF_NAME.value).toString()


        if (savedToken != "null") {
            // Token exists, validate JWT
            validateJWT(savedToken)
        }

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

    private fun validateJWT(token: String) {
        viewModel.validateJWT("Bearer $token").observe(this) {
            when (it.status) {
                Status.ERROR -> {
                    // Invalid JWT or other error, show login screen
                    navigateToLoginActivity()
                }
                else -> {
                    // JWT is valid, continue with the app
                    // TODO: Add any specific logic if needed
                }
            }
        }
    }

    private fun navigateToLoginActivity() {
        val intent = Intent(this@MainActivity, LoginActivity::class.java)
        startActivity(intent)
        finish()
    }

    fun getBottomNavigationView(): BottomNavigationView {
        return binding.bottomNavigation
    }
}