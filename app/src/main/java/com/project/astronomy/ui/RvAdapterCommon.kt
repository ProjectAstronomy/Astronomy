package com.project.astronomy.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.project.astronomy.entities.ItemRv
import com.project.astronomy.R
import com.project.astronomy.databinding.ItemRvMainCommonBinding

class RvAdapterCommon : RecyclerView.Adapter<RvAdapterCommon.ViewHolder>() {
    var adapterList: List<ItemRv> = listOf()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    var myListener: MyOnClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(
            ItemRvMainCommonBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(adapterList[position])
    }

    override fun getItemCount(): Int = adapterList.size

    inner class ViewHolder(private val viewBinding: ItemRvMainCommonBinding) :
        RecyclerView.ViewHolder(viewBinding.root) {

        fun bind(adapterItemView: ItemRv) {
            itemView.findViewById<TextView>(R.id.tv_title).text = adapterItemView.title

            when (adapterItemView.imageName) {
                "rv_apod_today" -> viewBinding.ivPic.setImageResource(R.drawable.rv_apod_today)

                "rv_solar_today" -> viewBinding.ivPic.setImageResource(R.drawable.rv_solar_today)
                "rv_solar_before" -> viewBinding.ivPic.setImageResource(R.drawable.rv_solar_before)
                "rv_solar_forecast" -> viewBinding.ivPic.setImageResource(R.drawable.rv_solar_forecast)

                "rv_geo_today" -> viewBinding.ivPic.setImageResource(R.drawable.rv_geo_today)
                "rv_geo_before" -> viewBinding.ivPic.setImageResource(R.drawable.rv_geo_before)
                "rv_geo_forecast" -> viewBinding.ivPic.setImageResource(R.drawable.rv_geo_forecast)

                "rv_epic_today" -> viewBinding.ivPic.setImageResource(R.drawable.rv_epic_today)
                "rv_epic_before" -> viewBinding.ivPic.setImageResource(R.drawable.rv_epic_before)

                "rv_mars_curiosity" -> viewBinding.ivPic.setImageResource(R.drawable.rv_mars_curiosity)
                "rv_mars_spirit" -> viewBinding.ivPic.setImageResource(R.drawable.rv_mars_spirit)
                "rv_mars_opportunity" -> viewBinding.ivPic.setImageResource(R.drawable.rv_mars_opportunity)
            }

            itemView.setOnClickListener {
                myListener?.onMyClicked(view = it)
            }

        }
    }


}