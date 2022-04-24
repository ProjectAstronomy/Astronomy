package com.project.donki.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.project.donki.databinding.ItemRvGstBinding
import com.project.donki.entities.GeomagneticStorm
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class GSTRecyclerViewAdapter : RecyclerView.Adapter<GSTRecyclerViewAdapter.GSTViewHolder>() {
    private val _isNeededToLoadInFlow = MutableStateFlow(false)
    val isNeededToLoadInFlow: StateFlow<Boolean> get() = _isNeededToLoadInFlow

    private val gstDiffUtilCallBack = object : DiffUtil.ItemCallback<GeomagneticStorm>() {
        override fun areItemsTheSame(oldItem: GeomagneticStorm, newItem: GeomagneticStorm): Boolean =
            oldItem.gstID == newItem.gstID

        override fun areContentsTheSame(oldItem: GeomagneticStorm, newItem: GeomagneticStorm): Boolean =
            oldItem == newItem
    }

    private val data = AsyncListDiffer(this, gstDiffUtilCallBack)

    fun submitData(list: List<GeomagneticStorm>) {
        data.submitList(list)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GSTViewHolder =
        GSTViewHolder(ItemRvGstBinding.inflate(LayoutInflater.from(parent.context), parent, false))

    override fun onBindViewHolder(holder: GSTViewHolder, position: Int) {
        _isNeededToLoadInFlow.value = (position * 100 / itemCount) > 80
        holder.bind(data.currentList[position])
    }

    override fun getItemCount(): Int = data.currentList.size

    class GSTViewHolder(private val viewBinding: ItemRvGstBinding) : RecyclerView.ViewHolder(viewBinding.root) {
        fun bind(geomagneticStorm: GeomagneticStorm) {
            //TODO: bind solarFlare to view
        }
    }
}