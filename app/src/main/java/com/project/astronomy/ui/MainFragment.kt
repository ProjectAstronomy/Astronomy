package com.project.astronomy.ui

import android.animation.ValueAnimator
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.TranslateAnimation
import android.widget.RelativeLayout
import androidx.fragment.app.viewModels
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.whenResumed
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.project.astronomy.R
import com.project.astronomy.databinding.MainFragmentBinding
import com.project.astronomy.di.SCOPE_MAIN_MODULE
import com.project.astronomy.entities.ItemRv
import com.project.astronomy.viewmodel.MainViewModel
import com.project.core.net.AndroidNetworkStatus
import com.project.core.ui.BaseFragment
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import org.koin.android.ext.android.getKoin
import org.koin.android.ext.android.inject
import org.koin.core.parameter.parametersOf
import org.koin.core.qualifier.named
import org.koin.core.scope.Scope

class MainFragment : BaseFragment<MainFragmentBinding>(MainFragmentBinding::inflate) {
    private val scopeMainModule: Scope =
        getKoin().getOrCreateScope(SCOPE_MAIN_MODULE, named(SCOPE_MAIN_MODULE))

    private val mainViewModel: MainViewModel by scopeMainModule.inject {
        parametersOf(SavedStateHandle())
    }

    private val androidNetworkStatus: AndroidNetworkStatus by inject()

    private val adapterAPOD by lazy { RvAdapterCommon(::onApodClickListener) }
    private val adapterSolar by lazy { RvAdapterCommon(::onSolarFlareClickListener) }
    private val adapterEPIC by lazy { RvAdapterCommon(::onEpicClickListener) }
    private val adapterMars by lazy { RvAdapterCommon(::onMarsClickListener) }

    // backdrop
    var showBackLayout = false
    var frontLayoutParams: RelativeLayout.LayoutParams? = null


    init {
        lifecycleScope.launch {
            whenResumed {
                androidNetworkStatus.networkState.collect { isNetworkAvailable ->
                    if (!isNetworkAvailable) {
                        Snackbar.make(binding.root, "No internet connection", Snackbar.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return providePersistentView(inflater, container, savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (!hasInitializedRootView) {
            hasInitializedRootView = true
            with(binding.rvApod) {
                layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
                adapter = adapterAPOD
            }
            with(binding.rvSolar) {
                layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
                adapter = adapterSolar
            }
            with(binding.rvEpic) {
                layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
                adapter = adapterEPIC
            }
            with(binding.rvMars) {
                layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
                adapter = adapterMars
            }
            //binding.chipSettings.setOnClickListener { onSettingsClicked() }
        }
        with(mainViewModel) {
            liveDataAPOD.observe(viewLifecycleOwner) { adapterAPOD.adapterList = it }
            liveDataEpic.observe(viewLifecycleOwner) { adapterEPIC.adapterList = it }
            liveDataMars.observe(viewLifecycleOwner) { adapterMars.adapterList = it }
            liveDataSolar.observe(viewLifecycleOwner) { adapterSolar.adapterList = it }
        }

        binding.appBarLayout.findViewById<View>(R.id.main_appbar).findViewById<View>(R.id.settings).setOnClickListener { dropLayout() }
        binding.appBarLayout.findViewById<View>(R.id.main_appbar).setOnClickListener { dropLayout() }
    }





    private fun dropLayout() {
        showBackLayout = !showBackLayout
        frontLayoutParams = binding.frontLayoutMain!!.layoutParams as RelativeLayout.LayoutParams
        if (showBackLayout) {
            val varl = ValueAnimator.ofInt(binding.backLayoutMain!!.height)
            varl.duration = 200
            varl.addUpdateListener { animation ->
                frontLayoutParams!!.setMargins(0, (animation.animatedValue as Int) - 72, 0, 0)
                binding.frontLayoutMain!!.layoutParams = frontLayoutParams
            }
            varl.start()
        } else {
            frontLayoutParams!!.setMargins(0, 0, 0, 0)
            binding.frontLayoutMain!!.layoutParams = frontLayoutParams
            val anim = TranslateAnimation(
                0F, 0F,
                binding.backLayoutMain!!.height.toFloat(), 0F
            )
            anim.startOffset = 0
            anim.duration = 200
            binding.frontLayoutMain!!.startAnimation(anim)
        }
    }






    override fun onDestroy() {
        super.onDestroy()
        scopeMainModule.close()
    }

    private fun onApodClickListener(itemRv: ItemRv) {
        findNavController().navigate(R.id.action_main_fragment_to_navigation_apod)
    }

    private fun onSolarFlareClickListener(itemRv: ItemRv) {
        if (itemRv.title == "Solar Flare")
            findNavController().navigate(R.id.action_main_fragment_to_navigation_flr)
        else
            findNavController().navigate(R.id.action_main_fragment_to_navigation_gst)
    }

    private fun onEpicClickListener(itemRv: ItemRv) {
        findNavController().navigate(R.id.action_main_fragment_to_navigation_epic)
    }

    private fun onMarsClickListener(itemRv: ItemRv) {
        val action = MainFragmentDirections.actionMainFragmentToNavigationMrp(itemRv.title)
        findNavController().navigate(action)
    }

    private fun onSettingsClicked() {
        findNavController().navigate(R.id.action_main_fragment_to_fragment_preferences)
    }
}