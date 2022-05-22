package com.project.mrp.ui

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import com.project.core.ui.BaseRecyclerViewAdapter
import com.project.mrp.databinding.ItemRvMrpBinding
import com.project.mrp.entities.remote.PhotosInformation

class MissionRecyclerViewAdapter(
    private val onItemClickListener: (PhotosInformation) -> Unit,
) :
    BaseRecyclerViewAdapter<PhotosInformation>() {

    var adapterList: List<PhotosInformation> = listOf()
        @SuppressLint("NotifyDataSetChanged")
        set(value) {
            field = value
            notifyDataSetChanged()
        }


    private val flrDiffUtilCallBack = object : DiffUtil.ItemCallback<PhotosInformation>() {
        override fun areItemsTheSame(
            oldItem: PhotosInformation,
            newItem: PhotosInformation,
        ): Boolean =
            oldItem.sol == newItem.sol

        override fun areContentsTheSame(
            oldItem: PhotosInformation,
            newItem: PhotosInformation,
        ): Boolean =
            oldItem == newItem
    }

    override val differ = AsyncListDiffer(this, flrDiffUtilCallBack)

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): BaseViewHolder<PhotosInformation> {
        return ManifestViewHolder(ItemRvMrpBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: BaseViewHolder<PhotosInformation>, position: Int) {
        holder as ManifestViewHolder
        holder.bind(adapterList[position])
    }

    override fun getItemCount(): Int = adapterList.size

    inner class ManifestViewHolder(private val viewBinding: ItemRvMrpBinding) : BaseViewHolder<PhotosInformation>(viewBinding.root) {
        @SuppressLint("SetTextI18n")
        override fun bind(adapterItem: PhotosInformation) {
            itemView.setOnClickListener { onItemClickListener(adapterItem) }
            with(viewBinding) {
                sol.text = "Sol: " + adapterItem.sol.toString()
                earthDate.text = "Дата Земли: " + adapterItem.earthDate
                totalPhotos.text = "Всего фото в этот день: " + adapterItem.totalPhotos.toString()
            }
        }
    }

}