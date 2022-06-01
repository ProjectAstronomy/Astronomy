package com.project.astronomy.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.project.astronomy.entities.ItemRv
import com.project.astronomy.R
import com.project.astronomy.databinding.ItemRvMainCommonBinding
import com.project.astronomy.repository.MainRepository

class RvAdapterCommon(
    private val onItemClickListener: (ItemRv) -> Unit
) : RecyclerView.Adapter<RvAdapterCommon.ViewHolder>() {
    var adapterList: List<ItemRv> = listOf()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(ItemRvMainCommonBinding.inflate(LayoutInflater.from(parent.context), parent, false))

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(adapterList[position])
    }

    override fun getItemCount(): Int = adapterList.size

    inner class ViewHolder(private val viewBinding: ItemRvMainCommonBinding) :
        RecyclerView.ViewHolder(viewBinding.root) {

        fun bind(adapterItemView: ItemRv) {
            viewBinding.tvTitle.text = adapterItemView.title

            when (adapterItemView.imageName) {
                MainRepository.RV_APOD_TODAY -> viewBinding.ivPic.setImageResource(R.drawable.rv_apod_today)

                MainRepository.RV_SOLAR_TODAY -> viewBinding.ivPic.setImageResource(R.drawable.rv_solar)
                MainRepository.RV_GEO_TODAY -> viewBinding.ivPic.setImageResource(R.drawable.rv_geo_today)

                MainRepository.RV_EPIC_TODAY -> viewBinding.ivPic.setImageResource(R.drawable.rv_epic_today)
                MainRepository.RV_EPIC_BEFORE -> viewBinding.ivPic.setImageResource(R.drawable.rv_epic_before)

                MainRepository.RV_MARS_CURIOSITY -> viewBinding.ivPic.setImageResource(R.drawable.rv_mars_curiosity)
                MainRepository.RV_MARS_SPIRIT -> viewBinding.ivPic.setImageResource(R.drawable.rv_mars_spirit)
                MainRepository.RV_MARS_OPPORTUNITY -> viewBinding.ivPic.setImageResource(R.drawable.rv_mars_opportunity)
            }

            itemView.setOnClickListener {
                onItemClickListener(adapterItemView)
            }
        }
    }
}