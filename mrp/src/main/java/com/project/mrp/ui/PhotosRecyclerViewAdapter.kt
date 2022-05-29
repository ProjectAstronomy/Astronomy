package com.project.mrp.ui.mission

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import com.project.core.ui.BaseRecyclerViewAdapter
import com.project.mrp.databinding.ItemPhotosBinding
import com.project.mrp.databinding.ItemRvMrpBinding
import com.project.mrp.entities.remote.Photo
import com.project.mrp.entities.remote.PhotosInformation

class PhotosRecyclerViewAdapter(private val onItemImageLoader: (ImageView, String?) -> Unit) :
    BaseRecyclerViewAdapter<Photo>() {

    var adapterList: List<Photo> = listOf()
        @SuppressLint("NotifyDataSetChanged")
        set(value) {
            field = value
            notifyDataSetChanged()
        }


    private val flrDiffUtilCallBack = object : DiffUtil.ItemCallback<Photo>() {
        override fun areItemsTheSame(
            oldItem: Photo,
            newItem: Photo,
        ): Boolean =
            oldItem.id == newItem.id

        override fun areContentsTheSame(
            oldItem: Photo,
            newItem: Photo,
        ): Boolean =
            oldItem == newItem
    }

    override val differ = AsyncListDiffer(this, flrDiffUtilCallBack)

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): BaseViewHolder<Photo> {
        return ManifestViewHolder(ItemPhotosBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: BaseViewHolder<Photo>, position: Int) {
        holder as ManifestViewHolder
        holder.bind(adapterList[position])
    }

    override fun getItemCount(): Int = adapterList.size

    inner class ManifestViewHolder(private val viewBinding: ItemPhotosBinding) : BaseViewHolder<Photo>(viewBinding.root) {
        @SuppressLint("SetTextI18n")
        override fun bind(adapterItem: Photo) {
            with(viewBinding) {
                onItemImageLoader(imgSrc, adapterItem.imgSrc.toString())
                sol.text = "Sol: " + adapterItem.sol.toString()
                earthDate.text = "Дата Земли: " + adapterItem.earthDate.toString()
                camera.text = adapterItem.camera?.name
            }
        }
    }

}