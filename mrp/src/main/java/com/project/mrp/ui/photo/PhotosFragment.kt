package com.project.mrp.ui.photo

import android.content.res.Resources
import android.graphics.Color
import android.os.Bundle
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import androidx.annotation.ColorInt
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.project.core.ui.BaseFragment
import com.project.core.viewmodel.SavedStateViewModelFactory
import com.project.mrp.databinding.FragmentPhotosBinding
import com.project.mrp.di.SCOPE_PHOTOS_MODULE
import com.project.mrp.viewmodel.PhotosViewModel
import com.project.mrp.viewmodel.PhotosViewModelFactory
import org.koin.android.ext.android.getKoin
import org.koin.core.qualifier.named

class PhotosFragment : BaseFragment<FragmentPhotosBinding>(FragmentPhotosBinding::inflate) {
    private val navArgs: PhotosFragmentArgs by navArgs()

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

        //sets specific status bar color because of no appbar animation in this fragment
        val typedValue = TypedValue()
        val theme: Resources.Theme = requireContext().theme
        theme.resolveAttribute(com.google.android.material.R.attr.colorPrimaryVariant, typedValue, true)
        @ColorInt val mColor = typedValue.data
        val window: Window = requireActivity().window
        context?.let { window.setStatusBarColor(mColor) }

        val roverName = navArgs.roverName
        val photosInformation = navArgs.photosInformation

        if (!hasInitializedRootView) {
            hasInitializedRootView = true
            photosViewModel.loadAsync(roverName, checkNotNull(photosInformation.sol))
            initRecyclerView()
        }

        with(photosViewModel) {
            responsePhotos().observe(viewLifecycleOwner) {
                binding.collapsToolbar.title = roverName
                adapter.items = it
            }
            error().observe(viewLifecycleOwner) { showThrowable(it) }
        }
    }

    override fun onResume() {
        super.onResume()
        //returning transparent status bar background color
        val window: Window = requireActivity().window
        window.setStatusBarColor(Color.parseColor("#00000000"))
    }

    private fun initRecyclerView() {
        with(binding.rvPhoto) {
            layoutManager = LinearLayoutManager(activity)
            adapter = this@PhotosFragment.adapter
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        photosScope.close()
    }
}