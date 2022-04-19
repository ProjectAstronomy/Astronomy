package com.project.apod.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.project.apod.databinding.ItemRvApodBinding
import com.project.apod.entities.APODResponse

class APODRecyclerViewAdapter(
    private val onItemClickListener: (APODResponse) -> Unit,
    private val onItemImageLoader: (ImageView, String?) -> Unit
) : RecyclerView.Adapter<APODRecyclerViewAdapter.APODViewHolder>() {

    private val apodDiffUtilCallBack = object : DiffUtil.ItemCallback<APODResponse>() {
        override fun areItemsTheSame(oldItem: APODResponse, newItem: APODResponse): Boolean
            = oldItem.date == newItem.date

        override fun areContentsTheSame(oldItem: APODResponse, newItem: APODResponse): Boolean
            = oldItem == newItem
    }

    private val data = AsyncListDiffer(this, apodDiffUtilCallBack)

    fun submitData(list: List<APODResponse>) {
        data.submitList(list)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): APODViewHolder =
        APODViewHolder(ItemRvApodBinding.inflate(LayoutInflater.from(parent.context), parent, false))

    override fun onBindViewHolder(holder: APODViewHolder, position: Int): Unit =
        holder.bind(data.currentList[position])

    override fun getItemCount(): Int = data.currentList.size

    inner class APODViewHolder(private val viewBinding: ItemRvApodBinding) :
        RecyclerView.ViewHolder(viewBinding.root) {

        fun bind(apodResponse: APODResponse) {
            itemView.setOnClickListener{ onItemClickListener(apodResponse) }
            with(viewBinding) {
                tvTitleApod.text = apodResponse.title
                tvDateApod.text = apodResponse.date
                tvCopyrightApod.text = apodResponse.copyright
                onItemImageLoader(ivUrlApod, apodResponse.url)
            }
        }
    }
}