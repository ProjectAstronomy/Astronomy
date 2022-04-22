package com.project.donki.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.project.core.ui.BaseFragment
import com.project.core.viewmodel.SavedStateViewModelFactory
import com.project.donki.databinding.ListSolarFlareBinding
import com.project.donki.di.SCOPE_SOLAR_FLARE_MODULE
import com.project.donki.viewmodel.SolarFlareViewModel
import com.project.donki.viewmodel.SolarFlareViewModelFactory
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import org.koin.android.ext.android.getKoin
import org.koin.core.qualifier.named
import org.koin.core.scope.Scope

class SolarFlareListFragment : BaseFragment<ListSolarFlareBinding>(ListSolarFlareBinding::inflate) {
    private val solarFlareListFragmentScope: Scope =
        getKoin().getOrCreateScope(SCOPE_SOLAR_FLARE_MODULE, named(SCOPE_SOLAR_FLARE_MODULE))

    private val solarFlareViewModelFactory: SolarFlareViewModelFactory =
        solarFlareListFragmentScope.get()
    private val solarFlareViewModel: SolarFlareViewModel by viewModels {
        SavedStateViewModelFactory(solarFlareViewModelFactory, this)
    }

    private val adapter by lazy { SolarFlareRecyclerViewAdapter() }

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
            solarFlareViewModel.loadAsync()
        }
        lifecycleScope.launch {
            adapter.isNeededToLoadInFlow.collect { isNeededToLoad ->
                if (isNeededToLoad) solarFlareViewModel.loadAsync()
            }
        }
        with(solarFlareViewModel) {
            responseSolarFlareFromDateToDate().observe(viewLifecycleOwner) { adapter.submitData(it) }
            error().observe(viewLifecycleOwner) { showThrowable(it) }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        solarFlareListFragmentScope.close()
    }
}