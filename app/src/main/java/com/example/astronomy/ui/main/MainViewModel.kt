package com.example.astronomy.ui.main

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.astronomy.model.RepositoryRv
import com.example.astronomy.model.entity.ItemRv

class MainViewModel : ViewModel() {

    val liveDataAPOD = MutableLiveData<List<ItemRv>>()
    val liveDataSolar = MutableLiveData<List<ItemRv>>()
    val liveDataGeo = MutableLiveData<List<ItemRv>>()
    val liveDataEpic = MutableLiveData<List<ItemRv>>()
    val liveDataMars = MutableLiveData<List<ItemRv>>()

    init {
        updateListFirst()
    }

    private fun updateListFirst() {
        liveDataAPOD.value = RepositoryRv.getListRvAPOD()
        liveDataSolar.value = RepositoryRv.getListRvSolarFlare()
        liveDataGeo.value = RepositoryRv.getListRvGeoStorm()
        liveDataEpic.value = RepositoryRv.getListRvEpic()
        liveDataMars.value = RepositoryRv.getListRvMars()
    }

}