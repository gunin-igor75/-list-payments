package com.github.gunin_igor75.list_payments.presentation

import android.os.Bundle
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.github.gunin_igor75.list_payments.R
import com.github.gunin_igor75.list_payments.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private lateinit var navController: NavController

    private val callback = object : OnBackPressedCallback(false){
        override fun handleOnBackPressed() {
            navController.popBackStack(R.id.homeFragment, false)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val navHost =
            supportFragmentManager.findFragmentById(R.id.fragment_container) as NavHostFragment
        navController = navHost.navController
        NavigationUI.setupActionBarWithNavController(this, navController)
        launchBackPressed()
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.popBackStack(R.id.homeFragment, false) || super.onSupportNavigateUp()
    }

    private fun launchBackPressed() {
        onBackPressedDispatcher.addCallback(this, callback)
        callback.isEnabled = true
    }
}