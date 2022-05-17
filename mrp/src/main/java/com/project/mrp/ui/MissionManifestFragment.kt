package com.project.mrp.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.project.core.ui.BaseFragment
import com.project.core.viewmodel.SavedStateViewModelFactory
import com.project.mrp.databinding.FragmentMissionManifestBinding
import com.project.mrp.di.SCOPE_MISSION_MANIFEST_MODULE
import com.project.mrp.entities.MissionManifest
import com.project.mrp.viewmodel.MissionManifestViewModel
import com.project.mrp.viewmodel.MissionManifestViewModelFactory
import org.koin.android.ext.android.getKoin
import org.koin.core.qualifier.named

class MissionManifestFragment :
    BaseFragment<FragmentMissionManifestBinding>(FragmentMissionManifestBinding::inflate) {

    private val missionFragmentScope =
        getKoin().getOrCreateScope(SCOPE_MISSION_MANIFEST_MODULE, named(
            SCOPE_MISSION_MANIFEST_MODULE))

    private val missionViewModelFactory: MissionManifestViewModelFactory =
        missionFragmentScope.get()
    private val missionManifestViewModel: MissionManifestViewModel by viewModels {
        SavedStateViewModelFactory(missionViewModelFactory, this)
    }

    private val adapterMission by lazy { MussionRecyclerViewAdapter() }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        return providePersistentView(inflater, container, savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (!hasInitializedRootView) {
            hasInitializedRootView = true
            initRecyclerView()
            missionManifestViewModel.loadAsync("curiosity")
        }

        with(missionManifestViewModel) {
            responseMission().observe(viewLifecycleOwner) {
                //adapterMission.items = it
                binding.apply {
                    name.text = "Меня зовут: \n" + it.photoManifest?.name
                    launchDate.text = "Дата Запуска: \n" + it.photoManifest?.launchDate
                    landingDate.text = "Приземлился: \n" + it.photoManifest?.landingDate
                    totalPhotos.text =
                        "Количество фотографий: \n" + it.photoManifest?.totalPhotos
                    status.text = "Статус мисии: \n" + it.photoManifest?.status
                    maxSol.text = "Последний сол: \n" + it.photoManifest?.maxSol
                    maxDate.text = "Последняя дата Земли: \n" + it.photoManifest?.maxDate
                }
            }
            error().observe(viewLifecycleOwner) { showThrowable(it) }
        }
    }

    private fun initRecyclerView() {
        with(binding.photos) {
            LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            adapter = this@MissionManifestFragment.adapterMission
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        missionFragmentScope.close()
    }
}