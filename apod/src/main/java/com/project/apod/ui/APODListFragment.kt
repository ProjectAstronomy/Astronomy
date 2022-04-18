package com.project.apod.ui

import androidx.fragment.app.viewModels
import com.project.apod.databinding.FragmentApodListBinding
import com.project.apod.di.SCOPE_APOD_LIST_MODULE
import com.project.apod.viewmodel.APODViewModel
import com.project.apod.viewmodel.APODViewModelFactory
import com.project.core.ui.BaseFragment
import com.project.core.viewmodel.SavedStateViewModelFactory
import org.koin.android.ext.android.getKoin
import org.koin.core.qualifier.named
import org.koin.core.scope.Scope

class APODListFragment :
    BaseFragment<FragmentApodListBinding>(FragmentApodListBinding::inflate) {

    private val apodListFragmentScope: Scope =
        getKoin().getOrCreateScope(SCOPE_APOD_LIST_MODULE, named(SCOPE_APOD_LIST_MODULE))

    private val apodViewModelFactory: APODViewModelFactory = apodListFragmentScope.get()
    private val apodViewModel: APODViewModel by viewModels {
        SavedStateViewModelFactory(apodViewModelFactory, this)
    }

    override fun onDestroy() {
        super.onDestroy()
        apodListFragmentScope.close()
    }
}