package com.project.donki.viewmodels

import android.annotation.SuppressLint
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.project.core.viewmodel.BaseViewModel
import com.project.donki.entities.local.adapteritems.flr.ISolarFlareAdapterItem
import com.project.donki.entities.local.adapteritems.flr.SolarFlareAdapterItemDetailed
import com.project.donki.entities.local.adapteritems.flr.SolarFlareAdapterItemHeader
import com.project.donki.entities.local.adapteritems.flr.SolarFlareAdapterItemNoFlare
import com.project.donki.entities.remote.SolarFlare
import com.project.donki.usecases.FLRUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.text.SimpleDateFormat
import java.util.*

class FLRViewModel(
    private val savedStateHandle: SavedStateHandle,
    private val flrUseCase: FLRUseCase
) : BaseViewModel(savedStateHandle) {

    companion object {
        private const val FLR_RESPONSE = "FLR_RESPONSE"
    }

    fun responseSolarFlare(): LiveData<List<ISolarFlareAdapterItem>> =
        savedStateHandle.getLiveData(FLR_RESPONSE)

    override fun onCleared() {
        super.onCleared()
        savedStateHandle.set(FLR_RESPONSE, null)
    }

    fun load(isNetworkAvailable: Boolean) {
        cancelJob()
        viewModelCoroutineScope.launch {
            var result: List<SolarFlare>
            withContext(Dispatchers.IO) {
                result = flrUseCase.load(isNetworkAvailable).reversed()
            }
            saveLoadedData(prepareListForAdapter(result))
        }
    }

    fun reload() {
        cancelJob()
        viewModelCoroutineScope.launch {
            var result: List<SolarFlare>
            withContext(Dispatchers.IO) {
                result = flrUseCase.reload().reversed()
                Log.d("TAG", "55555555555_reloadList from VM__$result")
            }
            //Log.d("TAG", "55555555555_reloadList from VM__$result")
            saveLoadedData(prepareListForAdapter(result))
            //Log.d("TAG", "55555555555_reloadList from VM__$result")
        }
    }

    @SuppressLint("SimpleDateFormat")
    fun prepareListForAdapter(listApi: List<SolarFlare>): List<ISolarFlareAdapterItem>{
        val simpleDateFormat = SimpleDateFormat("yyyy-MM-dd")
        // Формирование списка для адаптера
        val listForAdapter = mutableListOf<ISolarFlareAdapterItem>()

        // инициализируем крайнюю (предыдущую) дату для header (хедер - заголовок с датой),
        // устанавливаем предыдущую дату на "завтра" от сегодняшней даты/от начала списка
        var previousHeaderDay = Calendar.getInstance()
        val pDay = listApi[0].flrID.substring(8, 10).toInt()
        val pMonth = listApi[0].flrID.substring(5, 7).toInt() -1
        val pYear = listApi[0].flrID.substring(0, 4).toInt()
        previousHeaderDay.set(pYear, pMonth, pDay)

        previousHeaderDay.add(Calendar.DAY_OF_YEAR, 1)
        var previousHeaderDayString = simpleDateFormat.format(previousHeaderDay.time)

        for (index in listApi.indices) {
            // По элементу в листе api определяем новую дату для нового "текущего" header
            val currentHeaderDayString = listApi[index].flrID.take(10)
            // проверяем на наличие уже ранее добавленного такого хедера (с такой датой)
            if (previousHeaderDayString != currentHeaderDayString) {
                // определили, что такого хедера еще не было, и можно его уже добавить, но ! -

                // Прежде чем добавить новый хедер, нужно проверить период от предыдущего
                // хедера. Возможно в этом периоде были даты без солнечной активности
                // (в api не указываются дни без активности). Но в список адаптера
                // дни без активности надо добавить.
                previousHeaderDay.add(Calendar.DAY_OF_YEAR, -1)
                previousHeaderDayString = simpleDateFormat.format(previousHeaderDay.time)

                while (previousHeaderDayString != currentHeaderDayString) {
                    // Добавляем хедер для дня без активности
                    listForAdapter.add(
                        SolarFlareAdapterItemHeader(
                            flrID = "${previousHeaderDayString}_header",
                            beginTime = previousHeaderDayString
                        )
                    )
                    // Добавляем строчку "no flare" для дня без активности
                    listForAdapter.add(
                        SolarFlareAdapterItemNoFlare(
                            flrID = "${previousHeaderDayString}_no_flare",
                            beginTime = previousHeaderDayString
                        )
                    )
                    previousHeaderDay.add(Calendar.DAY_OF_YEAR, -1)
                    previousHeaderDayString = simpleDateFormat.format(previousHeaderDay.time)
                }

                // Теперь можно добавить текущий хедер
                listForAdapter.add(
                    SolarFlareAdapterItemHeader(
                        flrID = "${currentHeaderDayString}_header",
                        beginTime = currentHeaderDayString
                    )
                )
            }

            // Добавляем строку со сведения о конкретной SolarFlare
            with(listApi[index]) {
                listForAdapter.add(
                    SolarFlareAdapterItemDetailed(
                        flrID = this.flrID,
                        beginTime = this.beginTime,
                        peakTime = this.peakTime,
                        endTime = this.endTime,
                        classType = this.classType,
                        sourceLocation = this.sourceLocation,
                        activeRegionNum = this.activeRegionNum,
                        link = this.link
                    )
                )
            }
        }
        return listForAdapter
    }

    fun insert(solarFlare: SolarFlare) {
        viewModelScope.launch { flrUseCase.insert(solarFlare) }
    }

    private fun saveLoadedData(result: List<ISolarFlareAdapterItem>) {
        if (!savedStateHandle.contains(FLR_RESPONSE)) {
            savedStateHandle.set(FLR_RESPONSE, result)
        } else {
            val list =
                savedStateHandle.getLiveData<List<ISolarFlareAdapterItem>>(FLR_RESPONSE).value?.toMutableList()
            list?.addAll(result)
            savedStateHandle.set(FLR_RESPONSE, list)
        }
    }
}