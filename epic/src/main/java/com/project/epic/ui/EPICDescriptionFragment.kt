package com.project.epic.ui

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.graphics.drawable.toBitmap
import androidx.navigation.fragment.navArgs
import com.project.core.ui.BaseFragment
import com.project.epic.databinding.FragmentEpicDescriptionBinding
import com.project.epic.ui.ImageEpic.urlEpicImage

class EPICDescriptionFragment :
    BaseFragment<FragmentEpicDescriptionBinding>(FragmentEpicDescriptionBinding::inflate) {

    private val navArgs: EPICDescriptionFragmentArgs by navArgs()

    private var x = "X: "
    private var y = "Y: "
    private var z = "Z: "

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
        requestWritePermission()
        hasInitializedRootView = !hasInitializedRootView
        val epicResponse = navArgs.epicResponse

        with(binding) {
            sunX.text = x + epicResponse.sunJ2000Position?.x.toString()
            sunY.text = y + epicResponse.sunJ2000Position?.y.toString()
            sunZ.text = z + epicResponse.sunJ2000Position?.z.toString()

            sunXLuna.text = x + epicResponse.lunarJ2000Position?.x.toString()
            sunYLuna.text = y + epicResponse.lunarJ2000Position?.y.toString()
            sunZLuna.text = z + epicResponse.lunarJ2000Position?.z.toString()

            dscovrX.text = x + epicResponse.dscovrJ2000Position?.x.toString()
            dscovrY.text = y + epicResponse.dscovrJ2000Position?.y.toString()
            dscovrZ.text = z + epicResponse.dscovrJ2000Position?.z.toString()

            useCoilToLoadPhoto(imgEpic, urlEpicImage(epicResponse.date.toString(), epicResponse.image.toString()))

            saveImageToExternalStorage.setOnClickListener {
                if (isWritePermissionGranted)
                    saveImageToExternalStorage(
                        binding.imgEpic.drawable.toBitmap(),
                        "${epicResponse.identifier}.jpg"
                    )
            }
        }
    }
}