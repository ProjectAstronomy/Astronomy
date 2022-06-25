package com.project.apod.ui

import android.content.res.Resources
import android.graphics.Color
import android.os.Bundle
import android.util.TypedValue
import android.view.*
import android.widget.ImageView
import androidx.annotation.ColorInt
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.whenResumed
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import coil.ImageLoader
import coil.request.ImageRequest
import com.google.android.material.snackbar.Snackbar
import com.project.apod.databinding.ListApodFragmentBinding
import com.project.apod.di.SCOPE_APOD_LIST_MODULE
import com.project.apod.entities.remote.APODResponse
import com.project.apod.viewmodels.APODViewModel
import com.project.apod.viewmodels.APODViewModelFactory
import com.project.core.R
import com.project.core.net.AndroidNetworkStatus
import com.project.core.viewmodel.SavedStateViewModelFactory
import com.project.core.viewmodel.SettingsViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import org.koin.android.ext.android.getKoin
import org.koin.android.ext.android.inject
import org.koin.core.qualifier.named
import org.koin.core.scope.Scope

class APODListFragment : Fragment() {
    private val apodListFragmentScope: Scope =
        getKoin().getOrCreateScope(SCOPE_APOD_LIST_MODULE, named(SCOPE_APOD_LIST_MODULE))

    private val androidNetworkStatus: AndroidNetworkStatus by inject()
    private val settingsViewModel: SettingsViewModel by activityViewModels()
    private val apodViewModelFactory: APODViewModelFactory = apodListFragmentScope.get()
    private val apodViewModel: APODViewModel by viewModels {
        SavedStateViewModelFactory(apodViewModelFactory, this)
    }

    private var binding: ListApodFragmentBinding? = null

    private val adapter by lazy {
        APODRecyclerViewAdapter(::onItemClick, ::useCoilToLoadPhoto) { _, _ ->
            binding?.shimmerViewContainer?.stopShimmer()
            binding?.shimmerViewContainer?.visibility = View.GONE
            apodViewModel.onLoadingFinished()
        }
    }

    init {
        lifecycleScope.launch {
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
        binding = ListApodFragmentBinding.inflate(inflater, container, false)
        initRecyclerView()
        return binding?.root
    }

    override fun onResume() {
        super.onResume()
        binding?.shimmerViewContainer?.startShimmer()
        //returning transparent status bar background color
        val window: Window = requireActivity().window
        window.statusBarColor = Color.parseColor("#00000000")
    }

    override fun onPause() {
        super.onPause()
        binding?.shimmerViewContainer?.stopShimmer()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //sets specific status bar color because of no appbar animation in this fragment
        val typedValue = TypedValue()
        val theme: Resources.Theme = requireContext().theme
        theme.resolveAttribute(com.google.android.material.R.attr.colorPrimaryVariant, typedValue, true)
        @ColorInt val mColor = typedValue.data
        val window: Window = requireActivity().window
        context?.let { window.statusBarColor = mColor }
        with(apodViewModel) {
            isOnceCreated().observe(viewLifecycleOwner) { isOnceCreated ->
                if (isOnceCreated) {
                    binding?.shimmerViewContainer?.stopShimmer()
                    binding?.shimmerViewContainer?.visibility = View.GONE
                }
            }
            responseAPODFromDateToDate().observe(viewLifecycleOwner) { list ->
                if (list.isNotEmpty()) {
                    adapter.items = list
                } else {
                    binding?.root?.let { Snackbar.make(it, "No data received", Snackbar.LENGTH_LONG).show() }
                }
            }
            error().observe(viewLifecycleOwner) { exception ->
                binding?.root?.let { Snackbar.make(it, exception.message.toString(), Snackbar.LENGTH_LONG).show() }
            }
        }
        settingsViewModel.imageResolution.observe(viewLifecycleOwner) {
            adapter.onImageResolutionChanged(it)
        }

    }

    private fun initRecyclerView() {
        binding?.rvListApodVertical?.layoutManager = LinearLayoutManager(requireContext())
        binding?.rvListApodVertical?.adapter = adapter
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

    private fun useCoilToLoadPhoto(imageView: ImageView, imageLink: String?) {
        context?.let { _context ->
            val request = ImageRequest.Builder(_context)
                .data(imageLink)
                .target(
                    onStart = {},
                    onSuccess = { drawable -> imageView.setImageDrawable(drawable) },
                    onError = { imageView.setImageResource(R.drawable.no_image) }
                )
                .crossfade(true)
                .build()
            ImageLoader(_context).enqueue(request)
        }
    }
}