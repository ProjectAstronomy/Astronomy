package com.project.apod.ui

import android.content.res.Resources
import android.graphics.Color
import android.os.Bundle
import android.util.TypedValue
import android.view.*
import androidx.annotation.ColorInt
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.whenCreated
import androidx.lifecycle.whenResumed
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.project.apod.databinding.ListApodFragmentBinding
import com.project.apod.di.SCOPE_APOD_LIST_MODULE
import com.project.apod.entities.remote.APODResponse
import com.project.apod.viewmodels.APODViewModel
import com.project.core.net.AndroidNetworkStatus
import com.project.core.ui.BaseFragment
import com.project.core.viewmodel.SettingsViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import org.koin.android.ext.android.getKoin
import org.koin.android.ext.android.inject
import org.koin.core.parameter.parametersOf
import org.koin.core.qualifier.named
import org.koin.core.scope.Scope


class APODListFragment : BaseFragment<ListApodFragmentBinding>(ListApodFragmentBinding::inflate) {
    private val apodListFragmentScope: Scope =
        getKoin().getOrCreateScope(SCOPE_APOD_LIST_MODULE, named(SCOPE_APOD_LIST_MODULE))

    private val androidNetworkStatus: AndroidNetworkStatus by inject()
    private val settingsViewModel: SettingsViewModel by activityViewModels()
    private val apodViewModel: APODViewModel by apodListFragmentScope.inject {
        parametersOf(SavedStateHandle())
    }

    private val adapter by lazy {
        APODRecyclerViewAdapter(::onItemClick, ::useCoilToLoadPhoto) { _, _ ->
            with(binding.shimmerViewContainer) {
                stopShimmer()
                visibility = View.GONE
            }
        }
    }

    init {
        lifecycleScope.launch {
            whenCreated {
                apodViewModel.load(androidNetworkStatus.isNetworkAvailable())
            }
            whenResumed {
                adapter.isNeededToLoadInFlow.collect { isNeededToLoad ->
                    if (isNeededToLoad && androidNetworkStatus.isNetworkAvailable()) {
                        apodViewModel.reload()
                    }
                }
            }
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return providePersistentView(inflater, container, savedInstanceState)
    }

    override fun onResume() {
        super.onResume()
        binding.shimmerViewContainer.startShimmer()
        //returning transparent status bar background color
        val window: Window = requireActivity().window
        window.setStatusBarColor(Color.parseColor("#00000000"))
    }

    override fun onPause() {
        super.onPause()
        binding.shimmerViewContainer.stopShimmer()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //sets specific status bar color because of no appbar animation in this fragment
        val typedValue = TypedValue()
        val theme: Resources.Theme = requireContext().theme
        theme.resolveAttribute(com.google.android.material.R.attr.colorPrimaryVariant, typedValue, true)
        @ColorInt val mColor = typedValue.data
        val window: Window = requireActivity().window
        context?.let { window.setStatusBarColor(mColor) }

        if (!hasInitializedRootView) {
            hasInitializedRootView = true
            initRecyclerView()
        }
        with(apodViewModel) {
            responseAPODFromDateToDate().observe(viewLifecycleOwner) { list ->
                if (list.isNotEmpty()) {
                    adapter.items = list
                } else {
                    Snackbar.make(binding.root, "No data received", Snackbar.LENGTH_LONG).show()
                }
            }
            error().observe(viewLifecycleOwner) { exception ->
                Snackbar.make(binding.root, exception.message.toString(), Snackbar.LENGTH_LONG).show()
            }
        }
        settingsViewModel.imageResolution.observe(viewLifecycleOwner) {
            adapter.onImageResolutionChanged(it)
        }

    }

    private fun initRecyclerView() {
        with(binding.rvListApodVertical) {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = this@APODListFragment.adapter
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        apodListFragmentScope.close()
    }

    private fun onItemClick(apodResponse: APODResponse) {
        apodViewModel.insert(apodResponse)
        val action = APODListFragmentDirections
            .actionFragmentApodToFragmentApodDescription(apodResponse)
        findNavController().navigate(action)
    }
}