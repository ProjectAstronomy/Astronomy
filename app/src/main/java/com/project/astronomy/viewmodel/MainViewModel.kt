package com.project.astronomy.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import com.project.astronomy.entities.ItemRv
import com.project.astronomy.entities.APODAppState
import com.project.astronomy.repository.MainRepository
import com.project.core.viewmodel.BaseViewModel
import kotlinx.coroutines.launch
import java.util.*

class MainViewModel(
    private val savedStateHandle: SavedStateHandle,
    private val mainRepository: MainRepository
) : BaseViewModel() {
    companion object {
        private const val APODRESPONSE_TODAY = "APODRESPONSE_TODAY"
    }

    private val calendar = Calendar.getInstance()

    //val apodToday: LiveData<APODAppState>? = savedStateHandle[APODRESPONSE_TODAY]

    val liveDataAPOD: LiveData<List<ItemRv>> = MutableLiveData(mainRepository.getListRvAPOD())
    val liveDataSolar: LiveData<List<ItemRv>> = MutableLiveData(mainRepository.getListRvSolarFlare())
    val liveDataGeo: LiveData<List<ItemRv>> = MutableLiveData(mainRepository.getListRvGeoStorm())
    val liveDataEpic: LiveData<List<ItemRv>> = MutableLiveData(mainRepository.getListRvEpic())
    val liveDataMars: LiveData<List<ItemRv>> = MutableLiveData(mainRepository.getListRvMars())

    fun getAPODByDate() {
        savedStateHandle[APODRESPONSE_TODAY] = null
        cancelJob()
        viewModelCoroutineScope.launch {
            val year = calendar.get(Calendar.YEAR)
            val month = calendar.get(Calendar.MONTH)
            val day = calendar.get(Calendar.DAY_OF_MONTH)
            val response = mainRepository.getApodToday("$year-$month-$day")
            if (response.isSuccessful) {
                savedStateHandle[APODRESPONSE_TODAY] =
                    response.body()?.let { APODAppState.Success(it) }
            } else {
                //TODO: handle error
            }
        }
    }
}