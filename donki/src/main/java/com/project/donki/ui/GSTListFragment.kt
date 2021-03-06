package com.project.donki.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.whenCreated
import androidx.lifecycle.whenResumed
import com.project.core.net.AndroidNetworkStatus
import androidx.recyclerview.widget.LinearLayoutManager
import com.project.core.ui.BaseFragment
import com.project.core.viewmodel.SavedStateViewModelFactory
import com.project.donki.databinding.FragmentListGstBinding
import com.project.donki.di.SCOPE_GST_MODULE
import com.project.donki.entities.remote.GeomagneticStorm
import com.project.donki.ui.adapters.GSTRecyclerViewAdapter
import com.project.donki.ui.adapters.GeoRecyclerViewAdapter
import com.project.donki.viewmodels.GSTViewModel
import com.project.donki.viewmodels.factories.GSTViewModelFactory
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import org.koin.android.ext.android.getKoin
import org.koin.android.ext.android.inject
import org.koin.core.qualifier.named

class GSTListFragment : BaseFragment<FragmentListGstBinding>(FragmentListGstBinding::inflate) {
    private val gstListFragmentScope =
        getKoin().getOrCreateScope(SCOPE_GST_MODULE, named(SCOPE_GST_MODULE))

    private val gstViewModelFactory: GSTViewModelFactory = gstListFragmentScope.get()
    private val gstViewModel: GSTViewModel by viewModels {
        SavedStateViewModelFactory(gstViewModelFactory, this)
    }

    private val onGeomagneticStormClicked: (GeomagneticStorm) -> Unit = { gstViewModel.insert(it) }
    private val adapter by lazy { GeoRecyclerViewAdapter(onGeomagneticStormClicked) }
    private val androidNetworkStatus: AndroidNetworkStatus by inject()

    init {
        lifecycleScope.launch {
            whenCreated {
                gstViewModel.load(androidNetworkStatus.isNetworkAvailable())
            }
            whenResumed {
                adapter.isNeededToLoadInFlow.collect { isNeededToLoad ->
                    if (isNeededToLoad && androidNetworkStatus.isNetworkAvailable()) {
                        gstViewModel.reload()
                    }
                }
            }
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return providePersistentView(inflater, container, savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (!hasInitializedRootView) {
            hasInitializedRootView = true
            initRecyclerView()
        }

        with(gstViewModel) {
            responseGeomagneticStorms().observe(viewLifecycleOwner) {
//                // ?????????????????? ???????????? (listSolarResponse) ?? ?????????????? ???? API
//                val listGSTResponse = it
//                // ?????????????? ?????????????????????????????? ????????????
//                val listGSTDisplay = mutableListOf<GeomagneticStorm>()
//                for (index in listGSTResponse.indices) {
//                    listGSTDisplay.add(
//                        GeomagneticStorm(
//                            gstID = listGSTResponse[index].startTime.toString(),
//                            startTime = listGSTResponse[index].startTime?.take(10),
//                            null,
//                            null,
//                            "header"
//                        )
//                    )
//                    listGSTResponse[index].allKpIndex?.forEach { eachFromAllKpIndex ->
//                        listGSTDisplay.add(
//                            GeomagneticStorm(
//                                gstID = listGSTResponse[index].startTime.toString(),
//                                startTime = eachFromAllKpIndex.observedTime?.substring(11, 16),
//                                allKpIndex = listOf(eachFromAllKpIndex),
//                                null,
//                                "small"
//                            )
//                        )
//                    }
//                }
                binding.shimmerViewContainerGst.stopShimmer()
                adapter.items = it
            }
            error().observe(viewLifecycleOwner) { showThrowable(it) }
        }
    }

    private fun initRecyclerView() {
        with(binding.rvListGst) {
            layoutManager = LinearLayoutManager(context)
            adapter = this@GSTListFragment.adapter
        }
    }

    override fun onResume() {
        super.onResume()
        binding.shimmerViewContainerGst.startShimmer()
    }


    override fun onPause() {
        super.onPause()
        binding.shimmerViewContainerGst.stopShimmer()
    }

    override fun onDestroy() {
        super.onDestroy()
        gstListFragmentScope.close()
    }
}