package com.project.donki.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import com.project.core.ui.BaseRecyclerViewAdapter
import com.project.donki.databinding.ItemRvGstBinding
import com.project.donki.entities.GeomagneticStorm

class GSTRecyclerViewAdapter : BaseRecyclerViewAdapter<GeomagneticStorm>() {
    private val gstDiffUtilCallBack = object : DiffUtil.ItemCallback<GeomagneticStorm>() {
        override fun areItemsTheSame(oldItem: GeomagneticStorm, newItem: GeomagneticStorm): Boolean =
            oldItem.gstID == newItem.gstID

        override fun areContentsTheSame(oldItem: GeomagneticStorm, newItem: GeomagneticStorm): Boolean =
            oldItem == newItem
    }

    override val data: AsyncListDiffer<GeomagneticStorm>
        get() = AsyncListDiffer(this, gstDiffUtilCallBack)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GSTViewHolder =
        GSTViewHolder(ItemRvGstBinding.inflate(LayoutInflater.from(parent.context), parent, false))

    class GSTViewHolder(private val viewBinding: ItemRvGstBinding) : BaseViewHolder<GeomagneticStorm>(viewBinding.root) {
        override fun bind(geomagneticStorm: GeomagneticStorm) {
            //TODO: bind solarFlare to view
        }
    }
}