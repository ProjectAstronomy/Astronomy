package com.example.astronomy.ui.main

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.astronomy.model.entity.ItemRv
import com.example.astronomy.ui.MyOnClickListener
import com.project.astronomy.R
import kotlinx.android.synthetic.main.item_rv_main_common.view.*

class RvAdapterCommon : RecyclerView.Adapter<RvAdapterCommon.ViewHolder> () {

    var adapterList: List<ItemRv> = listOf()
        set(value) {
            field = value
            notifyDataSetChanged()
        }
    var myListener: MyOnClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_rv_main_common, parent, false)
        )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(adapterList[position])
    }

    override fun getItemCount(): Int = adapterList.size


    inner class ViewHolder (itemView: View) : RecyclerView.ViewHolder (itemView) {
        fun bind (adapterItemView: ItemRv) {
            itemView.findViewById<TextView>(R.id.tv_title).text = adapterItemView.title

            when (adapterItemView.imageName) {
                "rv_apod_today" -> itemView.iv_pic.setImageResource(R.drawable.rv_apod_today)
                "rv_apod_before" -> itemView.iv_pic.setImageResource(R.drawable.rv_pod_before)

                "rv_solar_today" -> itemView.iv_pic.setImageResource(R.drawable.rv_solar_today)
                "rv_solar_before" -> itemView.iv_pic.setImageResource(R.drawable.rv_solar_before)
                "rv_solar_forecast" -> itemView.iv_pic.setImageResource(R.drawable.rv_solar_forecast)

                "rv_geo_today" -> itemView.iv_pic.setImageResource(R.drawable.rv_geo_today)
                "rv_geo_before" -> itemView.iv_pic.setImageResource(R.drawable.rv_geo_before)
                "rv_geo_forecast" -> itemView.iv_pic.setImageResource(R.drawable.rv_geo_forecast)

                "rv_epic_today" -> itemView.iv_pic.setImageResource(R.drawable.rv_epic_today)
                "rv_epic_before" -> itemView.iv_pic.setImageResource(R.drawable.rv_epic_before)

                "rv_mars_curiosity" -> itemView.iv_pic.setImageResource(R.drawable.rv_mars_curiosity)
                "rv_mars_spirit" -> itemView.iv_pic.setImageResource(R.drawable.rv_mars_spirit)
                "rv_mars_opportunity" -> itemView.iv_pic.setImageResource(R.drawable.rv_mars_opportunity)
            }

            itemView.setOnClickListener {
                myListener?.onMyClicked (view = it)
            }

        }
    }



}