package com.project.mrp.ui

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import com.project.core.ui.BaseRecyclerViewAdapter
import com.project.mrp.databinding.ItemRvMrpBinding
import com.project.mrp.entities.remote.PhotosInformation

class MussionRecyclerViewAdapter : BaseRecyclerViewAdapter<PhotosInformation>() {

    private val mussionDiffUtilCallBack = object : DiffUtil.ItemCallback<PhotosInformation>() {
        override fun areItemsTheSame(oldItem: PhotosInformation, newItem: PhotosInformation): Boolean =
            oldItem.sol == newItem.sol

        override fun areContentsTheSame(oldItem: PhotosInformation, newItem: PhotosInformation): Boolean =
            oldItem == newItem
    }

    override val differ: AsyncListDiffer<PhotosInformation>
        get() = AsyncListDiffer(this, mussionDiffUtilCallBack)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MissonViewHolder =
        MissonViewHolder(ItemRvMrpBinding.inflate(LayoutInflater.from(parent.context), parent, false))


    inner class MissonViewHolder(private val viewBinding: ItemRvMrpBinding) :
        BaseViewHolder<PhotosInformation>(viewBinding.root) {

        @SuppressLint("SetTextI18n")
        override fun bind(potoManifest: PhotosInformation) {
            itemView.setOnClickListener {  }
            with(viewBinding) {
                earthDate.text = potoManifest.sol.toString()
            }
        }
    }

}