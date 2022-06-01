package com.project.donki.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.whenCreated
import androidx.lifecycle.whenResumed
import androidx.recyclerview.widget.LinearLayoutManager
import com.project.core.net.AndroidNetworkStatus
import com.project.core.ui.BaseFragment
import com.project.donki.databinding.FragmentListFlrBinding
import com.project.donki.di.SCOPE_FLR_MODULE
import com.project.donki.entities.remote.SolarFlare
import com.project.donki.ui.adapters.FLRRecyclerViewAdapter
import com.project.donki.viewmodels.FLRViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import org.koin.android.ext.android.getKoin
import org.koin.android.ext.android.inject
import org.koin.core.parameter.parametersOf
import org.koin.core.qualifier.named
import org.koin.core.scope.Scope

class FLRListFragment : BaseFragment<FragmentListFlrBinding>(FragmentListFlrBinding::inflate) {
    private val flrListFragmentScope: Scope =
        getKoin().getOrCreateScope(SCOPE_FLR_MODULE, named(SCOPE_FLR_MODULE))

    private val flrViewModel: FLRViewModel by flrListFragmentScope.inject {
        parametersOf(SavedStateHandle())
    }

    private val onSolarFlareClicked: (SolarFlare) -> Unit = { flrViewModel.insert(it) }
    private val adapterSolarVertical by lazy { FLRRecyclerViewAdapter(onSolarFlareClicked) }
    private val androidNetworkStatus: AndroidNetworkStatus by inject()

    init {
        lifecycleScope.launch {
            whenCreated {
                flrViewModel.load(androidNetworkStatus.isNetworkAvailable())
            }
            whenResumed {
                adapterSolarVertical.isNeededToLoadInFlow.collect { isNeededToLoad ->
                    if (isNeededToLoad && androidNetworkStatus.isNetworkAvailable()) {
                        flrViewModel.reload()
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

        with(flrViewModel) {
            responseSolarFlare().observe(viewLifecycleOwner) { adapterSolarVertical.items = it }
            error().observe(viewLifecycleOwner) { showThrowable(it) }
        }
    }

    private fun initRecyclerView() {
        with(binding.rvListSolar) {
            layoutManager = LinearLayoutManager(context)
            adapter = this@FLRListFragment.adapterSolarVertical
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        flrListFragmentScope.close()
    }
}