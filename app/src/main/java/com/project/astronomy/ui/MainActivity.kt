package com.project.astronomy.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.project.astronomy.R

class MainActivity : AppCompatActivity() {
    private val navHostFragment =
        supportFragmentManager.findFragmentById(R.id.activity_main_container) as NavHostFragment
    private val navController: NavController = navHostFragment.navController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp() || super.onSupportNavigateUp()
    }
}