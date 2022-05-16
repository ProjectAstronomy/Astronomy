package com.project.donki.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.core.view.isVisible
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import com.project.core.ui.BaseRecyclerViewAdapter
import com.project.donki.R
import com.project.donki.entities.GeomagneticStorm
import com.project.donki.entities.SolarFlare

class GSTRecyclerViewAdapter : BaseRecyclerViewAdapter<GeomagneticStorm>() {
//    private val gstDiffUtilCallBack = object : DiffUtil.ItemCallback<GeomagneticStorm>() {

        companion object {
            private const val TYPE_HEADER1 = 0
            private const val TYPE_SMALL1 = 1
            private const val TYPE_NO_FLR1 = 2
        }

        var adapterListGST: List<GeomagneticStorm> = listOf()
            set(value) {
                field = value
                notifyDataSetChanged()
            }

    private val gstDiffUtilCallBack = object : DiffUtil.ItemCallback<GeomagneticStorm>() {
        override fun areItemsTheSame(
            oldItem: GeomagneticStorm,
            newItem: GeomagneticStorm
        ): Boolean =
            oldItem.gstID == newItem.gstID

        override fun areContentsTheSame(
            oldItem: GeomagneticStorm,
            newItem: GeomagneticStorm
        ): Boolean =
            oldItem == newItem
    }

    override val differ = AsyncListDiffer(this, gstDiffUtilCallBack)
//************************



//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GSTViewHolder =
//        GSTViewHolder(ItemRvGstBinding.inflate(LayoutInflater.from(parent.context), parent, false))

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<GeomagneticStorm>
    {
        val myInflater = LayoutInflater.from(parent.context)
        return when (viewType) {
            TYPE_HEADER1 -> HeadersViewHolder(myInflater.inflate(R.layout.item_rv_flr_header, parent, false))
            TYPE_NO_FLR1 -> NoFlareViewHolder(myInflater.inflate(R.layout.item_rv_flr_no_data, parent, false))
            else -> SmallViewHolder(myInflater.inflate(R.layout.item_rv_flr, parent, false))
        }
    }

    override fun onBindViewHolder(holder: BaseViewHolder<GeomagneticStorm>, position: Int) {

        when (getItemViewType(position)) {
            TYPE_HEADER1 -> {
                holder as HeadersViewHolder
                holder.bind(adapterListGST[position])
            }
            TYPE_SMALL1 -> {
                holder as SmallViewHolder
                holder.bind(adapterListGST[position])
            }
            TYPE_NO_FLR1 -> {
                holder as NoFlareViewHolder
                holder.bind(adapterListGST[position])
            }
        }
    }

    override fun getItemCount(): Int = adapterListGST.size



//    class GSTViewHolder(private val viewBinding: ItemRvGstBinding) : BaseViewHolder<GeomagneticStorm>(viewBinding.root) {
//        override fun bind(geomagneticStorm: GeomagneticStorm) {
//            //TODO: bind solarFlare to view
//        }
//    }



    inner class HeadersViewHolder(itemView: View) : BaseViewHolder<GeomagneticStorm>(itemView) {
        override fun bind(adapterItemData: GeomagneticStorm) {
            itemView.findViewById<TextView>(R.id.tv_date_solar).text = adapterItemData.startTime
        }
    }

    inner class SmallViewHolder(itemView: View) : BaseViewHolder<GeomagneticStorm>(itemView) {
        override fun bind(adapterItemData: GeomagneticStorm) {
            itemView.findViewById<TextView>(R.id.tv_date_solar).text = adapterItemData.startTime
            itemView.findViewById<TextView>(R.id.tv_solar_flare_class).text = adapterItemData.link

            // обнуляем scale, т.к. было замечено сохранение старых значений при переопределении itemView
            itemView.findViewById<CardView>(R.id.view_scale_1of5).isVisible = false
            itemView.findViewById<CardView>(R.id.view_scale_2of5).isVisible = false
            itemView.findViewById<CardView>(R.id.view_scale_3of5).isVisible = false
            itemView.findViewById<CardView>(R.id.view_scale_4of5).isVisible = false
            itemView.findViewById<CardView>(R.id.view_scale_5of5).isVisible = false
//
            val cType = adapterItemData.link?.toInt()
            if (cType != null) {
                if (cType > 2) itemView.findViewById<CardView>(R.id.view_scale_1of5).isVisible = true
                if (cType > 3) itemView.findViewById<CardView>(R.id.view_scale_2of5).isVisible = true
                if (cType > 4) itemView.findViewById<CardView>(R.id.view_scale_3of5).isVisible = true
                if (cType > 5) itemView.findViewById<CardView>(R.id.view_scale_4of5).isVisible = true
                if (cType > 6) itemView.findViewById<CardView>(R.id.view_scale_5of5).isVisible = true
            }
        }
    }

    inner class NoFlareViewHolder(itemView: View) : BaseViewHolder<GeomagneticStorm>(itemView) {
        override fun bind(adapterItemData: GeomagneticStorm) {
            //itemView.findViewById<TextView>(R.id.tv_date_solar).text = adapterItemData.beginTime
        }
    }

    // определяем тип конкретного Item на основе его полей и позиции
    override fun getItemViewType(position: Int): Int {
        return when (adapterListGST[position].link) {
            "header" -> TYPE_HEADER1
            "no_flare" -> TYPE_NO_FLR1
            else -> TYPE_SMALL1
        }
    }


}