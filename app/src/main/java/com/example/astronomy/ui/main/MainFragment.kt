package com.example.astronomy.ui.main

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.astronomy.ui.MyOnClickListener
import com.example.astronomy.ui.apod.ListAPODFragment
import com.project.astronomy.R
import kotlinx.android.synthetic.main.main_fragment.*

class MainFragment : Fragment() {

    companion object {
        fun newInstance() = MainFragment()
    }

    private val viewModel: MainViewModel by lazy {
        ViewModelProvider(this).get(MainViewModel::class.java)
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.main_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        // 1. RV - A Picture Of The Day (APOD)
        rv_apod.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)

        val adapterAPOD = RvAdapterCommon()
        rv_apod.adapter = adapterAPOD

        viewModel.liveDataAPOD.observe(viewLifecycleOwner, Observer {
            adapterAPOD.adapterList = it
        })

        adapterAPOD.myListener = object: MyOnClickListener {
            override fun onMyClicked(view: View) {
                activity?.supportFragmentManager?.beginTransaction()
                    ?.replace(R.id.container, ListAPODFragment())?.addToBackStack(null)
                    ?.commit()
            }
        }

        // 2. RV - Solar Flare
        rv_solar.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        val adapterSolar = RvAdapterCommon()
        rv_solar.adapter = adapterSolar

        viewModel.liveDataSolar.observe(viewLifecycleOwner, Observer {
            adapterSolar.adapterList = it
        })
        // TODO: onClickListener

        // 3. RV - Geomagnetic Storm
        rv_geo.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        val adapterGeo = RvAdapterCommon()
        rv_geo.adapter = adapterGeo

        viewModel.liveDataGeo.observe(viewLifecycleOwner, Observer {
            adapterGeo.adapterList = it
        })
        // TODO: onClickListener

        // 4. RV - Earth Polychromatic Imaging Camera (Epic)
        rv_epic.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        val adapterEpic = RvAdapterCommon()
        rv_epic.adapter = adapterEpic

        viewModel.liveDataEpic.observe(viewLifecycleOwner, Observer {
            adapterEpic.adapterList = it
        })
        // TODO: onClickListener

        // 5. RV - Mars Rovers Photos
        rv_mars.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        val adapterMars = RvAdapterCommon()
        rv_mars.adapter = adapterMars

        viewModel.liveDataMars.observe(viewLifecycleOwner, Observer {
            adapterMars.adapterList = it
        })
        // TODO: onClickListener

    }


}