package com.project.donki.ui.adapters

import android.annotation.SuppressLint
import android.opengl.Visibility
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.viewbinding.ViewBinding
import com.project.core.ui.BaseRecyclerViewAdapter
import com.project.donki.entities.remote.SolarFlare
import com.project.donki.R
import com.project.donki.databinding.ItemRvFlrBinding
import com.project.donki.databinding.ItemRvFlrDetailedBinding
import com.project.donki.databinding.ItemRvFlrHeaderBinding
import com.project.donki.databinding.ItemRvFlrNoDataBinding
import com.project.donki.entities.local.adapteritems.flr.*

class FLRRecyclerViewAdapter(
    private val onSolarFlareClicked: (SolarFlare) -> Unit
) : BaseRecyclerViewAdapter<ISolarFlareAdapterItem>() {
    companion object {
        private const val TYPE_NO_FLR = 0
        private const val TYPE_HEADER = 1
        private const val TYPE_SMALL = 2
        private const val TYPE_LARGE = 3
    }

    private val flrDiffUtilCallBack = object : DiffUtil.ItemCallback<ISolarFlareAdapterItem>() {
        override fun areItemsTheSame(
            oldItem: ISolarFlareAdapterItem,
            newItem: ISolarFlareAdapterItem
        ): Boolean =
            oldItem.hashCode() == newItem.hashCode()

        @SuppressLint("DiffUtilEquals")
        override fun areContentsTheSame(
            oldItem: ISolarFlareAdapterItem,
            newItem: ISolarFlareAdapterItem
        ): Boolean =
            oldItem == newItem
    }

    override val differ = AsyncListDiffer(this, flrDiffUtilCallBack)

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): BaseViewHolder<ISolarFlareAdapterItem> {
        return when (viewType) {
            TYPE_HEADER -> HeadersViewHolder(
                ItemRvFlrHeaderBinding.inflate(
                    LayoutInflater.from(
                        parent.context
                    ), parent, false
                )
            )
            TYPE_NO_FLR -> NoFlareViewHolder(
                ItemRvFlrNoDataBinding.inflate(
                    LayoutInflater.from(
                        parent.context
                    ), parent, false
                )
            )
            TYPE_LARGE -> LargeViewHolder(
                ItemRvFlrDetailedBinding.inflate(
                    LayoutInflater.from(
                        parent.context
                    ), parent, false
                )
            )
            else -> SmallViewHolder(
                ItemRvFlrBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )
        }
    }

    override fun onBindViewHolder(holder: BaseViewHolder<ISolarFlareAdapterItem>, position: Int) {
        when (getItemViewType(position)) {
            TYPE_HEADER -> holder as HeadersViewHolder
            TYPE_SMALL -> holder as SmallViewHolder
            TYPE_LARGE -> holder as LargeViewHolder
            TYPE_NO_FLR -> holder as NoFlareViewHolder
        }
        _isNeededToLoadInFlow.value = (position * 100 / itemCount) > 80
        holder.bind(items[position])
    }

    inner class HeadersViewHolder(private val viewBinding: ItemRvFlrHeaderBinding) :
        BaseViewHolder<ISolarFlareAdapterItem>(viewBinding.root) {
        override fun bind(adapterItemData: ISolarFlareAdapterItem) {
            adapterItemData as SolarFlareAdapterItemHeader
            viewBinding.tvDateSolar.text = adapterItemData.beginTime
        }
    }

    inner class SmallViewHolder(private val viewBinding: ItemRvFlrBinding) :
        BaseViewHolder<ISolarFlareAdapterItem>(viewBinding.root) {
        override fun bind(adapterItemData: ISolarFlareAdapterItem) {
            adapterItemData as SolarFlareAdapterItemSmall
            fillSmallDataInRvItem(viewBinding, adapterItemData)
            itemView.setOnClickListener {
                //onSolarFlareClicked(adapterItemData)
                adapterItemData as? SolarFlareAdapterItemLarge
                items.removeAt(layoutPosition)

                notifyDataSetChanged()
                //toggleType(viewBinding, adapterItemData)
            }
        }
    }

    inner class LargeViewHolder(private val viewBinding: ItemRvFlrDetailedBinding) :
        BaseViewHolder<ISolarFlareAdapterItem>(viewBinding.root) {
        override fun bind(adapterItemData: ISolarFlareAdapterItem) {
            adapterItemData as SolarFlareAdapterItemLarge
            Log.d("TAG", "************** LargeViewHolder ")
            //fillSmallDataInRvItem(viewBinding, adapterItemData)
            fillDetailedDataInRvItem(viewBinding, adapterItemData)
            itemView.setOnClickListener {
                if (viewBinding.tvSolarDetails.visibility == View.VISIBLE) {
                    viewBinding.tvDateSolarDetailed.isVisible = false
                    viewBinding.tvSolarDetails.isVisible = false
                } else {
                    viewBinding.tvDateSolarDetailed.isVisible = true
                    viewBinding.tvSolarDetails.isVisible = true
                }

//                toggleType(layoutPosition)
            }
        }
    }

    inner class NoFlareViewHolder(private val viewBinding: ItemRvFlrNoDataBinding) :
        BaseViewHolder<ISolarFlareAdapterItem>(viewBinding.root) {
        override fun bind(adapterItemData: ISolarFlareAdapterItem) {
            adapterItemData as SolarFlareAdapterItemNoFlare
            //viewBinding.tvDateSolar.text = adapterItemData.beginTime
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when (items[position]) {
            is SolarFlareAdapterItemHeader -> TYPE_HEADER
            is SolarFlareAdapterItemNoFlare -> TYPE_NO_FLR
            is SolarFlareAdapterItemLarge -> TYPE_LARGE
            else -> TYPE_SMALL
        }
    }

//    private fun toggleType(viewBinding: ViewBinding, adapterItemData: ISolarFlareAdapterItem) {
//        if (adapterItemData is SolarFlareAdapterItemSmall)
//        //    println("rrrrrrrrrrrrrrrrrrrrrrr${items[layoutPosition]}__")
//        items[layoutPosition] as SolarFlareAdapterItemLarge
//        else items[layoutPosition] as SolarFlareAdapterItemSmall
//        notifyItemChanged(layoutPosition)
//    }

    private fun fillColoredScale(
        viewBinding: ViewBinding,
        adapterItemData: ISolarFlareAdapterItem
    ) {
        adapterItemData as SolarFlareAdapterItemSmall
        // обнуляем scale, т.к. было замечено сохранение старых значений при переопределении itemView
        viewBinding.root.findViewById<TextView>(R.id.view_scale_1of5).isVisible = false
        viewBinding.root.findViewById<TextView>(R.id.view_scale_2of5).isVisible = false
        viewBinding.root.findViewById<TextView>(R.id.view_scale_3of5).isVisible = false
        viewBinding.root.findViewById<TextView>(R.id.view_scale_4of5).isVisible = false
        viewBinding.root.findViewById<TextView>(R.id.view_scale_5of5).isVisible = false

        val cType = adapterItemData.classType?.take(1)
        Log.d("TAG", "***************++++++++++ $cType")
        if (cType == "A" || cType == "B" || cType == "C" || cType == "M" || cType == "X")
            viewBinding.root.findViewById<TextView>(R.id.view_scale_1of5).isVisible = true
        if (cType == "B" || cType == "C" || cType == "M" || cType == "X")
            viewBinding.root.findViewById<TextView>(R.id.view_scale_2of5).isVisible = true
        if (cType == "C" || cType == "M" || cType == "X")
            viewBinding.root.findViewById<TextView>(R.id.view_scale_3of5).isVisible = true
        if (cType == "M" || cType == "X")
            viewBinding.root.findViewById<TextView>(R.id.view_scale_4of5).isVisible = true
        if (cType == "X")
            viewBinding.root.findViewById<TextView>(R.id.view_scale_5of5).isVisible = true
    }

    @SuppressLint("SetTextI18n")
    private fun fillSmallDataInRvItem(viewBinding: ViewBinding,
                                      adapterItemData: SolarFlareAdapterItemSmall
    ) {
        //adapterItemData as SolarFlareAdapterItemSmall
        viewBinding.root.findViewById<TextView>(R.id.tv_date_solar).text =
            adapterItemData.peakTime?.substring(11, 16)
        viewBinding.root.findViewById<TextView>(R.id.tv_solar_flare_class).text =
            adapterItemData.classType
        fillColoredScale(viewBinding, adapterItemData)
    }

    @SuppressLint("SetTextI18n")
    private fun fillDetailedDataInRvItem(viewBinding: ItemRvFlrDetailedBinding,
                                         adapterItemData: SolarFlareAdapterItemLarge
    ) {
        viewBinding.tvSolarDetails.text =
                    "beginTime : ${adapterItemData.beginTime}\n" +
                    "peakTime : ${adapterItemData.peakTime}\n" +
                    "endTime : ${adapterItemData.endTime}\n" +
                    "classType : ${adapterItemData.classType}\n" +
                    "sourceLocation : ${adapterItemData.sourceLocation}\n" +
                    "activeRegionNum : ${adapterItemData.activeRegionNum}"
    }

}