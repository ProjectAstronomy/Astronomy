package com.project.astronomy.ui

import android.os.Bundle
import androidx.preference.PreferenceFragmentCompat
import com.project.astronomy.R

class SettingsFragment : PreferenceFragmentCompat() {

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.root_preferences, rootKey)
    }
}