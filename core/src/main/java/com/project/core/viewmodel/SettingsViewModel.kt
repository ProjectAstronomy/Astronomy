package com.project.core.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.project.core.entities.ApplicationIcon
import com.project.core.entities.ApplicationTheme
import com.project.core.entities.ImageResolution

class SettingsViewModel : ViewModel() {
    private val _imageResolution = MutableLiveData(ImageResolution.REGULAR)
    val imageResolution: LiveData<ImageResolution> get() = _imageResolution

    private val _applicationIcon = MutableLiveData<ApplicationIcon>()
    val applicationIcon: LiveData<ApplicationIcon> get() = _applicationIcon

    private val _applicationTheme = MutableLiveData<ApplicationTheme>()
    val applicationTheme: LiveData<ApplicationTheme> get() = _applicationTheme

    fun setImageResolution(imageResolution: ImageResolution) {
        _imageResolution.value = imageResolution
    }

    fun setApplicationIcon(applicationIcon: ApplicationIcon) {
        _applicationIcon.value = applicationIcon
    }

    fun setApplicationTheme(applicationTheme: ApplicationTheme) {
        _applicationTheme.value = applicationTheme
    }
}