package com.project.core.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.project.core.entities.ImageResolution

class SettingsViewModel : ViewModel() {
    private val _imageResolution = MutableLiveData(ImageResolution.REGULAR)
    val imageResolution: LiveData<ImageResolution> get() = _imageResolution

    private val _saveToGalleryEnabled = MutableLiveData(false)
    val saveToGalleryEnabled: LiveData<Boolean> get() = _saveToGalleryEnabled

    fun setImageResolution(imageResolution: ImageResolution) {
        _imageResolution.value = imageResolution
    }

    fun isSaveToGalleryEnabled(saveToGalleryEnabled: Boolean) {
        _saveToGalleryEnabled.value = saveToGalleryEnabled
    }
}