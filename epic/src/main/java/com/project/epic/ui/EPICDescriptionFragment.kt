package com.project.epic.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import com.project.core.ui.BaseFragment
import com.project.epic.databinding.FragmentEpicDescriptionBinding

class EPICDescriptionFragment :
    BaseFragment<FragmentEpicDescriptionBinding>(FragmentEpicDescriptionBinding::inflate) {

    private val navArgs: EPICDescriptionFragmentArgs by navArgs()

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
        val epicResponse = navArgs.epicResponse
        with(binding) {
            //TODO: bind epicResponse to fragment_epic_description.xml
        }
    }
}