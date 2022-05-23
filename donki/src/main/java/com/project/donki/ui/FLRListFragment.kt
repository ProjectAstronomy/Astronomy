package com.project.donki.ui

import android.os.Build
import android.os.Bundle
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
import com.project.donki.entities.local.adapteritems.flr.*
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

                // Формирование списка для адаптера
                val listForAdapter = mutableListOf<ISolarFlareAdapterItem>()

                // инициализируем крайнюю (предыдущую) дату для header (хедер - заголовок с датой),
                // устанавливаем предыдущую дату на "завтра" от сегодняшней даты
                val simpleDateFormat = SimpleDateFormat("yyyy-MM-dd")
                var previousHeaderDaу = Calendar.getInstance()
                previousHeaderDaу.add(Calendar.DAY_OF_YEAR, 1)
                var previousHeaderDayString = simpleDateFormat.format(previousHeaderDaу.time)

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
                        previousHeaderDaу.add(Calendar.DAY_OF_YEAR, -1)
                        previousHeaderDayString = simpleDateFormat.format(previousHeaderDaу.time)

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
                            previousHeaderDaу.add(Calendar.DAY_OF_YEAR, -1)
                            previousHeaderDayString = simpleDateFormat.format(previousHeaderDaу.time)
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