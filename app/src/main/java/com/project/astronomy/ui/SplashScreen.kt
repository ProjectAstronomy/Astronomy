package com.project.astronomy.ui

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import com.project.astronomy.R
import com.project.astronomy.databinding.SplashScreenBinding


@SuppressLint("CustomSplashScreen")
class SplashScreen : Activity() {

    companion object {
        const val SPLASH_DISPLAY_LENGTH = 5000
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.splash_screen)

        Handler(Looper.getMainLooper()).postDelayed(Runnable {
            val mainIntent = Intent(this@SplashScreen, MainActivity::class.java)
            this@SplashScreen.startActivity(mainIntent)
            this@SplashScreen.finish()
        }, SPLASH_DISPLAY_LENGTH.toLong())
    }
}