package com.project.astronomy.viewmodel

import androidx.lifecycle.SavedStateHandle
import com.project.astronomy.repository.MainRepository
import com.project.core.viewmodel.BaseViewModel

class MainViewModel(
    private val savedStateHandle: SavedStateHandle,
    private val mainRepository: MainRepository
) : BaseViewModel()