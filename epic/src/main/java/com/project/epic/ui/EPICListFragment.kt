package com.project.epic.ui

import android.content.res.Resources
import android.graphics.Color
import android.os.Bundle
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import androidx.annotation.ColorInt
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.whenCreated
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.facebook.shimmer.ShimmerFrameLayout
import com.project.core.entities.ImageResolution
import com.project.core.net.AndroidNetworkStatus
import com.project.core.ui.BaseFragment
import com.project.core.viewmodel.SavedStateViewModelFactory
import com.project.core.viewmodel.SettingsViewModel
import com.project.epic.databinding.FragmentListEpicBinding
import com.project.epic.di.SCOPE_EPIC_MODULE
import com.project.epic.entities.remote.EPICResponse
import com.project.epic.viewmodels.EPICViewModel
import com.project.epic.viewmodels.EPICViewModelFactory
import kotlinx.coroutines.launch
import org.koin.android.ext.android.getKoin
import org.koin.android.ext.android.inject
import org.koin.core.qualifier.named

class EPICListFragment : BaseFragment<FragmentListEpicBinding>(FragmentListEpicBinding::inflate) {
    private val epicFragmentScope =
        getKoin().getOrCreateScope(SCOPE_EPIC_MODULE, named(SCOPE_EPIC_MODULE))

    private var mShimmerViewContainer: ShimmerFrameLayout? = null

    private val epicViewModelFactory: EPICViewModelFactory = epicFragmentScope.get()
    private val epicViewModel: EPICViewModel by viewModels {
        SavedStateViewModelFactory(epicViewModelFactory, this)
    }

    private val settingsViewModel by activityViewModels<SettingsViewModel>()
    private var imageResolution = ImageResolution.REGULAR
    private val adapterEpic by lazy { EPICRecyclerViewAdapter(::onItemClick, ::useCoilToLoadPhoto) }
    private val androidNetworkStatus: AndroidNetworkStatus by inject()

    init {
        lifecycleScope.launch {
            whenCreated {
                epicViewModel.loadAsync(
                    androidNetworkStatus.isNetworkAvailable(),
                    imageResolution.resolution
                )
            }
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return providePersistentView(inflater, container, savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //returning transparent status bar background color
        val window: Window = requireActivity().window
        window.setStatusBarColor(Color.parseColor("#00000000"))

        if (!hasInitializedRootView) {
            hasInitializedRootView = true
            mShimmerViewContainer = binding.shimmerEpic
            initRecyclerView()
        }

        with(epicViewModel) {
            responseEPIC().observe(viewLifecycleOwner) {
                adapterEpic.items = it
                mShimmerViewContainer?.stopShimmer()
                mShimmerViewContainer?.visibility = View.GONE
            }
            error().observe(viewLifecycleOwner) { showThrowable(it) }
        }
        settingsViewModel.imageResolution.observe(viewLifecycleOwner) { imageResolution = it }
    }

    private fun initRecyclerView() {
        with(binding.rvListEpicVertical) {
            layoutManager = LinearLayoutManager(activity)
            adapter = this@EPICListFragment.adapterEpic
        }
    }

    override fun onResume() {
        super.onResume()
        mShimmerViewContainer?.startShimmer()
        //returning transparent status bar background color
        val window: Window = requireActivity().window
        window.setStatusBarColor(Color.parseColor("#00000000"))
    }

    override fun onPause() {
        super.onPause()
        mShimmerViewContainer?.startShimmer()
    }

    override fun onDestroy() {
        super.onDestroy()
        epicFragmentScope.close()
    }

    private fun onItemClick(epicResponse: EPICResponse) {
        epicViewModel.insert(epicResponse)
        val action = EPICListFragmentDirections
            .actionFragmentEpicToFragmentEpicDescription(epicResponse)
        findNavController().navigate(action)
    }
}