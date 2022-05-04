package com.project.apod.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.project.apod.databinding.ListApodFragmentBinding
import com.project.apod.di.SCOPE_APOD_LIST_MODULE
import com.project.apod.entities.APODResponse
import com.project.apod.viewmodels.APODViewModel
import com.project.apod.viewmodels.APODViewModelFactory
import com.project.core.ui.BaseFragment
import com.project.core.viewmodel.SavedStateViewModelFactory
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import org.koin.android.ext.android.getKoin
import org.koin.core.qualifier.named
import org.koin.core.scope.Scope

class APODListFragment :
    BaseFragment<ListApodFragmentBinding>(ListApodFragmentBinding::inflate) {

    private val apodListFragmentScope: Scope =
        getKoin().getOrCreateScope(SCOPE_APOD_LIST_MODULE, named(SCOPE_APOD_LIST_MODULE))

    private val apodViewModelFactory: APODViewModelFactory = apodListFragmentScope.get()
    private val apodViewModel: APODViewModel by viewModels {
        SavedStateViewModelFactory(apodViewModelFactory, this)
    }

    private val adapter by lazy { APODRecyclerViewAdapter(::onItemClick, ::useCoilToLoadPhoto) }

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
            initRecyclerView()
            apodViewModel.loadAsync(true)
            lifecycleScope.launch {
                adapter.isNeededToLoadInFlow.collect { isNeededToLoad ->
                    if (isNeededToLoad) {
                        apodViewModel.loadAsync(true)
                    }
                }
            }
        }
        with(apodViewModel) {
            responseAPODFromDateToDate().observe(viewLifecycleOwner) { adapter.items = it }
            error().observe(viewLifecycleOwner) { /* TODO: handle error here */ }
        }
    }

    private fun initRecyclerView() {
        with(binding.rvListApodVertical) {
            var scrollPosition = 0
            if (layoutManager != null) {
                scrollPosition =
                    (layoutManager as LinearLayoutManager).findFirstCompletelyVisibleItemPosition()
            }
            layoutManager = LinearLayoutManager(requireContext())
            scrollToPosition(scrollPosition)
            adapter = this@APODListFragment.adapter
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        apodListFragmentScope.close()
    }

    private fun onItemClick(apodResponse: APODResponse) {
        val action = APODListFragmentDirections
            .actionFragmentApodToFragmentApodDescription(apodResponse)
        findNavController().navigate(action)
    }
}