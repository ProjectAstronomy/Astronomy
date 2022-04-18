package com.example.astronomy.ui.apod

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.astronomy.ui.MyOnClickListener
import com.project.astronomy.R
import kotlinx.android.synthetic.main.list_apod_fragment.*

class ListAPODFragment : Fragment() {

    companion object {
        fun newInstance() = ListAPODFragment()
    }

    private lateinit var viewModel: ListAPODViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.list_apod_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this).get(ListAPODViewModel::class.java)


        rv_list_apod_vertical.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        val adapterAPODVertical = RvAdapterVertical()
        rv_list_apod_vertical.adapter = adapterAPODVertical

        viewModel.liveDataAPODVertical.observe(viewLifecycleOwner, Observer {
            adapterAPODVertical.adapterList = it
        })

        adapterAPODVertical.myListener = object : MyOnClickListener {
            override fun onMyClicked(rv_item_view: View) {
                activity?.supportFragmentManager?.beginTransaction()
                    ?.replace(R.id.container, OneAPODFragment())?.addToBackStack(null)
                    ?.commit()
            }
        }
    }
}