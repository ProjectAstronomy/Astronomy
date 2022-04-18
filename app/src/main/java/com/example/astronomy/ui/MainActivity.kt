package com.example.astronomy.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.astronomy.ui.main.MainFragment
import com.project.astronomy.R

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, MainFragment.newInstance())
                .commitNow()
        }
    }
}