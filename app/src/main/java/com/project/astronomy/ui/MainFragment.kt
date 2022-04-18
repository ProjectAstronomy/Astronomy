package com.project.astronomy.ui

import androidx.fragment.app.viewModels
import com.project.astronomy.databinding.FragmentMainBinding
import com.project.astronomy.di.SCOPE_MAIN_MODULE
import com.project.astronomy.viewmodel.MainViewModel
import com.project.astronomy.viewmodel.MainViewModelFactory
import com.project.core.ui.BaseFragment
import com.project.core.viewmodel.SavedStateViewModelFactory
import org.koin.android.ext.android.getKoin
import org.koin.core.qualifier.named
import org.koin.core.scope.Scope

class MainFragment : BaseFragment<FragmentMainBinding>(FragmentMainBinding::inflate) {
    private val scopeMainModule: Scope =
        getKoin().getOrCreateScope(SCOPE_MAIN_MODULE, named(SCOPE_MAIN_MODULE))

    private val mainViewModelFactory: MainViewModelFactory = scopeMainModule.get()
    private val mainViewModel: MainViewModel by viewModels {
        SavedStateViewModelFactory(mainViewModelFactory, this)
    }

    override fun onDestroy() {
        super.onDestroy()
        scopeMainModule.close()
    }
}