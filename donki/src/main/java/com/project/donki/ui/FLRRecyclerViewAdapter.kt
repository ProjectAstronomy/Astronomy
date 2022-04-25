package com.project.donki.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import com.project.core.ui.BaseRecyclerViewAdapter
import com.project.donki.databinding.ItemRvFlrBinding
import com.project.donki.entities.SolarFlare

class FLRRecyclerViewAdapter : BaseRecyclerViewAdapter<SolarFlare>() {
    private val flrDiffUtilCallBack = object : DiffUtil.ItemCallback<SolarFlare>() {
        override fun areItemsTheSame(oldItem: SolarFlare, newItem: SolarFlare): Boolean =
            oldItem.flrID == newItem.flrID

        override fun areContentsTheSame(oldItem: SolarFlare, newItem: SolarFlare): Boolean =
            oldItem == newItem
    }

    override val data: AsyncListDiffer<SolarFlare>
        get() = AsyncListDiffer(this, flrDiffUtilCallBack)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FLRViewHolder =
        FLRViewHolder(ItemRvFlrBinding.inflate(LayoutInflater.from(parent.context), parent, false))

    override fun getItemCount(): Int = data.currentList.size

    class FLRViewHolder(private val viewBinding: ItemRvFlrBinding) : BaseViewHolder<SolarFlare>(viewBinding.root) {
        override fun bind(solarFlare: SolarFlare) {
            //TODO: bind solarFlare to view
        }
    }
}