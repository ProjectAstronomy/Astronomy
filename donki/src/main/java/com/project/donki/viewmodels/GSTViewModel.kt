package com.project.donki.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.project.core.viewmodel.BaseViewModel
import com.project.donki.entities.local.adapteritems.gst.GstAdapterItemDetailed
import com.project.donki.entities.local.adapteritems.gst.GstAdapterItemHeader
import com.project.donki.entities.local.adapteritems.gst.IGstAdapterItem
import com.project.donki.entities.remote.GeomagneticStorm
import com.project.donki.usecases.GSTUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class GSTViewModel(
    private val savedStateHandle: SavedStateHandle,
    private val gstUseCase: GSTUseCase
) : BaseViewModel(savedStateHandle) {

    companion object {
        private const val GST_RESPONSE = "GST_RESPONSE"
    }

    fun responseGeomagneticStorms(): LiveData<List<GeomagneticStorm>> =
        savedStateHandle.getLiveData(GST_RESPONSE)

    override fun onCleared() {
        super.onCleared()
        savedStateHandle.set(GST_RESPONSE, null)
    }

    fun load(isNetworkAvailable: Boolean) {
        cancelJob()
        viewModelCoroutineScope.launch {
            var result: List<GeomagneticStorm>
            withContext(Dispatchers.IO) {
                result = gstUseCase.load(isNetworkAvailable).reversed()
            }
            saveLoadedData(result)
        }
    }

    fun reload() {
        cancelJob()
        viewModelCoroutineScope.launch {
            var result: List<GeomagneticStorm>
            withContext(Dispatchers.IO) {
                result = gstUseCase.reload().reversed()
            }
            saveLoadedData(result)
        }
    }

    fun prepareListForAdapter(listApi: List<GeomagneticStorm>): List<IGstAdapterItem> {
        val listForAdapter = mutableListOf<IGstAdapterItem>()

        for (index in listApi.indices) {
            // добавляем хедер
            listForAdapter.add(
                GstAdapterItemHeader(
                    flrID = "${listApi[index].startTime.toString()}_header",
                    beginTime = listApi[index].startTime.toString()
                )
            )

            // добавляем все имеющиеся в этот день случаи Geomagnetic Storm
            // - в элементе api это вложенный список "allKpIndex": [ {},{},{} ]
            listApi[index].allKpIndex?.forEach {
                listForAdapter.add(
                    GstAdapterItemDetailed(
                        startTime = listApi[index].startTime.toString(),
                        observedTime = it.observedTime,
                        kpIndex = it.kpIndex,
                        source = it.source
                    )
                )
            }
        }
        return listForAdapter
    }


    fun insert(geomagneticStorm: GeomagneticStorm) {
        viewModelScope.launch { gstUseCase.insert(geomagneticStorm) }
    }

    private fun saveLoadedData(result: List<GeomagneticStorm>) {
        if (!savedStateHandle.contains(GST_RESPONSE)) {
            savedStateHandle.set(GST_RESPONSE, result)
        } else {
            val list =
                savedStateHandle.getLiveData<List<GeomagneticStorm>>(GST_RESPONSE).value?.toMutableList()
            list?.addAll(result)
            savedStateHandle.set(GST_RESPONSE, list)
        }
    }
}