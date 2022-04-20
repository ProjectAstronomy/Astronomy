package com.project.apod.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.project.apod.databinding.OneApodFragmentBinding
import com.project.apod.entities.APODResponse
import com.project.core.ui.BaseFragment

class APODDescriptionFragment :
    BaseFragment<OneApodFragmentBinding>(OneApodFragmentBinding::inflate) {

    companion object {
        private const val APOD_RESPONSE_TAG = "apodResponse"
    }

    private lateinit var apodResponse: APODResponse

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let { apodResponse = it.getParcelable(APOD_RESPONSE_TAG)!! }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return providePersistentView(inflater, container, savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        hasInitializedRootView = !hasInitializedRootView
        with(binding) {
            tvTitleApod.text = apodResponse.title
            tvDateApod.text = apodResponse.date
            useCoilToLoadPhoto(ivUrlApod, apodResponse.url)
            tvExplanationApod.text = apodResponse.explanation
            tvCopyrightApod.text = apodResponse.copyright
        }
    }
}