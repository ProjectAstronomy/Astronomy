package com.project.core.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import coil.ImageLoader
import coil.request.ImageRequest

abstract class BaseFragment<V : ViewBinding>(
    private val inflaterBinding: (inflater: LayoutInflater, root: ViewGroup?, attachToRoot: Boolean) -> V
) : Fragment() {

    private var _binding: V? = null
    protected val binding: V get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = inflaterBinding.invoke(inflater, container, false)
        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    protected fun useCoilToLoadPhoto(imageView: ImageView, imageLink: String?) {
        context?.let { _context ->
            val request = ImageRequest.Builder(_context)
                .data(imageLink)
                .target(
                    onStart = {},
                    onSuccess = { drawable -> imageView.setImageDrawable(drawable) },
                    onError = { imageView.setImageResource(com.project.core.R.drawable.no_image) }
                )
                .build()
            ImageLoader(_context).enqueue(request)
        }
    }
}