package com.project.astronomy.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.project.astronomy.R
import com.project.astronomy.databinding.MainFragmentBinding
import com.project.astronomy.di.SCOPE_MAIN_MODULE
import com.project.astronomy.viewmodel.MainViewModel
import com.project.astronomy.viewmodel.MainViewModelFactory
import com.project.core.ui.BaseFragment
import com.project.core.viewmodel.SavedStateViewModelFactory
import org.koin.android.ext.android.getKoin
import org.koin.core.qualifier.named
import org.koin.core.scope.Scope

class MainFragment : BaseFragment<MainFragmentBinding>(MainFragmentBinding::inflate) {
    private val scopeMainModule: Scope =
        getKoin().getOrCreateScope(SCOPE_MAIN_MODULE, named(SCOPE_MAIN_MODULE))

    private val mainViewModelFactory: MainViewModelFactory = scopeMainModule.get()
    private val mainViewModel: MainViewModel by viewModels {
        SavedStateViewModelFactory(mainViewModelFactory, this)
    }

    private val adapterAPOD by lazy {
        RvAdapterCommon().apply {
            myListener = object : MyOnClickListener {
                override fun onMyClicked(view: View) {
                    findNavController().navigate(R.id.action_main_fragment_to_navigation_apod)
                }
            }
        }
    }
    private val adapterSolar by lazy { RvAdapterCommon() }
    private val adapterGeo by lazy { RvAdapterCommon() }
    private val adapterEPIC by lazy { RvAdapterCommon() }
    private val adapterMars by lazy { RvAdapterCommon() }

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
            with(binding.rvApod) {
                layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
                adapter = adapterAPOD
            }
            with(binding.rvSolar) {
                layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
                adapter = adapterSolar
            }
            with(binding.rvGeo) {
                layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
                adapter = adapterGeo
            }
            with(binding.rvEpic) {
                layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
                adapter = adapterEPIC
            }
            with(binding.rvMars) {
                layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
                adapter = adapterMars
            }
        }
        with(mainViewModel) {
            //getAPODByDate()
            liveDataAPOD.observe(viewLifecycleOwner) { adapterAPOD.adapterList = it }
            liveDataEpic.observe(viewLifecycleOwner) { adapterEPIC.adapterList = it }
            liveDataGeo.observe(viewLifecycleOwner) { adapterGeo.adapterList = it }
            liveDataMars.observe(viewLifecycleOwner) { adapterGeo.adapterList = it }
            liveDataSolar.observe(viewLifecycleOwner) { adapterSolar.adapterList = it }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        scopeMainModule.close()
    }
}