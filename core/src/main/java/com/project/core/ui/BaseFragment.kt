package com.project.core.ui

import android.content.ContentValues
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import coil.ImageLoader
import coil.request.ImageRequest
import com.google.android.material.snackbar.BaseTransientBottomBar
import com.google.android.material.snackbar.Snackbar
import com.project.core.R
import java.io.IOException

abstract class BaseFragment<V : ViewBinding>(
    private val inflaterBinding: (inflater: LayoutInflater, root: ViewGroup?, attachToRoot: Boolean) -> V
) : Fragment() {

    private var _binding: V? = null
    protected val binding: V get() = _binding!!

    protected var hasInitializedRootView: Boolean = false
    private var rootView: View? = null

    protected var isWritePermissionGranted = false
    private val permissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
            isWritePermissionGranted = isGranted
        }

    protected fun providePersistentView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        if (rootView == null) {
            _binding = inflaterBinding.invoke(inflater, container, false)
            rootView = binding.root
        } else {
            (rootView?.parent as? ViewGroup)?.removeView(rootView)
        }
        return rootView
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    protected fun showThrowable(throwable: Throwable) {
        Snackbar.make(
            binding.root,
            throwable.message.toString(),
            BaseTransientBottomBar.LENGTH_SHORT
        ).show()
    }

    protected fun useCoilToLoadPhoto(imageView: ImageView, imageLink: String?) {
        context?.let { _context ->
            val request = ImageRequest.Builder(_context)
                .data(imageLink)
                .target(
                    onStart = {},
                    onSuccess = { drawable -> imageView.setImageDrawable(drawable) },
                    onError = { imageView.setImageResource(R.drawable.no_image) }
                )
                .crossfade(true)
                .build()
            ImageLoader(_context).enqueue(request)
        }
    }

    protected fun requestWritePermission() {
        val isWritePermission = ContextCompat.checkSelfPermission(
            requireContext(),
            android.Manifest.permission.WRITE_EXTERNAL_STORAGE
        ) == PackageManager.PERMISSION_GRANTED

        isWritePermissionGranted =
            isWritePermission || Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q

        if (!isWritePermissionGranted) {
            permissionLauncher.launch(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
        }
    }

    protected fun saveImageToExternalStorage(bitmap: Bitmap, fileName: String) {
        val imageCollection: Uri = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            MediaStore.Images.Media.getContentUri(MediaStore.VOLUME_EXTERNAL_PRIMARY)
        } else {
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI
        }

        val contentValues = ContentValues().apply {
            put(MediaStore.Images.Media.DISPLAY_NAME, fileName)
            put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg")
            put(MediaStore.Images.Media.WIDTH, bitmap.width)
            put(MediaStore.Images.Media.HEIGHT, bitmap.height)
        }

        try {
            val contentResolver = requireContext().contentResolver
            contentResolver.insert(imageCollection, contentValues)?.also { uri ->
                contentResolver.openOutputStream(uri).use { outputStream ->
                    if (!bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream)) {
                        throw IOException("Failed to save image")
                    } else {
                        Toast.makeText(requireContext(), "Image saved successfully", Toast.LENGTH_LONG).show()
                    }
                }
            } ?: throw IOException("Failed to create Media Store entry")
        } catch (exception: IOException) {
            Toast.makeText(requireContext(), exception.localizedMessage, Toast.LENGTH_LONG).show()
        }
    }
}