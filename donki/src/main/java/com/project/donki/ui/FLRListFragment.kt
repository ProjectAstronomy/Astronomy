package com.project.donki.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.project.core.net.AndroidNetworkStatus
import com.project.core.ui.BaseFragment
import com.project.core.viewmodel.SavedStateViewModelFactory
import com.project.donki.databinding.FragmentListFlrBinding
import com.project.donki.di.SCOPE_FLR_MODULE
import com.project.donki.viewmodels.FLRViewModel
import com.project.donki.viewmodels.FLRViewModelFactory
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import org.koin.android.ext.android.getKoin
import org.koin.android.ext.android.inject
import org.koin.core.qualifier.named
import org.koin.core.scope.Scope

class FLRListFragment : BaseFragment<FragmentListFlrBinding>(FragmentListFlrBinding::inflate) {
    private val flrListFragmentScope: Scope = getKoin().getOrCreateScope(SCOPE_FLR_MODULE, named(SCOPE_FLR_MODULE))

    private val flrViewModelFactory: FLRViewModelFactory = flrListFragmentScope.get()
    private val flrViewModel: FLRViewModel by viewModels {
        SavedStateViewModelFactory(flrViewModelFactory, this)
    }

    private val adapter by lazy { FLRRecyclerViewAdapter() }
    private val androidNetworkStatus: AndroidNetworkStatus by inject()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return providePersistentView(inflater, container, savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (!hasInitializedRootView) {
            hasInitializedRootView = true
            //TODO: init views
            flrViewModel.load(androidNetworkStatus.isNetworkAvailable())
        }
        lifecycleScope.launch {
            adapter.isNeededToLoadInFlow.collect { isNeededToLoad ->
                if (isNeededToLoad && androidNetworkStatus.isNetworkAvailable()) {
                    flrViewModel.reload()
                }
            }
        }
        with(flrViewModel) {
            responseSolarFlare().observe(viewLifecycleOwner) { list ->
                if (list.isNotEmpty()) {
                    adapter.items = list
                } else {
                    //TODO: inform user list is empty
                }
            }
            error().observe(viewLifecycleOwner) { /* TODO: handle error here */ }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        flrListFragmentScope.close()
    }
}