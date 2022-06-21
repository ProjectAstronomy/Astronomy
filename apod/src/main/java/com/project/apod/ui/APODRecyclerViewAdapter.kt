package com.project.apod.ui

import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.webkit.WebChromeClient
import android.webkit.WebSettings
import android.webkit.WebView
import android.widget.ImageView
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import com.project.apod.databinding.ItemRvApodBinding
import com.project.apod.entities.remote.APODResponse
import com.project.core.entities.ImageResolution
import com.project.core.ui.BaseRecyclerViewAdapter

class APODRecyclerViewAdapter(
    private val onItemClickListener: (APODResponse) -> Unit,
    private val onItemImageLoader: (ImageView, String?) -> Unit,
    private val onListUpdated: (List<APODResponse>, List<APODResponse>) -> Unit
) : BaseRecyclerViewAdapter<APODResponse>() {

    private val apodDiffUtilCallBack = object : DiffUtil.ItemCallback<APODResponse>() {
        override fun areItemsTheSame(oldItem: APODResponse, newItem: APODResponse): Boolean =
            oldItem.date == newItem.date

        override fun areContentsTheSame(oldItem: APODResponse, newItem: APODResponse): Boolean =
            oldItem == newItem
    }

    private var imageResolution = ImageResolution.REGULAR

    override val differ = AsyncListDiffer(this, apodDiffUtilCallBack).apply {
        this.addListListener(onListUpdated)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): APODViewHolder =
        APODViewHolder(
            ItemRvApodBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    fun onImageResolutionChanged(imageResolution: ImageResolution) {
        this.imageResolution = imageResolution
    }

    inner class APODViewHolder(private val viewBinding: ItemRvApodBinding) :
        BaseViewHolder<APODResponse>(viewBinding.root) {

        @SuppressLint("SetJavaScriptEnabled")
        override fun bind(apodResponse: APODResponse) {
            itemView.setOnClickListener { onItemClickListener(apodResponse) }
            with(viewBinding) {
                tvTitleApod.text = apodResponse.title
                tvDateApod.text = apodResponse.date
                tvCopyrightApod.text = apodResponse.copyright

                when (apodResponse.mediaType) {
                    "image" -> {
                        viewBinding.ivUrlApod.visibility = View.VISIBLE
                        viewBinding.wvRvUrlVideoApod.visibility = View.GONE
                        ivUrlApod.setImageDrawable(null)
                        onItemImageLoader(
                            ivUrlApod,
                            when (imageResolution) {
                                ImageResolution.REGULAR -> apodResponse.url
                                ImageResolution.HD -> apodResponse.hdurl
                            }
                        )
                    }

                    "video" -> {
                        viewBinding.ivUrlApod.visibility = View.GONE
                        viewBinding.wvRvUrlVideoApod.visibility = View.VISIBLE
                        if (apodResponse.url?.take(23) == "https://www.youtube.com") {
                            with(viewBinding.wvRvUrlVideoApod) {
                                visibility = View.VISIBLE
                                settings.javaScriptEnabled = true
                                settings.pluginState = WebSettings.PluginState.ON
                                loadUrl(apodResponse.url + "&fs=0&loop=1&modestbranding=1&autoplay=1&mute=1")
                                webChromeClient = WebChromeClient()
                            }
                        } else {
                            viewBinding.wvRvUrlVideoApod.loadUrl(apodResponse.url.toString())
                            // using setOnTouchListener because of onClickListener does not work in webview
                            viewBinding.wvRvUrlVideoApod.setOnTouchListener(object : View.OnTouchListener {
                                override fun onTouch(v: View?, event: MotionEvent?): Boolean {
                                    when (event?.action) {
                                        MotionEvent.ACTION_UP -> onItemClickListener(apodResponse)
                                    }
                                    return v?.onTouchEvent(event) ?: true
                                }
                            })

                        }
                    }
                }
            }
        }
    }
}
