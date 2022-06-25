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
import com.project.donki.entities.local.adapteritems.gst.GstAdapterItemDetailed
import com.project.donki.entities.local.adapteritems.gst.GstAdapterItemHeader
import com.project.donki.entities.local.adapteritems.gst.IGstAdapterItem
import com.project.donki.entities.remote.GeomagneticStorm

class GeoRecyclerViewAdapter(
    private val onGeoClicked: (GeomagneticStorm) -> Unit
) : BaseRecyclerViewAdapter<IGstAdapterItem>() {
    companion object {
        private const val TYPE_HEADER = 1
        private const val TYPE_DETAILED = 2
    }

    private val flrDiffUtilCallBack = object : DiffUtil.ItemCallback<IGstAdapterItem>() {
        override fun areItemsTheSame(
            oldItem: IGstAdapterItem,
            newItem: IGstAdapterItem
        ): Boolean =
            oldItem.hashCode() == newItem.hashCode()

        @SuppressLint("DiffUtilEquals")
        override fun areContentsTheSame(
            oldItem: IGstAdapterItem,
            newItem: IGstAdapterItem
        ): Boolean =
            oldItem == newItem
    }

    override val differ = AsyncListDiffer(this, flrDiffUtilCallBack)

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): BaseViewHolder<IGstAdapterItem> {
        return when (viewType) {
            TYPE_HEADER -> HeadersViewHolder(
                ItemRvFlrHeaderBinding.inflate(
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

    override fun onBindViewHolder(holder: BaseViewHolder<IGstAdapterItem>, position: Int) {
        when (getItemViewType(position)) {
            TYPE_HEADER -> holder as HeadersViewHolder
            TYPE_DETAILED -> holder as DetailedViewHolder
        }
        _isNeededToLoadInFlow.value = (position * 100 / itemCount) > 80
        holder.bind(items[position])
    }

    inner class HeadersViewHolder(private val viewBinding: ItemRvFlrHeaderBinding) :
        BaseViewHolder<IGstAdapterItem>(viewBinding.root) {
        override fun bind(adapterItemData: IGstAdapterItem) {
            adapterItemData as GstAdapterItemHeader
            viewBinding.tvDateSolar.text = adapterItemData.beginTime
        }
    }

    inner class DetailedViewHolder(private val viewBinding: ItemRvFlrDetailedBinding) :
        BaseViewHolder<IGstAdapterItem>(viewBinding.root) {
        override fun bind(adapterItemData: IGstAdapterItem) {
            adapterItemData as GstAdapterItemDetailed
            fillSmallDataInRvItem(viewBinding, adapterItemData)
            viewBinding.tvDateSolarDetailed.isVisible = false
            viewBinding.tvSolarDetails.isVisible = false
            itemView.setOnClickListener {
                //onGeoClicked()
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

    override fun getItemViewType(position: Int): Int {
        return when (items[position]) {
            is GstAdapterItemHeader -> TYPE_HEADER
            else -> TYPE_DETAILED
        }
    }

    private fun fillColoredScale(viewBinding: ItemRvFlrDetailedBinding, adapterItemData: GstAdapterItemDetailed) {
        // обнуляем scale, т.к. было замечено сохранение старых значений при переопределении itemView
        viewBinding.viewScale1of5.isVisible = false
        viewBinding.viewScale2of5.isVisible = false
        viewBinding.viewScale3of5.isVisible = false
        viewBinding.viewScale4of5.isVisible = false
        viewBinding.viewScale5of5.isVisible = false

        val cType = adapterItemData.kpIndex?.toInt()
        Log.d("TAG", "***************++++++++++ $cType")
        if (cType != null) {
            if (cType > 2) viewBinding.viewScale1of5.isVisible = true
            if (cType > 3) viewBinding.viewScale2of5.isVisible = true
            if (cType > 4) viewBinding.viewScale3of5.isVisible = true
            if (cType > 5) viewBinding.viewScale4of5.isVisible = true
            if (cType > 6) viewBinding.viewScale5of5.isVisible = true
        }
    }

    @SuppressLint("SetTextI18n")
    private fun fillSmallDataInRvItem(viewBinding: ItemRvFlrDetailedBinding,
                                      adapterItemData: GstAdapterItemDetailed
    ) {
        viewBinding.tvDateSolar.text = adapterItemData.observedTime?.substring(11, 16)
        viewBinding.tvSolarFlareClass.text = adapterItemData.kpIndex.toString()
        fillColoredScale(viewBinding, adapterItemData)
    }

    @SuppressLint("SetTextI18n")
    private fun fillDetailedDataInRvItem(viewBinding: ItemRvFlrDetailedBinding,
                                         adapterItemData: GstAdapterItemDetailed
    ) {
        viewBinding.tvSolarDetails.text =
            "startTime : ${adapterItemData.startTime}\n" +
                    "observedTime : ${adapterItemData.observedTime}\n" +
                    "kpIndex : ${adapterItemData.kpIndex}\n" +
                    "source : ${adapterItemData.source}\n"

    }
}