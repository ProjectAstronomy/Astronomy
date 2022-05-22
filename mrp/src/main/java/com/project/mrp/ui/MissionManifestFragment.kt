package com.project.mrp.ui

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.fragment.app.activityViewModels
import androidx.navigation.Navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.facebook.shimmer.ShimmerFrameLayout
import com.project.core.ui.BaseFragment
import com.project.core.viewmodel.SavedStateViewModelFactory
import com.project.mrp.R
import com.project.mrp.databinding.FragmentMissionManifestBinding
import com.project.mrp.di.SCOPE_MISSION_MANIFEST_MODULE
import com.project.mrp.entities.remote.PhotoManifest
import com.project.mrp.entities.remote.Photos
import com.project.mrp.entities.remote.PhotosInformation
import com.project.mrp.ui.AllRoverFun.transformDate
import com.project.mrp.viewmodel.MissionManifestViewModel
import com.project.mrp.viewmodel.MissionManifestViewModelFactory
import com.project.mrp.viewmodel.PhotosViewModel
import org.koin.android.ext.android.getKoin
import org.koin.core.qualifier.named


class MissionManifestFragment :
    BaseFragment<FragmentMissionManifestBinding>(FragmentMissionManifestBinding::inflate) {
    private val navArgs: MissionManifestFragmentArgs by navArgs()

    private val missionFragmentScope =
        getKoin().getOrCreateScope(SCOPE_MISSION_MANIFEST_MODULE, named(
            SCOPE_MISSION_MANIFEST_MODULE))

    private var mShimmerViewContainer: ShimmerFrameLayout? = null
    private val listPhotoDisplay = mutableListOf<PhotosInformation>()



    private val missionViewModelFactory: MissionManifestViewModelFactory =
        missionFragmentScope.get()
    private val missionManifestViewModel: MissionManifestViewModel by viewModels {
        SavedStateViewModelFactory(missionViewModelFactory, this)
    }

    private val adapter by lazy { MissionRecyclerViewAdapter(::onItemClick) }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        return providePersistentView(inflater, container, savedInstanceState)
    }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (!hasInitializedRootView) {
            hasInitializedRootView = true
            mShimmerViewContainer = binding.shimmerViewContainerRoverImg
            initRecyclerView()
            missionManifestViewModel.loadAsync("curiosity")
        }
        photoManifest()
        roverInfo()
        val roverName = navArgs.roverName
    }

    override fun onResume() {
        super.onResume()
        mShimmerViewContainer?.startShimmerAnimation()
    }

    override fun onPause() {
        super.onPause()
        mShimmerViewContainer?.startShimmerAnimation()
    }

    private fun photoManifest() {
        with(missionManifestViewModel) {
            responseMission().observe(viewLifecycleOwner) {

                it.photoManifest?.apply {
                    PhotoManifest(
                        name, landingDate, launchDate,
                        status, maxSol, maxDate, totalPhotos, photos
                    )
                    photoList(it.photoManifest)
                }

                adapter.adapterList = listPhotoDisplay
                mShimmerViewContainer?.stopShimmerAnimation()
                mShimmerViewContainer?.visibility = View.GONE
            }
            error().observe(viewLifecycleOwner) { showThrowable(it) }
        }
    }

    private fun photoList(photos: PhotoManifest?) {
        photos?.photos?.apply {
            for (n in 0..1000) {
                listPhotoDisplay += PhotosInformation(
                    get(n).sol, get(n).earthDate,
                    get(n).totalPhotos, get(n).cameras)
            }
        }
    }

    private fun initRecyclerView() {
        with(binding.rvPhotos) {
            layoutManager = LinearLayoutManager(activity)
            adapter = this@MissionManifestFragment.adapter
        }
    }

    @SuppressLint("SimpleDateFormat", "SetTextI18n")
    private fun roverInfo() {
        with(missionManifestViewModel) {
            responseMission().observe(viewLifecycleOwner) {
                binding.apply {
                    collapsToolbar.title = it.photoManifest?.name
                    launchDate.text = "Дата Запуска: " + transformDate(it.photoManifest?.launchDate)
                    landingDate.text = "Приземлился: " + transformDate(it.photoManifest?.landingDate)
                    totalPhotos.text =
                        "Количество фотографий: " + it.photoManifest?.totalPhotos.toString()
                    if (it.photoManifest?.status.isNullOrEmpty()) {
                        status.text = "Статус мисии: Неизвестен"
                    } else {
                        status.text = "Статус мисии: " + it.photoManifest?.status
                    }
                    maxSol.text = "Последний сол: " + it.photoManifest?.maxSol
                    maxDate.text = "Последняя дата Земли: " + it.photoManifest?.maxDate
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        missionFragmentScope.close()
    }

    private fun onItemClick(photoInfo: PhotosInformation) {
        val action = MissionManifestFragmentDirections.actionMissionManifestFragmentToPhotosFragment(navArgs.roverName, photoInfo)
        findNavController().navigate(action)
    }
}