package com.project.apod.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebChromeClient
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.project.apod.databinding.OneApodFragmentBinding
import com.project.core.ui.BaseFragment

class APODDescriptionFragment :
    BaseFragment<OneApodFragmentBinding>(OneApodFragmentBinding::inflate) {

    private val navArgs: APODDescriptionFragmentArgs by navArgs()

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
                "image" -> useCoilToLoadPhoto(ivUrlApod, apodResponse.url)
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