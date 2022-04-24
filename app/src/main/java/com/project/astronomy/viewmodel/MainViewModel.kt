package com.project.astronomy.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import com.project.astronomy.entities.ItemRv
import com.project.astronomy.repository.MainRepository
import com.project.core.viewmodel.BaseViewModel

//TODO: complete MainViewModel
class MainViewModel(
    private val savedStateHandle: SavedStateHandle,
    private val mainRepository: MainRepository
) : BaseViewModel(savedStateHandle) {

    val liveDataAPOD: LiveData<List<ItemRv>> = MutableLiveData(mainRepository.getListRvAPOD())
    val liveDataSolar: LiveData<List<ItemRv>> = MutableLiveData(mainRepository.getListRvSolarFlare())
    val liveDataGeo: LiveData<List<ItemRv>> = MutableLiveData(mainRepository.getListRvGeoStorm())
    val liveDataEpic: LiveData<List<ItemRv>> = MutableLiveData(mainRepository.getListRvEpic())
    val liveDataMars: LiveData<List<ItemRv>> = MutableLiveData(mainRepository.getListRvMars())
}