package com.project.astronomy.ui

import android.content.SharedPreferences
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.preference.PreferenceManager
import com.project.astronomy.R
import com.project.astronomy.R.style.Theme_Astronomy
import com.project.core.entities.ImageResolution
import com.project.core.viewmodel.SettingsViewModel

class MainActivity : AppCompatActivity(), SharedPreferences.OnSharedPreferenceChangeListener {
    private var navController: NavController? = null
    private val settingsViewModel: SettingsViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setCurrentTheme()
        setContentView(R.layout.main_activity)
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.activity_main_container) as NavHostFragment
        navController = navHostFragment.navController
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController?.navigateUp() == true || super.onSupportNavigateUp()
    }

    override fun onResume() {
        super.onResume()
        PreferenceManager.getDefaultSharedPreferences(this)
            .registerOnSharedPreferenceChangeListener(this)
    }

    override fun onPause() {
        super.onPause()
        PreferenceManager.getDefaultSharedPreferences(this)
            .unregisterOnSharedPreferenceChangeListener(this)
    }

    override fun onSharedPreferenceChanged(sharedPreferences: SharedPreferences, key: String) {
        val themesKey = getString(R.string.themes_key)
        val imageResolutionKey = getString(R.string.image_resolution_key)

        when (key) {
            themesKey -> recreate()
            imageResolutionKey -> onImageResolutionChanged(sharedPreferences, key)
        }
    }

    private fun onImageResolutionChanged(sharedPreferences: SharedPreferences, key: String) {
        val defValue = getString(R.string.image_resolution_default_value)
        val hd = getString(R.string.hd)
        val imageResolution = when (sharedPreferences.getString(key, defValue)) {
            hd -> ImageResolution.HD
            else -> ImageResolution.REGULAR
        }
        settingsViewModel.setImageResolution(imageResolution)
    }

    private fun setCurrentTheme() {
        val key = getString(R.string.themes_key)
        val defValue = getString(R.string.theme_default_value)
        val originalTheme = getString(R.string.theme_original)
        val blackAndWhiteTheme = getString(R.string.theme_blackAndWhite)
        val marsTheme = getString(R.string.theme_mars)

        when (PreferenceManager.getDefaultSharedPreferences(this).getString(key, defValue)) {
            originalTheme -> {
                setTheme(R.style.Theme_Astronomy_Earth)
            }
            blackAndWhiteTheme -> {
                setTheme(Theme_Astronomy)
            }
            marsTheme -> {
                setTheme(R.style.Theme_Astronomy_Mars)
            }
        }
    }
}