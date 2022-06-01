package com.project.astronomy.ui

import android.content.Context

class ResourceProvider(private val applicationContext: Context) {
    fun getString(stringResId: Int): String = applicationContext.getString(stringResId)
}