package com.project.apod.ui

import android.view.LayoutInflater
import android.view.ViewGroup
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

        override fun bind(apodResponse: APODResponse) {
            itemView.setOnClickListener { onItemClickListener(apodResponse) }
            with(viewBinding) {
                tvTitleApod.text = apodResponse.title
                tvDateApod.text = apodResponse.date
                tvCopyrightApod.text = apodResponse.copyright
                when (apodResponse.mediaType) {
                    "image" -> {
                        ivUrlApod.setImageDrawable(null)
                        onItemImageLoader(ivUrlApod, apodResponse.url)
                    }
                    "video" -> {
                        //TODO: add YouTubePlayerView to item_rv_apod.xml
                    }
                }
            }
        }
    }
}