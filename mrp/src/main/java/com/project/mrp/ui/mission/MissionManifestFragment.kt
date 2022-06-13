package com.project.mrp.ui.mission

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.facebook.shimmer.ShimmerFrameLayout
import com.project.core.ui.BaseFragment
import com.project.core.viewmodel.SavedStateViewModelFactory
import com.project.mrp.databinding.FragmentMissionManifestBinding
import com.project.mrp.di.SCOPE_MISSION_MANIFEST_MODULE
import com.project.mrp.entities.remote.PhotosInformation
import com.project.mrp.viewmodel.MissionManifestViewModel
import com.project.mrp.viewmodel.MissionManifestViewModelFactory
import org.koin.android.ext.android.getKoin
import org.koin.core.qualifier.named
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

class MissionManifestFragment :
    BaseFragment<FragmentMissionManifestBinding>(FragmentMissionManifestBinding::inflate) {
    private val navArgs: MissionManifestFragmentArgs by navArgs()

    private val missionFragmentScope =
        getKoin().getOrCreateScope(SCOPE_MISSION_MANIFEST_MODULE, named(
            SCOPE_MISSION_MANIFEST_MODULE))

    private var mShimmerViewContainer: ShimmerFrameLayout? = null

    private val missionViewModelFactory: MissionManifestViewModelFactory =
        missionFragmentScope.get()
    private val missionManifestViewModel: MissionManifestViewModel by viewModels {
        SavedStateViewModelFactory(missionViewModelFactory, this)
    }

    private val onListUpdated: (List<PhotosInformation>, List<PhotosInformation>) -> Unit =
        { _, _ ->
            mShimmerViewContainer?.stopShimmer()
            mShimmerViewContainer?.visibility = View.GONE
        }

    private val adapter by lazy {
        MissionRecyclerViewAdapter(::onItemClick, onListUpdated)
    }

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
            mShimmerViewContainer = binding.shimmerViewContainerRoverImg
            initRecyclerView()
            roverInfo()
        }
    }

    override fun onResume() {
        super.onResume()
        mShimmerViewContainer?.startShimmer()
    }

    override fun onPause() {
        super.onPause()
        mShimmerViewContainer?.stopShimmer()
    }

    private fun initRecyclerView() {
        with(binding.rvPhotos) {
            layoutManager = LinearLayoutManager(activity)
            adapter = this@MissionManifestFragment.adapter
        }
    }

    @SuppressLint("SimpleDateFormat")
    private fun transformDate(date: String?): String {
        val serverDate = date
        val originalFormat: DateFormat = SimpleDateFormat("yyyy-MM-dd")
        val targetFormat: DateFormat = SimpleDateFormat("dd-MMM-yyyy")
        val date: Date = originalFormat.parse(serverDate.toString()) as Date
        return targetFormat.format(date)
    }

    @SuppressLint("SimpleDateFormat", "SetTextI18n")
    private fun roverInfo() {
        missionManifestViewModel.loadAsync(navArgs.roverName)
        with(missionManifestViewModel) {
            responseMissionManifest().observe(viewLifecycleOwner) {

                binding.apply {
                    collapsToolbar.title = it?.name
                    launchDate.text = "Дата Запуска: " + transformDate(it?.launchDate)
                    landingDate.text = "Приземлился: " + transformDate(it?.landingDate)
                    roverName.text = "Исследователь: " + it?.name
                    totalPhotos.text =
                        "Количество фотографий: " + it?.totalPhotos.toString()
                    if (it?.status.isNullOrEmpty()) {
                        status.text = "Статус мисии: Неизвестен"
                    } else {
                        status.text = "Статус мисии: " + it?.status
                    }
                    maxSol.text = "Последний сол: " + it?.maxSol
                    maxDate.text = "Последняя дата Земли: " + it?.maxDate
                }
            }
            responseMissionManifestList().observe(viewLifecycleOwner) {
                adapter.items = it
            }

            error().observe(viewLifecycleOwner) { showThrowable(it) }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        missionFragmentScope.close()
    }

    private fun onItemClick(photoInfo: PhotosInformation) {
        val action = MissionManifestFragmentDirections.actionFragmentMissionToFragmentPhotos(navArgs.roverName, photoInfo)
        findNavController().navigate(action)
    }
}