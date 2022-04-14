package com.project.core.viewmodel

import androidx.lifecycle.SavedStateHandle

interface ViewModelAssistedFactory<V : BaseViewModel> {
    fun create(savedStateHandle: SavedStateHandle): V
}