package com.project.donki.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.project.core.net.AndroidNetworkStatus
import com.project.core.ui.BaseFragment
import com.project.core.viewmodel.SavedStateViewModelFactory
import com.project.donki.databinding.FragmentListFlrBinding
import com.project.donki.di.SCOPE_FLR_MODULE
import com.project.donki.entities.remote.SolarFlare
import com.project.donki.ui.adapters.FLRRecyclerViewAdapter
import com.project.donki.viewmodels.FLRViewModel
import com.project.donki.viewmodels.factories.FLRViewModelFactory
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

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return providePersistentView(inflater, container, savedInstanceState)
    }

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

        binding.rvListSolar.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        binding.rvListSolar.adapter = adapterSolarVertical

        with(flrViewModel) {
            responseSolarFlare().observe(viewLifecycleOwner) {
                adapterSolarVertical.items = it
            }
            error().observe(viewLifecycleOwner) { showThrowable(it) }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        flrListFragmentScope.close()
    }
}