package com.project.apod.ui

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.project.apod.databinding.ListApodFragmentBinding
import com.project.apod.di.SCOPE_APOD_LIST_MODULE
import com.project.apod.entities.APODResponse
import com.project.apod.viewmodel.APODViewModel
import com.project.apod.viewmodel.APODViewModelFactory
import com.project.core.ui.BaseFragment
import com.project.core.viewmodel.SavedStateViewModelFactory
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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        apodViewModel.getAPODFromDateToDate()
        initRecyclerView()
        apodViewModel.responseAPODFromDateToDate()
            .observe(viewLifecycleOwner) { adapter.submitData(it) }
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
            addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    super.onScrolled(recyclerView, dx, dy)
                    val layoutManager = recyclerView.layoutManager as LinearLayoutManager
                    val totalItemCount = layoutManager.itemCount
                    val lastVisible = layoutManager.findLastVisibleItemPosition()

                    val endHasBeenReached = lastVisible + 3 >= totalItemCount
                    if (totalItemCount > 0 && endHasBeenReached) {
                        apodViewModel.getAPODFromDateToDate()
                    }
                }
            })
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        apodListFragmentScope.close()
    }

    fun onItemClick(apodResponse: APODResponse) {
        val action = APODListFragmentDirections
            .actionFragmentApodToFragmentApodDescription(apodResponse)
        findNavController().navigate(action)
    }
}