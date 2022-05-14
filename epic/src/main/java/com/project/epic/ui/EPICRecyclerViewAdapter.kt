package com.project.epic.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import com.project.core.ui.BaseRecyclerViewAdapter
import com.project.epic.databinding.ItemRvEpicBinding
import com.project.epic.entities.EPICResponse

class EPICRecyclerViewAdapter(
    private val onItemClickListener: (EPICResponse) -> Unit,
    private val onItemImageLoader: (ImageView, String?) -> Unit
) : BaseRecyclerViewAdapter<EPICResponse>() {

    private val epicDiffUtilCallBack = object : DiffUtil.ItemCallback<EPICResponse>() {
        override fun areItemsTheSame(oldItem: EPICResponse, newItem: EPICResponse): Boolean =
            oldItem.identifier == newItem.identifier

        override fun areContentsTheSame(oldItem: EPICResponse, newItem: EPICResponse): Boolean =
            oldItem == newItem
    }

    override val differ = AsyncListDiffer(this, epicDiffUtilCallBack)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EPICViewHolder =
        EPICViewHolder(ItemRvEpicBinding.inflate(LayoutInflater.from(parent.context), parent, false))

    inner class EPICViewHolder(private val viewBinding: ItemRvEpicBinding) :
        BaseViewHolder<EPICResponse>(viewBinding.root) {

        override fun bind(epicResponse: EPICResponse) {
            itemView.setOnClickListener { onItemClickListener(epicResponse) }
            with(viewBinding) {
                //TODO: bind epicResponse to item_rv_epic.xml
            }
        }
    }
}