package com.project.donki.ui

import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.project.core.net.AndroidNetworkStatus
import androidx.recyclerview.widget.LinearLayoutManager
import com.project.core.ui.BaseFragment
import com.project.core.viewmodel.SavedStateViewModelFactory
import com.project.donki.databinding.FragmentListFlrBinding
import com.project.donki.di.SCOPE_FLR_MODULE
import com.project.donki.entities.local.adapteritems.flr.ISolarFlareAdapterItem
import com.project.donki.entities.local.adapteritems.flr.SolarFlareAdapterItemHeader
import com.project.donki.entities.local.adapteritems.flr.SolarFlareAdapterItemLarge
import com.project.donki.entities.local.adapteritems.flr.SolarFlareAdapterItemSmall
import com.project.donki.entities.remote.SolarFlare
import com.project.donki.ui.adapters.FLRRecyclerViewAdapter
import com.project.donki.viewmodels.FLRViewModel
import com.project.donki.viewmodels.FLRViewModelFactory
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import org.koin.android.ext.android.getKoin
import org.koin.android.ext.android.inject
import org.koin.core.qualifier.named
import org.koin.core.scope.Scope

class FLRListFragment : BaseFragment<FragmentListFlrBinding>(FragmentListFlrBinding::inflate) {
    private val flrListFragmentScope: Scope =
        getKoin().getOrCreateScope(SCOPE_FLR_MODULE, named(SCOPE_FLR_MODULE))

    private val flrViewModelFactory: FLRViewModelFactory = flrListFragmentScope.get()
    private val flrViewModel: FLRViewModel by viewModels {
        SavedStateViewModelFactory(flrViewModelFactory, this)
    }

    private val onSolarFlareClicked: (SolarFlare) -> Unit = { flrViewModel.insert(it) }
    private val adapterSolarVertical by lazy { FLRRecyclerViewAdapter(onSolarFlareClicked) }
    private val androidNetworkStatus: AndroidNetworkStatus by inject()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return providePersistentView(inflater, container, savedInstanceState)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (!hasInitializedRootView) {
            hasInitializedRootView = true
            flrViewModel.load(androidNetworkStatus.isNetworkAvailable())
        }

        lifecycleScope.launch {
            adapterSolarVertical.isNeededToLoadInFlow.collect { isNeededToLoad ->
                if (isNeededToLoad && androidNetworkStatus.isNetworkAvailable()) {
                    flrViewModel.reload()
                }
            }
        }

        binding.rvListSolar.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        binding.rvListSolar.adapter = adapterSolarVertical

        with(flrViewModel) {
            responseSolarFlare().observe(viewLifecycleOwner) { listApi ->

                // сохраняем массив (listSolarResponse) с данными из API
                //val listSolarResponse = it
//                Log.d("TAG", "___________size_${listSolarResponse.size}")
//                listSolarResponse.forEach {
//                    Log.d("TAG", "___________size_${it.flrID}, ${it.beginTime}")
//                }


                val apiSize = listApi.size
                var previousHeaderPosition = -1
                val listForAdapter = mutableListOf<ISolarFlareAdapterItem>()

                for (index in listApi.indices) {
                    // 1. Добавляем header
                    val currentDayString = listApi[index].flrID.take(10)
                    with(listApi[index]) {
                        listForAdapter.add(
                            SolarFlareAdapterItemHeader(
                                flrID = "${currentDayString}_header",
                                beginTime = currentDayString,
                                peakTime = null,
                                endTime = null,
                                classType = null,
                                sourceLocation = null,
                                activeRegionNum = null,
                                link = null
                            )
                        )
                    }
                    // запоминаем позицию хедера для последующего сравнения (чтобы не накидать одинаковых хедеров)
                    previousHeaderPosition = listForAdapter.size-1

                    with(listApi[index]) {
                        listForAdapter.add(
                            SolarFlareAdapterItemSmall(
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
                    with(listApi[index]) {
                        listForAdapter.add(
                            SolarFlareAdapterItemLarge(
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

                Log.d("TAG", "_________****__size_${listApi.size}")

                listForAdapter.forEach {
                    println(it)
//                    it as SolarFlareAdapterItemSmall
//                    Log.d("TAG", "________****____${it.flrID}, ${it.beginTime}")
                }


                // определяем начальную дату из крайнего элемента
                //val fromDateString = listSolarResponse[listSolarResponse.size-1].flrID.take(10)

                // определяем сегодняшнюю дату (toDateString)
//                val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
//                val current = LocalDateTime.now()
//                val toDateString = current.format(formatter)

                // высчитываем количество дней в периоде между начальной и сегодняшней датами
//                val fromDate = LocalDate.parse(fromDateString, formatter)
//                val toDate = LocalDate.parse(toDateString, formatter)
//                val period = Period.between(fromDate, toDate)
//                val periodOfDays = period.years * 365 + period.months * 30 + period.days
//                Log.d("TAG","___________period_d_$periodOfDays")
//
//                // создаем вспомогательный массив (listCalendarDays), заполняем датами по размеру списка из api
//                val listCalendarDays = mutableListOf<SolarFlare>()
//                val calendarToDate = Calendar.getInstance()
//                val simpleDateFormat = SimpleDateFormat("yyyy-MM-dd")
//                repeat(periodOfDays) {
//                    calendarToDate.add(Calendar.DAY_OF_YEAR, -1)
//                    listCalendarDays.add(
//                        SolarFlare(flrID = simpleDateFormat.format(calendarToDate.time)+"_header", null, beginTime = simpleDateFormat.format(calendarToDate.time), null, null, null,null,null, null,"header")
//                    )
//                }
//                Log.d("TAG","___________size_${listCalendarDays.size}___${listCalendarDays[0].flrID}_${listCalendarDays[listCalendarDays.size-1].flrID}")

                // создаем объединенный массив (listFullEveryDay)
//                val listFullEveryDay = mutableListOf<SolarFlare>()
//                var isNoSolarFlareThisDay: Boolean
//
//                for (index in listCalendarDays.indices) {
//                    val seekTime = listCalendarDays[index].beginTime
//                    listFullEveryDay.add(listCalendarDays[index])
//                    isNoSolarFlareThisDay = true
//                    listSolarResponse.forEach { solarFlare ->
//                        if (solarFlare.beginTime?.take(10).equals(seekTime)) {
//                            listFullEveryDay.add(solarFlare)
//                            isNoSolarFlareThisDay = false
//                        }
//                    }
//                    if (isNoSolarFlareThisDay) {
//                        listFullEveryDay.add(SolarFlare(flrID = seekTime+"_no_flare", null, null,null,null, null,null,null,null, "no_flare"))
//                    }
//                }

                adapterSolarVertical.items = listForAdapter
            }
            error().observe(viewLifecycleOwner) { showThrowable(it) }
        }
    }


    override fun onDestroy() {
        super.onDestroy()
        flrListFragmentScope.close()
    }
}