package com.project.epic.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.project.epic.databinding.ItemRvEpicBinding
import com.project.epic.entities.EPICResponse
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class EPICRecyclerViewAdapter(
    private val onItemClickListener: (EPICResponse) -> Unit,
    private val onItemImageLoader: (ImageView, String?) -> Unit
) : RecyclerView.Adapter<EPICRecyclerViewAdapter.EPICViewHolder>() {

    private val _isNeededToLoadInFlow = MutableStateFlow(false)
    val isNeededToLoadInFlow: StateFlow<Boolean> get() = _isNeededToLoadInFlow

    private val apodDiffUtilCallBack = object : DiffUtil.ItemCallback<EPICResponse>() {
        override fun areItemsTheSame(oldItem: EPICResponse, newItem: EPICResponse): Boolean =
            oldItem.identifier == newItem.identifier

        override fun areContentsTheSame(oldItem: EPICResponse, newItem: EPICResponse): Boolean =
            oldItem == newItem
    }

    private val data = AsyncListDiffer(this, apodDiffUtilCallBack)

    fun submitData(list: List<EPICResponse>) {
        data.submitList(list)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EPICViewHolder =
        EPICViewHolder(ItemRvEpicBinding.inflate(LayoutInflater.from(parent.context), parent, false))

    override fun onBindViewHolder(holder: EPICViewHolder, position: Int) {
        _isNeededToLoadInFlow.value = (position * 100 / itemCount) > 80
        holder.bind(data.currentList[position])
    }

    override fun getItemCount(): Int = data.currentList.size

    inner class EPICViewHolder(private val viewBinding: ItemRvEpicBinding) :
        RecyclerView.ViewHolder(viewBinding.root) {

        fun bind(epicResponse: EPICResponse) {
            itemView.setOnClickListener { onItemClickListener(epicResponse) }
            with(viewBinding) {
                //TODO: bind epicResponse to item_rv_epic.xml
            }
        }
    }
}