package com.project.donki.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.project.donki.R
import com.project.donki.entities.SolarFlare

// class FLRRecyclerViewAdapter : BaseRecyclerViewAdapter<SolarFlare>() {
class FLRRecyclerViewAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object {
        private const val TYPE_HEADER = 0
        private const val TYPE_SMALL = 1
    }

    var adapterList: List<SolarFlare> = listOf()
        set(value) {
            field = value
            notifyDataSetChanged()
        }


//    private val flrDiffUtilCallBack = object : DiffUtil.ItemCallback<SolarFlare>() {
//        override fun areItemsTheSame(oldItem: SolarFlare, newItem: SolarFlare): Boolean =
//            oldItem.flrID == newItem.flrID
//
//        override fun areContentsTheSame(oldItem: SolarFlare, newItem: SolarFlare): Boolean =
//            oldItem == newItem
//    }
//    override val differ = AsyncListDiffer(this, flrDiffUtilCallBack)
//*********************






//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FLRViewHolder =
//        FLRViewHolder(ItemRvFlrBinding.inflate(LayoutInflater.from(parent.context), parent, false))
// ****************
    // в этой строке заненен конкретный из этого класса viewHolder на общий из библиотеки
override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder
{
    val myInflater = LayoutInflater.from(parent.context)
    return when (viewType) {
        TYPE_HEADER -> HeadersViewHolder(myInflater.inflate(R.layout.item_rv_flr, parent, false))
        else -> SmallViewHolder(myInflater.inflate(R.layout.item_rv_flr, parent, false))
    }
}

//    class FLRViewHolder(private val viewBinding: ItemRvFlrBinding) : BaseViewHolder<SolarFlare>(viewBinding.root) {
//        override fun bind(solarFlare: SolarFlare) {
//            //TODO: bind solarFlare to view
//        }
//    }
//**************************************************



    // в этой строке заненен конкретный из этого класса viewHolder на общий из библиотеки
    // override fun onBindViewHolder(holder: ViewHolder, position: Int) {
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (getItemViewType(position)) {
            TYPE_HEADER -> {
                holder as HeadersViewHolder
                holder.bind(adapterList[position])
            }
            TYPE_SMALL -> {
                holder as SmallViewHolder
                holder.bind(adapterList[position])
            }
        }
    }

    override fun getItemCount(): Int = adapterList.size

    inner class HeadersViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(adapterItemData: SolarFlare) {
            itemView.findViewById<TextView>(R.id.tv_date_solar).text = adapterItemData.beginTime
        }
    }

    inner class SmallViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(adapterItemData: SolarFlare) {
            itemView.findViewById<TextView>(R.id.tv_date_solar).text = adapterItemData.beginTime
            itemView.findViewById<TextView>(R.id.tv_solar_flare_class).text = adapterItemData.classType

            val cType = adapterItemData.classType?.take(1)
            itemView.findViewById<CardView>(R.id.view_scale_1of5).isVisible = true
            if (cType == "A" || cType == "B" || cType == "C" || cType == "M" || cType == "X") itemView.findViewById<CardView>(R.id.view_scale_1of5).isVisible = true
            if (cType == "B" || cType == "C" || cType == "M" || cType == "X") itemView.findViewById<CardView>(R.id.view_scale_2of5).isVisible = true
            if (cType == "C" || cType == "M" || cType == "X") itemView.findViewById<CardView>(R.id.view_scale_3of5).isVisible = true
            if (cType == "M" || cType == "X") itemView.findViewById<CardView>(R.id.view_scale_4of5).isVisible = true
            if (cType == "X") itemView.findViewById<CardView>(R.id.view_scale_5of5).isVisible = true
        }
    }

    // определяем тип конкретного Item на основе его полей и позиции
    override fun getItemViewType(position: Int): Int {
        return when (adapterList[position].classType) {
            "header" -> TYPE_HEADER
            else -> TYPE_SMALL
        }
    }


}