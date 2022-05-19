package com.project.donki.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.project.core.net.AndroidNetworkStatus
import androidx.recyclerview.widget.LinearLayoutManager
import com.project.core.ui.BaseFragment
import com.project.core.viewmodel.SavedStateViewModelFactory
import com.project.donki.databinding.FragmentListFlrBinding
import com.project.donki.di.SCOPE_FLR_MODULE
import com.project.donki.entities.remote.SolarFlare
import com.project.donki.ui.adapters.FLRRecyclerViewAdapter
import com.project.donki.viewmodels.FLRViewModel
import com.project.donki.viewmodels.FLRViewModelFactory
import org.koin.android.ext.android.getKoin
import org.koin.android.ext.android.inject
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

    private val onSolarFlareClicked: (SolarFlare) -> Unit = { flrViewModel.insert(it) }
    private val adapterSolarVertical by lazy { FLRRecyclerViewAdapter(onSolarFlareClicked) }
    private val androidNetworkStatus: AndroidNetworkStatus by inject()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return providePersistentView(inflater, container, savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (!hasInitializedRootView) {
            hasInitializedRootView = true
            flrViewModel.load(androidNetworkStatus.isNetworkAvailable())
        }

        binding.rvListSolar.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        binding.rvListSolar.adapter = adapterSolarVertical

        with(flrViewModel) {
            responseSolarFlare().observe(viewLifecycleOwner) {
                // сохраняем массив (listSolarResponse) с данными из API
                val listSolarResponse = it

                // создаем вспомогательный массив (listCalendarDays), заполняем датами за 30 дней
                val listCalendarDays = mutableListOf<SolarFlare>()
                val calendar = Calendar.getInstance()
                val sdf = SimpleDateFormat("yyyy-MM-dd")

                repeat(30) {
                    calendar.add(Calendar.DAY_OF_YEAR, -1)
                    listCalendarDays.add(
                        SolarFlare(flrID = sdf.format(calendar.time), null, null, null, null, null,null,null, null,"header")
                    )
                }

                // создаем объединенный массив (listFullEveryDay)
                val listFullEveryDay = mutableListOf<SolarFlare>()
                var isNoSolarFlareThisDay: Boolean

                for (index in listCalendarDays.indices) {
                    val seekTime = listCalendarDays[index].flrID
                    listFullEveryDay.add(listCalendarDays[index])
                    isNoSolarFlareThisDay = true
                    listSolarResponse.forEach { solarFlare ->
                        if (solarFlare.beginTime?.take(10).equals(seekTime)) {
                            listFullEveryDay.add(solarFlare)
                            isNoSolarFlareThisDay = false
                        }
                    }
                    if (isNoSolarFlareThisDay) {
                        listFullEveryDay.add(SolarFlare(flrID = index.toString(), null, null,null,null, null,null,null,null, "no_flare"))
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