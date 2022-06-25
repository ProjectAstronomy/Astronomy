package com.project.astronomy.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import com.project.astronomy.entities.ItemRv
import com.project.astronomy.repository.MainRepository
import com.project.core.viewmodel.BaseViewModel

class MainViewModel(
    savedStateHandle: SavedStateHandle,
    mainRepository: MainRepository
) : BaseViewModel(savedStateHandle) {

    val liveDataSolar: LiveData<List<ItemRv>> =
        MutableLiveData(mainRepository.getListRvSolarFlare())
    val liveDataEpic: LiveData<List<ItemRv>> = MutableLiveData(mainRepository.getListRvEpic())
    val liveDataMars: LiveData<List<ItemRv>> = MutableLiveData(mainRepository.getListRvMars())
}