package com.project.astronomy.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.project.astronomy.databinding.FragmentMainBinding
import com.project.astronomy.di.SCOPE_MAIN_MODULE
import com.project.astronomy.viewmodel.MainViewModel
import com.project.astronomy.viewmodel.MainViewModelFactory
import com.project.core.viewmodel.SavedStateViewModelFactory
import org.koin.android.ext.android.getKoin
import org.koin.core.qualifier.named
import org.koin.core.scope.Scope

class MainFragment : Fragment() {
    private val scopeMainModule: Scope =
        getKoin().getOrCreateScope(SCOPE_MAIN_MODULE, named(SCOPE_MAIN_MODULE))

    private val mainViewModelFactory: MainViewModelFactory = scopeMainModule.get()
    private val mainViewModel: MainViewModel by viewModels {
        SavedStateViewModelFactory(mainViewModelFactory, this)
    }

    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMainBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onDestroy() {
        super.onDestroy()
        scopeMainModule.close()
        _binding = null
    }
}