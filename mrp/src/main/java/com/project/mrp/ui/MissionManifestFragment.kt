package com.project.mrp.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import com.project.core.ui.BaseFragment
import com.project.mrp.databinding.FragmentMissionManifestBinding

class MissionManifestFragment : BaseFragment<FragmentMissionManifestBinding>(FragmentMissionManifestBinding::inflate) {
    private val navArgs: MissionManifestFragmentArgs by navArgs()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return providePersistentView(inflater, container, savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (!hasInitializedRootView) {
            hasInitializedRootView = true
            //TODO: init views here
        }
        val roverName = navArgs.roverName
    }
}