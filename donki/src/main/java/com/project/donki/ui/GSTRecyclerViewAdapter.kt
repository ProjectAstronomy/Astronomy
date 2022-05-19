package com.project.donki.ui

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.core.view.isVisible
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import com.project.core.ui.BaseRecyclerViewAdapter
import com.project.donki.entities.remote.GeomagneticStorm
import com.project.donki.R

class GSTRecyclerViewAdapter : BaseRecyclerViewAdapter<GeomagneticStorm>() {
    companion object {
        private const val TYPE_NO_FLR = 0
        private const val TYPE_HEADER = 1
        private const val TYPE_SMALL = 2
        private const val TYPE_LARGE = 3
    }

    var adapterListGST: List<GeomagneticStorm> = listOf()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    private val gstDiffUtilCallBack = object : DiffUtil.ItemCallback<GeomagneticStorm>() {
        override fun areItemsTheSame(oldItem: GeomagneticStorm, newItem: GeomagneticStorm): Boolean =
            oldItem.gstID == newItem.gstID

        override fun areContentsTheSame(oldItem: GeomagneticStorm, newItem: GeomagneticStorm): Boolean =
            oldItem == newItem
    }

    override val differ = AsyncListDiffer(this, gstDiffUtilCallBack)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<GeomagneticStorm> {
        val myInflater = LayoutInflater.from(parent.context)
        return when (viewType) {
            TYPE_HEADER -> HeadersViewHolder(myInflater.inflate(R.layout.item_rv_flr_header, parent, false))
            TYPE_NO_FLR -> NoFlareViewHolder(myInflater.inflate(R.layout.item_rv_flr_no_data, parent, false))
            TYPE_LARGE -> LargeViewHolder(myInflater.inflate(R.layout.item_rv_flr_detailed, parent, false))
            else -> SmallViewHolder(myInflater.inflate(R.layout.item_rv_flr, parent, false))
        }
    }

    override fun onBindViewHolder(holder: BaseViewHolder<GeomagneticStorm>, position: Int) {
        when (getItemViewType(position)) {
            TYPE_HEADER -> holder as HeadersViewHolder
            TYPE_SMALL -> holder as SmallViewHolder
            TYPE_LARGE -> holder as LargeViewHolder
            TYPE_NO_FLR -> holder as NoFlareViewHolder
        }
        holder.bind(adapterListGST[position])
    }

    override fun getItemCount(): Int = adapterListGST.size

    inner class HeadersViewHolder(itemView: View) : BaseViewHolder<GeomagneticStorm>(itemView) {
        override fun bind(adapterItemData: GeomagneticStorm) {
            itemView.findViewById<TextView>(R.id.tv_date_solar).text = adapterItemData.startTime
        }
    }

    inner class SmallViewHolder(itemView: View) : BaseViewHolder<GeomagneticStorm>(itemView) {
        override fun bind(adapterItemData: GeomagneticStorm) {
            fillSmallDataInRvItem (itemView, adapterItemData)
            fillColoredScale (itemView, adapterItemData)
            itemView.setOnClickListener {
                toggleType(layoutPosition)
            }
        }
    }

    inner class LargeViewHolder(itemView: View) : BaseViewHolder<GeomagneticStorm>(itemView) {
        override fun bind(adapterItemData: GeomagneticStorm) {
            fillSmallDataInRvItem(itemView, adapterItemData)
            fillColoredScale (itemView, adapterItemData)
            fillDetailedDataInRvItem(itemView, adapterItemData)
            itemView.setOnClickListener {
                toggleType(layoutPosition)
            }
        }
    }

    inner class NoFlareViewHolder(itemView: View) : BaseViewHolder<GeomagneticStorm>(itemView) {
        override fun bind(adapterItemData: GeomagneticStorm) {}
    }

    override fun getItemViewType(position: Int): Int {
        return when (adapterListGST[position].link) {
            "header" -> TYPE_HEADER
            "no_flare" -> TYPE_NO_FLR
            "large" -> TYPE_LARGE
            else -> TYPE_SMALL
        }
    }

    private fun toggleType(layoutPosition: Int) {
        if (adapterListGST[layoutPosition].link == "large")
            adapterListGST[layoutPosition].link = "small"
        else adapterListGST[layoutPosition].link = "large"
        notifyItemChanged(layoutPosition)
    }

    private fun fillColoredScale (itemView: View, adapterItemData: GeomagneticStorm) {
        // обнуляем scale, т.к. было замечено сохранение старых значений при переопределении itemView
        itemView.findViewById<CardView>(R.id.view_scale_1of5).isVisible = false
        itemView.findViewById<CardView>(R.id.view_scale_2of5).isVisible = false
        itemView.findViewById<CardView>(R.id.view_scale_3of5).isVisible = false
        itemView.findViewById<CardView>(R.id.view_scale_4of5).isVisible = false
        itemView.findViewById<CardView>(R.id.view_scale_5of5).isVisible = false

        val cType = adapterItemData.allKpIndex?.get(0)?.kpIndex?.toInt()
        if (cType != null) {
            if (cType > 2) itemView.findViewById<CardView>(R.id.view_scale_1of5).isVisible = true
            if (cType > 3) itemView.findViewById<CardView>(R.id.view_scale_2of5).isVisible = true
            if (cType > 4) itemView.findViewById<CardView>(R.id.view_scale_3of5).isVisible = true
            if (cType > 5) itemView.findViewById<CardView>(R.id.view_scale_4of5).isVisible = true
            if (cType > 6) itemView.findViewById<CardView>(R.id.view_scale_5of5).isVisible = true
        }
    }

    @SuppressLint("SetTextI18n")
    private fun fillSmallDataInRvItem (itemView: View, adapterItemData: GeomagneticStorm) {
        itemView.findViewById<TextView>(R.id.tv_date_solar).text = adapterItemData.startTime
        itemView.findViewById<TextView>(R.id.tv_solar_flare_class).text = "kp ${adapterItemData.allKpIndex?.get(0)?.kpIndex}"
    }

    @SuppressLint("SetTextI18n")
    private fun fillDetailedDataInRvItem (itemView: View, adapterItemData: GeomagneticStorm) {
        itemView.findViewById<TextView>(R.id.tv_solar_details).text =
            "startTime : ${adapterItemData.gstID}\n" +
                    "observedTime : ${adapterItemData.allKpIndex?.get(0)?.observedTime}\n" +
                    "kpIndex : ${adapterItemData.allKpIndex?.get(0)?.kpIndex}\n" +
                    "source : ${adapterItemData.allKpIndex?.get(0)?.source}\n"
        itemView.findViewById<TextView>(R.id.tv_date_solar_detailed).text = " \n \n \n \n"
    }
}