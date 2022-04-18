package com.project.apod.ui

import android.os.Bundle
import com.project.apod.databinding.FragmentApodDescriptionBinding
import com.project.apod.entities.APODResponse
import com.project.core.ui.BaseFragment

class APODDescriptionFragment :
    BaseFragment<FragmentApodDescriptionBinding>(FragmentApodDescriptionBinding::inflate) {

    companion object {
        private const val APOD_RESPONSE_TAG = "apodResponse"
    }

    private lateinit var apodResponse: APODResponse

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let { apodResponse = it.getParcelable(APOD_RESPONSE_TAG)!! }
    }
}