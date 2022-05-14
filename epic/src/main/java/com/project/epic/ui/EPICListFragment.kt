package com.project.epic.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.project.core.ui.BaseFragment
import com.project.core.viewmodel.SavedStateViewModelFactory
import com.project.epic.databinding.FragmentListEpicBinding
import com.project.epic.di.SCOPE_EPIC_MODULE
import com.project.epic.entities.EPICResponse
import com.project.epic.viewmodels.EPICViewModel
import com.project.epic.viewmodels.EPICViewModelFactory
import org.koin.android.ext.android.getKoin
import org.koin.core.qualifier.named

class EPICListFragment : BaseFragment<FragmentListEpicBinding>(FragmentListEpicBinding::inflate) {
    private val epicFragmentScope =
        getKoin().getOrCreateScope(SCOPE_EPIC_MODULE, named(SCOPE_EPIC_MODULE))

    private val epicViewModelFactory: EPICViewModelFactory = epicFragmentScope.get()
    private val epicViewModel: EPICViewModel by viewModels {
        SavedStateViewModelFactory(epicViewModelFactory, this)
    }

    private val adapter by lazy { EPICRecyclerViewAdapter(::onItemClick, ::useCoilToLoadPhoto) }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return providePersistentView(inflater, container, savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (!hasInitializedRootView) {
            hasInitializedRootView = true
            initRecyclerView()
            epicViewModel.loadAsync()
        }

        with(epicViewModel) {
            responseEPIC().observe(viewLifecycleOwner) { adapter.items = it }
            error().observe(viewLifecycleOwner) { showThrowable(it) }
        }
    }

    private fun initRecyclerView() {

    }

    override fun onDestroy() {
        super.onDestroy()
        epicFragmentScope.close()
    }

    private fun onItemClick(epicResponse: EPICResponse) {
        val action = EPICListFragmentDirections
            .actionFragmentEpicToFragmentEpicDescription(epicResponse)
        findNavController().navigate(action)
    }
}