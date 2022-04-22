package com.project.mrp.viewmodel

import androidx.lifecycle.SavedStateHandle
import com.project.core.viewmodel.BaseViewModel
import com.project.mrp.domain.Repository

class MRPViewModel(savedStateHandle: SavedStateHandle, repository: Repository) : BaseViewModel() {
    override fun handleThrowable(throwable: Throwable) {
        TODO("Not yet implemented")
    }

    override fun loadAsync() {
        TODO("Not yet implemented")
    }
}