package com.project.epic.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.project.epic.entities.EPICResponse
import com.project.core.ui.BaseFragment
import com.project.epic.databinding.FragmentEpicDescriptionBinding

class EPICDescriptionFragment :
    BaseFragment<FragmentEpicDescriptionBinding>(FragmentEpicDescriptionBinding::inflate) {

    companion object {
        private const val EPIC_RESPONSE_TAG = "epicResponse"
    }

    private lateinit var epicResponse: EPICResponse

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let { epicResponse = it.getParcelable(EPIC_RESPONSE_TAG)!! }
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
            //TODO: bind epicResponse to fragment_epic_description.xml
        }
    }
}