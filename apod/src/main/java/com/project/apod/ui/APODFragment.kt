package com.project.apod.ui

import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.project.apod.di.SCOPE_APOD_MODULE
import com.project.apod.viewmodel.APODViewModel
import com.project.apod.viewmodel.APODViewModelFactory
import com.project.core.viewmodel.SavedStateViewModelFactory
import org.koin.android.ext.android.getKoin
import org.koin.core.qualifier.named

class APODFragment : Fragment() {
    private val scopeAPODFragment = getKoin().getOrCreateScope(SCOPE_APOD_MODULE, named(SCOPE_APOD_MODULE))

    private val apodViewModelFactory: APODViewModelFactory = scopeAPODFragment.get()
    private val apodViewModel: APODViewModel by viewModels {
        SavedStateViewModelFactory(apodViewModelFactory, this)
    }

    override fun onDestroy() {
        super.onDestroy()
        scopeAPODFragment.close()
    }
}