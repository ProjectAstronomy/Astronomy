package com.project.mrp.ui.photo

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import com.project.core.ui.BaseRecyclerViewAdapter
import com.project.mrp.databinding.ItemPhotosBinding
import com.project.mrp.entities.remote.Photo

class PhotosRecyclerViewAdapter(
    private val onItemImageLoader: (ImageView, String?) -> Unit,
) : BaseRecyclerViewAdapter<Photo>() {

    private val photoDiffUtilCallBack = object : DiffUtil.ItemCallback<Photo>() {
        override fun areItemsTheSame(oldItem: Photo, newItem: Photo): Boolean =
            oldItem.sol == newItem.sol

        override fun areContentsTheSame(oldItem: Photo, newItem: Photo): Boolean =
            oldItem == newItem
    }

    override val differ = AsyncListDiffer(this, photoDiffUtilCallBack)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PhotoViewHolder =
        PhotoViewHolder(ItemPhotosBinding.inflate(LayoutInflater.from(parent.context), parent, false))

    inner class PhotoViewHolder(private val viewBinding: ItemPhotosBinding) :
        BaseViewHolder<Photo>(viewBinding.root) {

        @SuppressLint("SetTextI18n")
        override fun bind(t: Photo) {
            with(viewBinding) {
                sol.text = t.sol.toString()
                onItemImageLoader(imgSrc, t.imgSrc)
            }
        }
    }
}