package com.project.apod.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebChromeClient
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.project.apod.databinding.OneApodFragmentBinding
import com.project.core.entities.ImageResolution
import com.project.core.ui.BaseFragment
import com.project.core.viewmodel.SettingsViewModel

class APODDescriptionFragment :
    BaseFragment<OneApodFragmentBinding>(OneApodFragmentBinding::inflate) {

    internal val navArgs: APODDescriptionFragmentArgs by navArgs()
    private val settingsViewModel: SettingsViewModel by activityViewModels()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return providePersistentView(inflater, container, savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        hasInitializedRootView = !hasInitializedRootView
        val apodResponse = navArgs.apodResponse
        with(binding) {
            tvTitleApod.text = apodResponse.title
            tvDateApod.text = apodResponse.date
            when (apodResponse.mediaType) {
                "image" -> {
                    settingsViewModel.imageResolution.observe(viewLifecycleOwner) { imageResolution ->
                        val resolution = when (imageResolution) {
                            ImageResolution.REGULAR -> apodResponse.url
                            ImageResolution.HD -> apodResponse.hdurl
                        }
                        useCoilToLoadPhoto(ivUrlApod, resolution)
                    }
                }
                "video" -> {
                    binding.ivUrlApod.visibility = View.GONE
                    with(binding.wvOneUrlVideoApod) {
                        visibility = View.VISIBLE
                        settings.javaScriptEnabled = true
                        settings.pluginState = android.webkit.WebSettings.PluginState.ON
                        loadUrl(apodResponse.url + "&fs=0&loop=1&modestbranding=1&autoplay=1&mute=1")
                        webChromeClient = WebChromeClient()
                    }
                }
            }
            tvExplanationApod.text = apodResponse.explanation
            tvCopyrightApod.text = apodResponse.copyright
        }
        binding.ivUrlApod.setOnClickListener {
            val action = APODDescriptionFragmentDirections
                .actionFragmentApodDescriptionToAPODScaleImageFragment(apodResponse)
            findNavController().navigate(action)
        }
    }
}