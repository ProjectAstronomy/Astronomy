package com.project.mrp.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.project.core.ui.BaseFragment
import com.project.core.viewmodel.SavedStateViewModelFactory
import com.project.mrp.databinding.FragmentPhotosBinding
import com.project.mrp.di.SCOPE_PHOTOS_MODULE
import com.project.mrp.entities.remote.Photo
import com.project.mrp.entities.remote.Photos
import com.project.mrp.ui.mission.PhotosRecyclerViewAdapter
import com.project.mrp.viewmodel.PhotosViewModel
import com.project.mrp.viewmodel.PhotosViewModelFactory
import org.koin.android.ext.android.getKoin
import org.koin.core.qualifier.named

class PhotosFragment : BaseFragment<FragmentPhotosBinding>(FragmentPhotosBinding::inflate) {
    private val navArgs: PhotosFragmentArgs by navArgs()

    private val listPhotoDisplay = mutableListOf<Photo>()

    private val photosScope = getKoin().getOrCreateScope(SCOPE_PHOTOS_MODULE, named(
        SCOPE_PHOTOS_MODULE))

    private val photosViewFactory: PhotosViewModelFactory =
        photosScope.get()

    private val photosViewModel: PhotosViewModel by viewModels {
        SavedStateViewModelFactory(photosViewFactory, this)
    }

    private val adapter by  lazy { PhotosRecyclerViewAdapter(::useCoilToLoadPhoto) }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return providePersistentView(inflater, container, savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val roverName = navArgs.roverName
        val photosInformation = navArgs.photosInformation

        if (!hasInitializedRootView) {
            hasInitializedRootView = true
            photosViewModel.loadAsync(roverName, photosInformation.sol!!)
            initRecyclerView()

        }

        with(photosViewModel) {
            photosViewModel.loadAsync(roverName, photosInformation.sol!!)
            responsePhotos().observe(viewLifecycleOwner) {
                binding.apply {
                    test.text = it.photos?.size.toString()
                }
                it.apply {
                    photos
                }
                photoList(it)
                adapter.adapterList = listPhotoDisplay
            }
            error().observe(viewLifecycleOwner) { showThrowable(it) }
        }
    }

    private fun initRecyclerView() {
        with(binding.rvPhoto) {
            layoutManager = LinearLayoutManager(activity)
            adapter = this@PhotosFragment.adapter
        }
    }

    private fun photoList(photos: Photos?) {
        photos?.photos?.apply {
            for (n in 0..1000) {
                listPhotoDisplay += Photo(
                    get(n).id,
                    get(n).sol,
                    get(n).camera,
                    get(n).imgSrc,
                    get(n).earthDate,
                    get(n).rover)
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        photosScope.close()
    }
}