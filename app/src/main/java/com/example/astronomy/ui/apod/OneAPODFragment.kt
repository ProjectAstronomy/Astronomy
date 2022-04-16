package com.example.astronomy.ui.apod

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.astronomy.model.RepositoryRv
import com.project.astronomy.R
import kotlinx.android.synthetic.main.one_apod_fragment.*

class OneAPODFragment : Fragment() {

    companion object {
        fun newInstance() = OneAPODFragment()
    }

    private val viewModel: OneAPODViewModel by lazy {
        ViewModelProvider(this).get(OneAPODViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.one_apod_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // заглушка. взял напрямую в model. было лень прописывать через viewModel
        val aPODResponseTEMP = RepositoryRv.getListAPODResponseTEMP()
        tv_title_apod.text = aPODResponseTEMP[0].title
        tv_copyright_apod.text = "\u00A9 ${aPODResponseTEMP[0].copyright}"
        tv_date_apod.text = aPODResponseTEMP[0].date
        tv_explanation_apod.text = aPODResponseTEMP[0].explanation
        iv_url_apod.setImageResource(R.drawable.apod_temp)
    }
}