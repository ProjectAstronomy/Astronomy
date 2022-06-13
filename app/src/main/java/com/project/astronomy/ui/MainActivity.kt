package com.project.astronomy.ui

import android.content.ComponentName
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.project.astronomy.BuildConfig
import com.project.astronomy.R
import com.project.astronomy.R.style.*
import com.project.core.entities.ApplicationIcon
import com.project.core.entities.ApplicationTheme
import com.project.core.entities.ApplicationTheme.*
import com.project.core.viewmodel.SettingsViewModel

class MainActivity : AppCompatActivity() {
    companion object {
        private const val MAIN_ACTIVITY_KEY = "MainActivityKey"
    }

    private var navController: NavController? = null
    private val settingsViewModel: SettingsViewModel by viewModels()
    private var applicationTheme: ApplicationTheme? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        savedInstanceState?.let {
            applicationTheme = it.getSerializable(MAIN_ACTIVITY_KEY) as? ApplicationTheme
        }
        setCurrentTheme()
        setContentView(R.layout.main_activity)
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.activity_main_container) as NavHostFragment
        navController = navHostFragment.navController
        settingsViewModel.applicationIcon.observe(this) { setIcon(it) }
        settingsViewModel.applicationTheme.observe(this) { applicationTheme = it }
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        applicationTheme = savedInstanceState.getSerializable(MAIN_ACTIVITY_KEY) as? ApplicationTheme
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putSerializable(MAIN_ACTIVITY_KEY, applicationTheme)
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController?.navigateUp() == true || super.onSupportNavigateUp()
    }

    private fun setCurrentTheme() {
        when (applicationTheme) {
            ORIGINAL -> setTheme(Theme_Astronomy)
            EARTH -> setTheme(Theme_Astronomy_Earth)
            MARS -> setTheme(Theme_Astronomy_Mars)
            null -> setTheme(Theme_Astronomy)
        }
    }

    private fun setIcon(applicationIcon: ApplicationIcon) {
        for (value in ApplicationIcon.values()) {
            val action = if (value == applicationIcon) {
                PackageManager.COMPONENT_ENABLED_STATE_ENABLED
            } else {
                PackageManager.COMPONENT_ENABLED_STATE_DISABLED
            }
            packageManager.setComponentEnabledSetting(
                ComponentName(BuildConfig.APPLICATION_ID, "${BuildConfig.APPLICATION_ID}.${value.name}"),
                action,
                PackageManager.DONT_KILL_APP
            )
        }
    }
}