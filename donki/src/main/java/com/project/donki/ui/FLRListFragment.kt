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


//        val listSolar = mutableListOf(
//            SolarFlare (null, null, beginTime= "2022-04-11T05:00Z", null, null, classType = "C2.2", null, null, null, null),
//            SolarFlare (null, null, beginTime = "2022-04-15T11:07Z", null, null, classType = "M2.2",null, null, null, null, ),
//            SolarFlare (null, null, beginTime = "2022-04-15T13:47Z", null, null, classType = "M1.9",null, null, null, null, ),
//            SolarFlare (null, null, beginTime = "2022-04-17", null, null, classType = "M1.0",null, null, null, null, ),
//            SolarFlare (null, null, beginTime = "2022-04-22T03:17Z", null, null, classType = "A1.1",null, null, null, null, ),
//            SolarFlare (null, null, beginTime = "2022-04-22T03:17Z", null, null, classType = "_B1.1",null, null, null, null, ),
//            SolarFlare (null, null, beginTime = "2022-04-22T03:17Z", null, null, classType = "X1.1",null, null, null, null, ),
//            SolarFlare (null, null, beginTime = "2022-04-22T03:17Z", null, null, classType = "B1.1",null, null, null, null, ),
//            SolarFlare (null, null, beginTime = "2022-04-22T03:17Z", null, null, classType = "X1.1",null, null, null, null, ),
//        )
//        adapterSolarVertical.adapterList = listSolar


//        lifecycleScope.launch {
//            adapter.isNeededToLoadInFlow.collect { isNeededToLoad ->
//                if (isNeededToLoad) flrViewModel.loadAsync()
//            }
//        }

        // получаем массив (listSolarResponse) с данными из API
        var listSolarResponse = listOf<SolarFlare>()
        with(flrViewModel) {
            responseSolarFlare().observe(viewLifecycleOwner) {
                listSolarResponse = it
                println ("------------44---------- $it")
            }
            error().observe(viewLifecycleOwner) { showThrowable(it) }
        }

        // -------
        // создаем массив (listFullEveryDay), заполняем датами за 30 дней, класс солнечной активности "B"
//        val listFullEveryDay = mutableListOf<SolarFlare>()
//        val calendar = Calendar.getInstance()
//        val sdf = SimpleDateFormat("yyyy-MM-dd")
//
//        repeat(30) {
//            calendar.add(Calendar.DAY_OF_YEAR, -1)
//            listFullEveryDay.add(SolarFlare(null, null, beginTime = sdf.format(calendar.time),null, null, classType = "header",null, null, null, null ))
//        }

        // В массиве listFullEveryDay берем каждый элемент (из 30 шт.) и проверяем есть ли такой элемент в
        // массиве 2 (сравниваем по дате). Если есть, то заменяем элемент массива 1 на
        // элемент массива 2

//        for (index in listFullEveryDay.indices) {
//            var seekTime = listFullEveryDay[index].beginTime
//            listSolarResponse.forEach {
//                if (it.beginTime?.take(10).equals(seekTime)) {
//                    listFullEveryDay.add(it)
//                }
//            }
//        }

        //listFullEveryDay.add(listSolarResponse[2])


        adapterSolarVertical.adapterList = listSolarResponse
    }

    override fun onDestroy() {
        super.onDestroy()
        flrListFragmentScope.close()
    }
}