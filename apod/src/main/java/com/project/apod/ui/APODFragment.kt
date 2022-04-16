package com.project.apod.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.project.apod.databinding.FragmentApodBinding
import com.project.apod.di.SCOPE_APOD_MODULE
import com.project.apod.viewmodel.APODViewModel
import com.project.apod.viewmodel.APODViewModelFactory
import com.project.core.viewmodel.SavedStateViewModelFactory
import org.koin.android.ext.android.getKoin
import org.koin.core.qualifier.named

class APODFragment : Fragment() {
    private val scopeAPODModule = getKoin().getOrCreateScope(SCOPE_APOD_MODULE, named(SCOPE_APOD_MODULE))

    private val apodViewModelFactory: APODViewModelFactory = scopeAPODModule.get()
    private val apodViewModel: APODViewModel by viewModels {
        SavedStateViewModelFactory(apodViewModelFactory, this)
    }

    private var _binding: FragmentApodBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentApodBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onDestroy() {
        super.onDestroy()
        scopeAPODModule.close()
        _binding = null
    }
}