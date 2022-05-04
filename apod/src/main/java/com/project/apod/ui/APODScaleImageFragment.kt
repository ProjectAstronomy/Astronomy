package com.project.apod.ui

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.project.apod.R
import com.project.apod.databinding.OneApodFragmentBinding
import com.project.apod.viewmodels.APODScaleImageViewModel
import com.project.core.ui.BaseFragment

class APODScaleImageFragment : BaseFragment<OneApodFragmentBinding>(OneApodFragmentBinding::inflate) {

    companion object {
        fun newInstance() = APODScaleImageFragment()
    }

    private lateinit var viewModel: APODScaleImageViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.scale_image_apon_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this).get(APODScaleImageViewModel::class.java)



    }

}