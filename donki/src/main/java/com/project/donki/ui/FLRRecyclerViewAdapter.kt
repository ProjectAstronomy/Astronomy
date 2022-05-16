package com.project.donki.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.core.view.isVisible
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.project.core.ui.BaseRecyclerViewAdapter
import com.project.donki.R
import com.project.donki.entities.SolarFlare

class FLRRecyclerViewAdapter : BaseRecyclerViewAdapter<SolarFlare>() {

    companion object {
        private const val TYPE_HEADER = 0
        private const val TYPE_SMALL = 1
        private const val TYPE_NO_FLR = 2
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
//*********************




//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FLRViewHolder =
//        FLRViewHolder(ItemRvFlrBinding.inflate(LayoutInflater.from(parent.context), parent, false))
// ****************
    // в этой строке заненен конкретный из этого класса viewHolder на общий из библиотеки
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<SolarFlare>
    {
        val myInflater = LayoutInflater.from(parent.context)
        return when (viewType) {
            TYPE_HEADER -> HeadersViewHolder(myInflater.inflate(R.layout.item_rv_flr_header, parent, false))
            TYPE_NO_FLR -> NoFlareViewHolder(myInflater.inflate(R.layout.item_rv_flr_no_data, parent, false))
            else -> SmallViewHolder(myInflater.inflate(R.layout.item_rv_flr, parent, false))
        }
    }


    // в этой строке заненен конкретный из этого класса viewHolder на общий из библиотеки
    // override fun onBindViewHolder(holder: ViewHolder, position: Int) {
    override fun onBindViewHolder(holder: BaseViewHolder<SolarFlare>, position: Int) {
        when (getItemViewType(position)) {
            TYPE_HEADER -> {
                holder as HeadersViewHolder
                holder.bind(adapterList[position])
            }
            TYPE_SMALL -> {
                holder as SmallViewHolder
                holder.bind(adapterList[position])
            }
            TYPE_NO_FLR -> {
                holder as NoFlareViewHolder
                holder.bind(adapterList[position])
            }
        }
    }

    override fun getItemCount(): Int = adapterList.size



//    class FLRViewHolder(private val viewBinding: ItemRvFlrBinding) : BaseViewHolder<SolarFlare>(viewBinding.root) {
//        override fun bind(solarFlare: SolarFlare) {
//            //TODO: bind solarFlare to view
//        }
//    }
//**************************************************



    inner class HeadersViewHolder(itemView: View) : BaseViewHolder<SolarFlare>(itemView) {
        override fun bind(adapterItemData: SolarFlare) {
            itemView.findViewById<TextView>(R.id.tv_date_solar).text = adapterItemData.beginTime
        }
    }

    inner class SmallViewHolder(itemView: View) : BaseViewHolder<SolarFlare>(itemView) {
        override fun bind(adapterItemData: SolarFlare) {
            itemView.findViewById<TextView>(R.id.tv_date_solar).text = adapterItemData.beginTime?.substring(11, 16)
            itemView.findViewById<TextView>(R.id.tv_solar_flare_class).text = adapterItemData.classType

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
    }

    inner class NoFlareViewHolder(itemView: View) : BaseViewHolder<SolarFlare>(itemView) {
        override fun bind(adapterItemData: SolarFlare) {
            //itemView.findViewById<TextView>(R.id.tv_date_solar).text = adapterItemData.beginTime
        }
    }

    // определяем тип конкретного Item на основе его полей и позиции
    override fun getItemViewType(position: Int): Int {
        return when (adapterList[position].classType) {
            "header" -> TYPE_HEADER
            "no_flare" -> TYPE_NO_FLR
            else -> TYPE_SMALL
        }
    }

}