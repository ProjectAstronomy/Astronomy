package com.example.astronomy.ui.apod

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.astronomy.model.RepositoryRv
import com.example.astronomy.model.entity.APODResponseTEMP

class ListAPODViewModel : ViewModel() {

    val liveDataAPODVertical = MutableLiveData<List<APODResponseTEMP>>()

    init {
        updateListFirst()
    }

    private fun updateListFirst() {
        liveDataAPODVertical.value = RepositoryRv.getListAPODResponseTEMP()
    }

}