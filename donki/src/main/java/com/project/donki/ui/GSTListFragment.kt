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
import com.project.donki.databinding.FragmentListGstBinding
import com.project.donki.di.SCOPE_GST_MODULE
import com.project.donki.viewmodels.GSTViewModel
import com.project.donki.viewmodels.GSTViewModelFactory
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import org.koin.android.ext.android.getKoin
import org.koin.android.ext.android.inject
import org.koin.core.qualifier.named

class GSTListFragment : BaseFragment<FragmentListGstBinding>(FragmentListGstBinding::inflate) {
    private val gstListFragmentScope = getKoin().getOrCreateScope(SCOPE_GST_MODULE, named(SCOPE_GST_MODULE))

    private val gstViewModelFactory: GSTViewModelFactory = gstListFragmentScope.get()
    private val gstViewModel: GSTViewModel by viewModels {
        SavedStateViewModelFactory(gstViewModelFactory, this)
    }

    private val adapter by lazy { GSTRecyclerViewAdapter() }
    private val androidNetworkStatus: AndroidNetworkStatus by inject()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return providePersistentView(inflater, container, savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (!hasInitializedRootView) {
            hasInitializedRootView = true
            //TODO: init views
            gstViewModel.load(androidNetworkStatus.isNetworkAvailable())
        }
        lifecycleScope.launch {
            adapter.isNeededToLoadInFlow.collect { isNeededToLoad ->
                if (isNeededToLoad) gstViewModel.reload()
            }
        }
        with(gstViewModel) {
            responseGeomagneticStorms().observe(viewLifecycleOwner) { adapter.items = it }
            error().observe(viewLifecycleOwner) { /* TODO: handle error here */ }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        gstListFragmentScope.close()
    }
}