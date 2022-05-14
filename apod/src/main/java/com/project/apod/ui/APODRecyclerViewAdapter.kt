package com.project.apod.ui

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebChromeClient
import android.webkit.WebSettings
import android.widget.ImageView
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import com.project.apod.databinding.ItemRvApodBinding
import com.project.apod.entities.APODResponse
import com.project.core.ui.BaseRecyclerViewAdapter

class APODRecyclerViewAdapter(
    private val onItemClickListener: (APODResponse) -> Unit,
    private val onItemImageLoader: (ImageView, String?) -> Unit
) : BaseRecyclerViewAdapter<APODResponse>() {

    private val apodDiffUtilCallBack = object : DiffUtil.ItemCallback<APODResponse>() {
        override fun areItemsTheSame(oldItem: APODResponse, newItem: APODResponse): Boolean =
            oldItem.date == newItem.date

        override fun areContentsTheSame(oldItem: APODResponse, newItem: APODResponse): Boolean =
            oldItem == newItem
    }

    override val differ = AsyncListDiffer(this, apodDiffUtilCallBack)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): APODViewHolder =
        APODViewHolder(ItemRvApodBinding.inflate(LayoutInflater.from(parent.context), parent, false))

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
                        onItemImageLoader(ivUrlApod, apodResponse.url)
                    }
                    "video" -> {
                        viewBinding.ivUrlApod.visibility = View.GONE
                        with(viewBinding.wvRvUrlVideoApod) {
                            visibility = View.VISIBLE
                            settings.javaScriptEnabled = true
                            settings.pluginState = WebSettings.PluginState.ON
                            loadUrl(apodResponse.url + "&fs=0&loop=1&modestbranding=1&autoplay=1&mute=1")
                            webChromeClient = WebChromeClient()
                        }
                    }
                }
            }
        }
    }
}