package com.project.mrp.ui.mission

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
    private val onListUpdated: (List<PhotosInformation>, List<PhotosInformation>) -> Unit,
) :
    BaseRecyclerViewAdapter<PhotosInformation>() {

    private val missionDiffUtilCallBack = object : DiffUtil.ItemCallback<PhotosInformation>() {
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

    override val differ = AsyncListDiffer(this, missionDiffUtilCallBack).apply {
        this.addListListener(onListUpdated)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MissionViewHolder =
        MissionViewHolder(ItemRvMrpBinding.inflate(LayoutInflater.from(parent.context),
            parent,
            false))

    inner class MissionViewHolder(private val viewBinding: ItemRvMrpBinding) :
        BaseViewHolder<PhotosInformation>(viewBinding.root) {

        @SuppressLint("SetJavaScriptEnabled", "SetTextI18n")
        override fun bind(t: PhotosInformation) {
            itemView.setOnClickListener { onItemClickListener(t) }
            with(viewBinding) {
                val soll = t.sol?.plus(1)
                sol.text = "Солнечные сутки на Марсе: " + soll.toString()
                earthDate.text = "Передача фотографий: " + t.earthDate
                totalPhotos.text = "Всего фото сделано: " + t.totalPhotos.toString()
            }
        }
    }
}