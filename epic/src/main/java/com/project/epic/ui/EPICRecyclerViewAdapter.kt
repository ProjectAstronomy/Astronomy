package com.project.epic.ui

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import com.project.core.ui.BaseRecyclerViewAdapter
import com.project.epic.databinding.ItemRvEpicBinding
import com.project.epic.entities.remote.EPICResponse
import com.project.epic.ui.ImageEpic.dateImageEpic
import com.project.epic.ui.ImageEpic.urlEpicImage


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
        private var version = "V: "
        private var datez = "Date: "
        private var id = "ID: "

        @SuppressLint("SetTextI18n")
        override fun bind(epicResponse: EPICResponse) {
            itemView.setOnClickListener { onItemClickListener(epicResponse) }
            with(viewBinding) {
                onItemImageLoader(imgEpic, urlEpicImage(epicResponse.date.toString(), epicResponse.image.toString()))
                verEpic.text = version + epicResponse.version
                dateEpic.text = datez + epicResponse.date?.let { dateImageEpic(it) }
                identifierEpic.text = id + epicResponse.identifier
            }
        }
    }
}