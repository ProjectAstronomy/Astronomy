package com.project.donki.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.project.core.ui.BaseFragment
import com.project.core.viewmodel.SavedStateViewModelFactory
import com.project.donki.databinding.FragmentListGstBinding
import com.project.donki.di.SCOPE_GST_MODULE
import com.project.donki.entities.GeomagneticStorm
import com.project.donki.viewmodels.GSTViewModel
import com.project.donki.viewmodels.GSTViewModelFactory
import kotlinx.coroutines.launch
import org.koin.android.ext.android.getKoin
import org.koin.core.qualifier.named

class GSTListFragment : BaseFragment<FragmentListGstBinding>(FragmentListGstBinding::inflate) {
    private val gstListFragmentScope =
        getKoin().getOrCreateScope(SCOPE_GST_MODULE, named(SCOPE_GST_MODULE))

    private val gstViewModelFactory: GSTViewModelFactory = gstListFragmentScope.get()
    private val gstViewModel: GSTViewModel by viewModels {
        SavedStateViewModelFactory(gstViewModelFactory, this)
    }

    private val adapterGST by lazy { GSTRecyclerViewAdapter() }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return providePersistentView(inflater, container, savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (!hasInitializedRootView) {
            hasInitializedRootView = true
            //TODO: init views
            gstViewModel.loadAsync()
        }

        binding.rvListGst.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        binding.rvListGst.adapter = adapterGST

//        lifecycleScope.launch {
//            adapter.isNeededToLoadInFlow.collect { isNeededToLoad ->
//                if (isNeededToLoad) gstViewModel.loadAsync()
//            }
//        }


        with(gstViewModel) {
            responseGeomagneticStorms().observe(viewLifecycleOwner) {
                // сохраняем массив (listSolarResponse) с данными из API
                val listGSTResponse = it
                // создаем вспомогательный массив
                var listGSTDisplay = mutableListOf<GeomagneticStorm>()
                for (index in listGSTResponse.indices) {
                    listGSTDisplay.add(
                        GeomagneticStorm(
                            null,
                            listGSTResponse[index].startTime?.take(10),
                            null,
                            null,
                            "header"
                        )
                    )
                    listGSTResponse[index].allKpIndex?.forEach {
                        listGSTDisplay.add(
                            GeomagneticStorm(
                                null,
                                listGSTResponse[index].startTime?.substring(11, 16),
                                null,
                                null,
                                it.kpIndex.toString()
                            )
                        )
                    }
                }
                adapterGST.adapterListGST = listGSTDisplay
            }
            error().observe(viewLifecycleOwner) {
                showThrowable(it)
            }
        }

    }

    override fun onDestroy() {
        super.onDestroy()
        gstListFragmentScope.close()
    }
}