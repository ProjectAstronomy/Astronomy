package com.project.donki.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.project.core.ui.BaseFragment
import com.project.core.viewmodel.SavedStateViewModelFactory
import com.project.donki.R
import com.project.donki.databinding.FragmentListFlrBinding
import com.project.donki.di.SCOPE_FLR_MODULE
import com.project.donki.entities.SolarFlare
import com.project.donki.viewmodels.FLRViewModel
import com.project.donki.viewmodels.FLRViewModelFactory
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import org.koin.android.ext.android.getKoin
import org.koin.core.qualifier.named
import org.koin.core.scope.Scope
import java.text.SimpleDateFormat
import java.util.*

class FLRListFragment : BaseFragment<FragmentListFlrBinding>(FragmentListFlrBinding::inflate) {
    private val flrListFragmentScope: Scope =
        getKoin().getOrCreateScope(SCOPE_FLR_MODULE, named(SCOPE_FLR_MODULE))

    private val flrViewModelFactory: FLRViewModelFactory = flrListFragmentScope.get()
    private val flrViewModel: FLRViewModel by viewModels {
        SavedStateViewModelFactory(flrViewModelFactory, this)
    }

    //    private val adapter by lazy { FLRRecyclerViewAdapter() }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        //return providePersistentView(inflater, container, savedInstanceState)
        return inflater.inflate(R.layout.fragment_list_flr, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (!hasInitializedRootView) {
            hasInitializedRootView = true

            flrViewModel.loadAsync()
        }

        view.findViewById<RecyclerView>(R.id.rv_list_solar).layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        val adapterSolarVertical = FLRRecyclerViewAdapter()
        view.findViewById<RecyclerView>(R.id.rv_list_solar).adapter = adapterSolarVertical


//        lifecycleScope.launch {
//            adapter.isNeededToLoadInFlow.collect { isNeededToLoad ->
//                if (isNeededToLoad) flrViewModel.loadAsync()
//            }
//        }


        with(flrViewModel) {
            responseSolarFlare().observe(viewLifecycleOwner) {
                // сохраняем массив (listSolarResponse) с данными из API
                var listSolarResponse = it

                // создаем вспомогательный массив (listCalendarDays), заполняем датами за 30 дней
                val listCalendarDays = mutableListOf<SolarFlare>()
                val calendar = Calendar.getInstance()
                val sdf = SimpleDateFormat("yyyy-MM-dd")

                repeat(30) {
                    calendar.add(Calendar.DAY_OF_YEAR, -1)
                    listCalendarDays.add(
                        SolarFlare(null, null, beginTime = sdf.format(calendar.time),null,null, classType = "header",null,null, null,null)
                    )
                }

                // создаем объединенный массив (listFullEveryDay)
                val listFullEveryDay = mutableListOf<SolarFlare>()
                var isNoSolarFlareThisDay = true

                for (index in listCalendarDays.indices) {
                    var seekTime = listCalendarDays[index].beginTime
                    listFullEveryDay.add(listCalendarDays[index])
                    isNoSolarFlareThisDay = true
                    listSolarResponse.forEach {
                        if (it.beginTime?.take(10).equals(seekTime)) {
                            listFullEveryDay.add(it)
                            isNoSolarFlareThisDay = false
                        }
                    }
                    if (isNoSolarFlareThisDay) {
                        listFullEveryDay.add(SolarFlare(null, null, beginTime = "There is no solar flare",null,null, classType = "no_flare",null,null, null,null))
                    }
                }

                adapterSolarVertical.adapterList = listFullEveryDay
            }
            error().observe(viewLifecycleOwner) { showThrowable(it) }
        }


    }

    override fun onDestroy() {
        super.onDestroy()
        flrListFragmentScope.close()
    }
}