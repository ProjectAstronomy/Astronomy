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
import com.project.donki.entities.remote.SolarFlare
import com.project.donki.R
import com.project.donki.entities.remote.GeomagneticStorm

class FLRRecyclerViewAdapter : BaseRecyclerViewAdapter<SolarFlare>() {
    companion object {
        private const val TYPE_NO_FLR = 0
        private const val TYPE_HEADER = 1
        private const val TYPE_SMALL = 2
        private const val TYPE_LARGE = 3
    }

    var adapterList: List<SolarFlare> = listOf()
        set(value) {
            field = value
            notifyDataSetChanged()
        }


    private val flrDiffUtilCallBack = object : DiffUtil.ItemCallback<SolarFlare>() {
        override fun areItemsTheSame(oldItem: SolarFlare, newItem: SolarFlare): Boolean =
            oldItem.flrID == newItem.flrID

        override fun areContentsTheSame(oldItem: SolarFlare, newItem: SolarFlare): Boolean =
            oldItem == newItem
    }

    override val differ = AsyncListDiffer(this, flrDiffUtilCallBack)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<SolarFlare> {
        val myInflater = LayoutInflater.from(parent.context)
        return when (viewType) {
            TYPE_HEADER -> HeadersViewHolder(myInflater.inflate(R.layout.item_rv_flr_header, parent, false))
            TYPE_NO_FLR -> NoFlareViewHolder(myInflater.inflate(R.layout.item_rv_flr_no_data, parent, false))
            TYPE_LARGE -> LargeViewHolder(myInflater.inflate(R.layout.item_rv_flr_detailed, parent, false))
            else -> SmallViewHolder(myInflater.inflate(R.layout.item_rv_flr, parent, false))
        }
    }

    override fun onBindViewHolder(holder: BaseViewHolder<SolarFlare>, position: Int) {
        when (getItemViewType(position)) {
            TYPE_HEADER -> holder as HeadersViewHolder
            TYPE_SMALL -> holder as SmallViewHolder
            TYPE_LARGE -> holder as LargeViewHolder
            TYPE_NO_FLR -> holder as NoFlareViewHolder
        }
        holder.bind(adapterList[position])
    }

    override fun getItemCount(): Int = adapterList.size

    inner class HeadersViewHolder(itemView: View) : BaseViewHolder<SolarFlare>(itemView) {
        override fun bind(adapterItemData: SolarFlare) {
            itemView.findViewById<TextView>(R.id.tv_date_solar).text = adapterItemData.flrID
        }
    }

    inner class SmallViewHolder(itemView: View) : BaseViewHolder<SolarFlare>(itemView) {
        override fun bind(adapterItemData: SolarFlare) {
            fillSmallDataInRvItem(itemView, adapterItemData)
            fillColoredScale(itemView, adapterItemData)
            itemView.setOnClickListener {
                toggleType(layoutPosition)
            }
        }
    }

    inner class LargeViewHolder(itemView: View) : BaseViewHolder<SolarFlare>(itemView) {
        override fun bind(adapterItemData: SolarFlare) {
            fillSmallDataInRvItem(itemView, adapterItemData)
            fillColoredScale(itemView, adapterItemData)
            fillDetailedDataInRvItem(itemView, adapterItemData)
            itemView.setOnClickListener {
                toggleType(layoutPosition)
            }
        }
    }

    inner class NoFlareViewHolder(itemView: View) : BaseViewHolder<SolarFlare>(itemView) {
        override fun bind(adapterItemData: SolarFlare) {
            //itemView.findViewById<TextView>(R.id.tv_date_solar).text = adapterItemData.beginTime
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when (adapterList[position].link) {
            "header" -> TYPE_HEADER
            "no_flare" -> TYPE_NO_FLR
            "large" -> TYPE_LARGE
            else -> TYPE_SMALL
        }
    }

    private fun toggleType(layoutPosition: Int) {
        if (adapterList[layoutPosition].link == "large")
            adapterList[layoutPosition].link = "small"
        else adapterList[layoutPosition].link = "large"
        notifyItemChanged(layoutPosition)
    }

    private fun fillColoredScale (itemView: View, adapterItemData: SolarFlare) {
        // обнуляем scale, т.к. было замечено сохранение старых значений при переопределении itemView
        itemView.findViewById<CardView>(R.id.view_scale_1of5).isVisible = false
        itemView.findViewById<CardView>(R.id.view_scale_2of5).isVisible = false
        itemView.findViewById<CardView>(R.id.view_scale_3of5).isVisible = false
        itemView.findViewById<CardView>(R.id.view_scale_4of5).isVisible = false
        itemView.findViewById<CardView>(R.id.view_scale_5of5).isVisible = false

        val cType = adapterItemData.classType?.take(1)
        if (cType == "A" || cType == "B" || cType == "C" || cType == "M" || cType == "X") itemView.findViewById<CardView>(R.id.view_scale_1of5).isVisible = true
        if (cType == "B" || cType == "C" || cType == "M" || cType == "X") itemView.findViewById<CardView>(R.id.view_scale_2of5).isVisible = true
        if (cType == "C" || cType == "M" || cType == "X") itemView.findViewById<CardView>(R.id.view_scale_3of5).isVisible = true
        if (cType == "M" || cType == "X") itemView.findViewById<CardView>(R.id.view_scale_4of5).isVisible = true
        if (cType == "X") itemView.findViewById<CardView>(R.id.view_scale_5of5).isVisible = true
    }

    @SuppressLint("SetTextI18n")
    private fun fillSmallDataInRvItem (itemView: View, adapterItemData: SolarFlare) {
        itemView.findViewById<TextView>(R.id.tv_date_solar).text = adapterItemData.peakTime?.substring(11, 16)
        itemView.findViewById<TextView>(R.id.tv_solar_flare_class).text = adapterItemData.classType
    }

    @SuppressLint("SetTextI18n")
    private fun fillDetailedDataInRvItem (itemView: View, adapterItemData: SolarFlare) {
        itemView.findViewById<TextView>(R.id.tv_solar_details).text =
            "beginTime : ${adapterItemData.beginTime}\n" +
                    "peakTime : ${adapterItemData.peakTime}\n" +
                    "endTime : ${adapterItemData.endTime}\n" +
                    "classType : ${adapterItemData.classType}\n" +
                    "sourceLocation : ${adapterItemData.sourceLocation}\n" +
                    "activeRegionNum : ${adapterItemData.activeRegionNum}"
    }

}