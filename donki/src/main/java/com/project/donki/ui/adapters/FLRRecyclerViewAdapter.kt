package com.project.donki.ui.adapters

import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import com.project.core.ui.BaseRecyclerViewAdapter
import com.project.donki.databinding.ItemRvFlrDetailedBinding
import com.project.donki.databinding.ItemRvFlrHeaderBinding
import com.project.donki.databinding.ItemRvFlrNoDataBinding
import com.project.donki.entities.local.adapteritems.flr.*
import com.project.donki.entities.remote.SolarFlare

class FLRRecyclerViewAdapter(
    private val onSolarFlareClicked: (SolarFlare) -> Unit
) : BaseRecyclerViewAdapter<ISolarFlareAdapterItem>() {
    companion object {
        private const val TYPE_NO_FLR = 0
        private const val TYPE_HEADER = 1
        private const val TYPE_DETAILED = 2
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
            else -> DetailedViewHolder(
                ItemRvFlrDetailedBinding.inflate(
                    LayoutInflater.from(
                        parent.context
                    ), parent, false
                )
            )
        }
    }

    override fun onBindViewHolder(holder: BaseViewHolder<ISolarFlareAdapterItem>, position: Int) {
        when (getItemViewType(position)) {
            TYPE_HEADER -> holder as HeadersViewHolder
            TYPE_DETAILED -> holder as DetailedViewHolder
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

    inner class DetailedViewHolder(private val viewBinding: ItemRvFlrDetailedBinding) :
        BaseViewHolder<ISolarFlareAdapterItem>(viewBinding.root) {
        override fun bind(adapterItemData: ISolarFlareAdapterItem) {
            adapterItemData as SolarFlareAdapterItemDetailed
            fillSmallDataInRvItem(viewBinding, adapterItemData)
            viewBinding.tvDateSolarDetailed.isVisible = false
            viewBinding.tvSolarDetails.isVisible = false
            itemView.setOnClickListener {
                onSolarFlareClicked(adapterItemData.solarFlare)
                if (viewBinding.tvSolarDetails.visibility == View.VISIBLE) {
                    viewBinding.tvDateSolarDetailed.isVisible = false
                    viewBinding.tvSolarDetails.isVisible = false
                    viewBinding.ivInfoClose.isVisible = true
                    viewBinding.ivInfoOpen.isVisible = false
                } else {
                    viewBinding.tvDateSolarDetailed.isVisible = true
                    viewBinding.tvSolarDetails.isVisible = true
                    viewBinding.ivInfoClose.isVisible = false
                    viewBinding.ivInfoOpen.isVisible = true
                    fillDetailedDataInRvItem(viewBinding, adapterItemData)
                }
            }
        }
    }

    inner class NoFlareViewHolder(viewBinding: ItemRvFlrNoDataBinding) :
        BaseViewHolder<ISolarFlareAdapterItem>(viewBinding.root) {
        override fun bind(adapterItemData: ISolarFlareAdapterItem) {
            adapterItemData as SolarFlareAdapterItemNoFlare
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when (items[position]) {
            is SolarFlareAdapterItemHeader -> TYPE_HEADER
            is SolarFlareAdapterItemNoFlare -> TYPE_NO_FLR
            else -> TYPE_DETAILED
        }
    }

    private fun fillColoredScale(viewBinding: ItemRvFlrDetailedBinding, adapterItemData: SolarFlareAdapterItemDetailed) {
        // ???????????????? scale, ??.??. ???????? ???????????????? ???????????????????? ???????????? ???????????????? ?????? ?????????????????????????????? itemView
        viewBinding.viewScale1of5.isVisible = false
        viewBinding.viewScale2of5.isVisible = false
        viewBinding.viewScale3of5.isVisible = false
        viewBinding.viewScale4of5.isVisible = false
        viewBinding.viewScale5of5.isVisible = false

        val cType = adapterItemData.solarFlare.classType?.take(1)
        Log.d("TAG", "***************++++++++++ $cType")
        if (cType == "A" || cType == "B" || cType == "C" || cType == "M" || cType == "X") viewBinding.viewScale1of5.isVisible = true
        if (cType == "B" || cType == "C" || cType == "M" || cType == "X") viewBinding.viewScale2of5.isVisible = true
        if (cType == "C" || cType == "M" || cType == "X") viewBinding.viewScale3of5.isVisible = true
        if (cType == "M" || cType == "X") viewBinding.viewScale4of5.isVisible = true
        if (cType == "X") viewBinding.viewScale5of5.isVisible = true
    }

    @SuppressLint("SetTextI18n")
    private fun fillSmallDataInRvItem(viewBinding: ItemRvFlrDetailedBinding,
                                      adapterItemData: SolarFlareAdapterItemDetailed
    ) {
        viewBinding.tvDateSolar.text = adapterItemData.solarFlare.peakTime?.substring(11, 16)
        viewBinding.tvSolarFlareClass.text = adapterItemData.solarFlare.classType
        fillColoredScale(viewBinding, adapterItemData)
    }

    @SuppressLint("SetTextI18n")
    private fun fillDetailedDataInRvItem(viewBinding: ItemRvFlrDetailedBinding,
                                         adapterItemData: SolarFlareAdapterItemDetailed
    ) {
        viewBinding.tvSolarDetails.text =
                    "beginTime : ${adapterItemData.solarFlare.beginTime}\n" +
                    "peakTime : ${adapterItemData.solarFlare.peakTime}\n" +
                    "endTime : ${adapterItemData.solarFlare.endTime}\n" +
                    "classType : ${adapterItemData.solarFlare.classType}\n" +
                    "sourceLocation : ${adapterItemData.solarFlare.sourceLocation}\n" +
                    "activeRegionNum : ${adapterItemData.solarFlare.activeRegionNum}"
    }
}