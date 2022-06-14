package com.project.apod.ui

import android.annotation.SuppressLint
import android.content.res.Resources
import android.os.Bundle
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.webkit.WebChromeClient
import androidx.annotation.ColorInt
import androidx.core.graphics.drawable.toBitmap
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

    @SuppressLint("SetJavaScriptEnabled")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        requestWritePermission()
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
                        binding.saveImageToExternalStorage.setOnClickListener {
                            if (isWritePermissionGranted)
                                saveImageToExternalStorage(
                                    binding.ivUrlApod.drawable.toBitmap(),
                                    "${apodResponse.title.toString()}.jpg"
                                )
                        }
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

    override fun onResume() {
        super.onResume()
        //sets specific status bar color because of no appbar animation in this fragment
        val typedValue = TypedValue()
        val theme: Resources.Theme = requireContext().theme
        theme.resolveAttribute(com.google.android.material.R.attr.colorPrimaryVariant, typedValue, true)
        @ColorInt val mColor = typedValue.data
        val window: Window = requireActivity().window
        context?.let { window.statusBarColor = mColor }
    }
}