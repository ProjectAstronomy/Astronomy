package com.project.donki.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.project.donki.databinding.ItemRvFlrBinding
import com.project.donki.entities.SolarFlare
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class FLRRecyclerViewAdapter : RecyclerView.Adapter<FLRRecyclerViewAdapter.FLRViewHolder>() {
    private val _isNeededToLoadInFlow = MutableStateFlow(false)
    val isNeededToLoadInFlow: StateFlow<Boolean> get() = _isNeededToLoadInFlow

    private val flrDiffUtilCallBack = object : DiffUtil.ItemCallback<SolarFlare>() {
        override fun areItemsTheSame(oldItem: SolarFlare, newItem: SolarFlare): Boolean =
            oldItem.flrID == newItem.flrID

        override fun areContentsTheSame(oldItem: SolarFlare, newItem: SolarFlare): Boolean =
            oldItem == newItem
    }

    private val data = AsyncListDiffer(this, flrDiffUtilCallBack)

    fun submitData(list: List<SolarFlare>) {
        data.submitList(list)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FLRViewHolder =
        FLRViewHolder(ItemRvFlrBinding.inflate(LayoutInflater.from(parent.context), parent, false))

    override fun onBindViewHolder(holder: FLRViewHolder, position: Int) {
        _isNeededToLoadInFlow.value = (position * 100 / itemCount) > 80
        holder.bind(data.currentList[position])
    }

    override fun getItemCount(): Int = data.currentList.size

    class FLRViewHolder(private val viewBinding: ItemRvFlrBinding) : RecyclerView.ViewHolder(viewBinding.root) {
        fun bind(solarFlare: SolarFlare) {
            //TODO: bind solarFlare to view
        }
    }
}