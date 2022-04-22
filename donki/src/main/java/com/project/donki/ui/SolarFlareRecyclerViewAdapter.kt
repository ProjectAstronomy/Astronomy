package com.project.donki.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.project.donki.databinding.ItemRvSolarFlareBinding
import com.project.donki.entities.SolarFlareResponse
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class SolarFlareRecyclerViewAdapter :
    RecyclerView.Adapter<SolarFlareRecyclerViewAdapter.SolarFlareViewHolder>() {

    private val _isNeededToLoadInFlow = MutableStateFlow(false)
    val isNeededToLoadInFlow: StateFlow<Boolean> get() = _isNeededToLoadInFlow

    private val solarFlareDiffUtilCallBack = object : DiffUtil.ItemCallback<SolarFlareResponse>() {
        override fun areItemsTheSame(oldItem: SolarFlareResponse, newItem: SolarFlareResponse): Boolean =
            oldItem.flrID == newItem.flrID

        override fun areContentsTheSame(oldItem: SolarFlareResponse, newItem: SolarFlareResponse): Boolean =
            oldItem == newItem
    }

    private val data = AsyncListDiffer(this, solarFlareDiffUtilCallBack)

    fun submitData(list: List<SolarFlareResponse>) {
        data.submitList(list)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SolarFlareViewHolder =
        SolarFlareViewHolder(
            ItemRvSolarFlareBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )

    override fun onBindViewHolder(holder: SolarFlareViewHolder, position: Int) {
        _isNeededToLoadInFlow.value = (position * 100 / itemCount) > 80
        holder.bind(data.currentList[position])
    }

    override fun getItemCount(): Int = data.currentList.size

    class SolarFlareViewHolder(private val viewBinding: ItemRvSolarFlareBinding) :
        RecyclerView.ViewHolder(viewBinding.root) {
        fun bind(solarFlareResponse: SolarFlareResponse) {
            //TODO: bind solarFlare to view
        }
    }
}