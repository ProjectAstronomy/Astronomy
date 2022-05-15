package com.project.donki.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.project.core.ui.BaseFragment
import com.project.core.viewmodel.SavedStateViewModelFactory
import com.project.donki.databinding.FragmentListGstBinding
import com.project.donki.di.SCOPE_GST_MODULE
import com.project.donki.domain.GSTRepositoryFake
import com.project.donki.entities.GeomagneticStorm
import com.project.donki.entities.SolarFlare
import com.project.donki.viewmodels.GSTViewModel
import com.project.donki.viewmodels.GSTViewModelFactory
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import org.koin.android.ext.android.getKoin
import org.koin.core.qualifier.named

class GSTListFragment : BaseFragment<FragmentListGstBinding>(FragmentListGstBinding::inflate) {
    private val gstListFragmentScope = getKoin().getOrCreateScope(SCOPE_GST_MODULE, named(SCOPE_GST_MODULE))

    private val gstViewModelFactory: GSTViewModelFactory = gstListFragmentScope.get()
    private val gstViewModel: GSTViewModel by viewModels {
        SavedStateViewModelFactory(gstViewModelFactory, this)
    }

    private val adapterGST by lazy { GSTRecyclerViewAdapter() }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return providePersistentView(inflater, container, savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (!hasInitializedRootView) {
            hasInitializedRootView = true
            //TODO: init views
            gstViewModel.loadAsync()
        }

        binding.rvListGst.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        binding.rvListGst.adapter = adapterGST

//        lifecycleScope.launch {
//            adapter.isNeededToLoadInFlow.collect { isNeededToLoad ->
//                if (isNeededToLoad) gstViewModel.loadAsync()
//            }
//        }
        println("------------------222_______________")

//        var tempList = mutableListOf<GeomagneticStorm>()
//        tempList.add(GeomagneticStorm("gstID","startTime",null,null,"link"))
//        tempList.add(GeomagneticStorm("gstID","startTime",null,null,"link"))


        //adapterGST.adapterListGST = tempList


        with(gstViewModel) {
            responseGeomagneticStorms().observe(viewLifecycleOwner) {
                println("------------------333_______________")
                adapterGST.adapterListGST = it

            }
            error().observe(viewLifecycleOwner) {
                showThrowable(it)
                println("------------------555_______________")
            }
        }

    }

    override fun onDestroy() {
        super.onDestroy()
        gstListFragmentScope.close()
    }
}