package com.project.astronomy.ui

import android.animation.ValueAnimator
import android.annotation.SuppressLint
import android.content.res.Resources
import android.graphics.Color
import android.os.Bundle
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.view.animation.TranslateAnimation
import android.widget.RelativeLayout
import androidx.annotation.ColorInt
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.whenResumed
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.project.astronomy.BuildConfig
import com.project.astronomy.R
import com.project.astronomy.databinding.MainFragmentBinding
import com.project.astronomy.di.SCOPE_MAIN_MODULE
import com.project.astronomy.entities.ItemRv
import com.project.astronomy.viewmodel.MainViewModel
import com.project.core.entities.ApplicationIcon
import com.project.core.entities.ApplicationTheme
import com.project.core.entities.ImageResolution
import com.project.core.net.AndroidNetworkStatus
import com.project.core.ui.BaseFragment
import com.project.core.viewmodel.SettingsViewModel
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

    private val settingsViewModel: SettingsViewModel by activityViewModels()
    private val mainViewModel: MainViewModel by scopeMainModule.inject {
        parametersOf(SavedStateHandle())
    }

    private val androidNetworkStatus: AndroidNetworkStatus by inject()

    private val adapterSolar by lazy { RvAdapterCommon(::onSolarFlareClickListener) }
    private val adapterEPIC by lazy { RvAdapterCommon(::onEpicClickListener) }
    private val adapterMars by lazy { RvAdapterCommon(::onMarsClickListener) }

    // backdrop
    private var showBackLayout = false
    private var frontLayoutParams: RelativeLayout.LayoutParams? = null

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

    @SuppressLint("ResourceAsColor")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (!hasInitializedRootView) {
            hasInitializedRootView = true
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
        }
        with(mainViewModel) {
            liveDataEpic.observe(viewLifecycleOwner) { adapterEPIC.adapterList = it }
            liveDataMars.observe(viewLifecycleOwner) { adapterMars.adapterList = it }
            liveDataSolar.observe(viewLifecycleOwner) { adapterSolar.adapterList = it }
        }

        binding.ivPic.setOnClickListener { onApodClickListener() }

        binding.appBarLayout.findViewById<View>(R.id.main_appbar).findViewById<View>(R.id.settings).setOnClickListener { dropLayout() }
        binding.appBarLayout.findViewById<View>(R.id.main_appbar).setOnClickListener { dropLayout() }

        // initial icon stroke
        binding.cardviewIconOne.strokeWidth = 6

        binding.cardviewIconOne.setOnClickListener {
            setIconsStrokeClear()
            binding.cardviewIconOne.strokeWidth = 6
            settingsViewModel.setApplicationIcon(ApplicationIcon.MARS)
        }

        binding.cardviewIconTwo.setOnClickListener {
            setIconsStrokeClear()
            binding.cardviewIconTwo.strokeWidth = 6
            settingsViewModel.setApplicationIcon(ApplicationIcon.JUPITER)
        }

        binding.cardviewIconThree.setOnClickListener {
            setIconsStrokeClear()
            binding.cardviewIconThree.strokeWidth = 6
            settingsViewModel.setApplicationIcon(ApplicationIcon.ROVER)
        }

        binding.cardviewIconFour.setOnClickListener {
            setIconsStrokeClear()
            binding.cardviewIconFour.strokeWidth = 6
            settingsViewModel.setApplicationIcon(ApplicationIcon.VENUS)
        }

        binding.buttonResolutionHd.setOnClickListener {
            settingsViewModel.setImageResolution(ImageResolution.HD)
        }

        binding.buttonResolutionRegular.setOnClickListener {
            settingsViewModel.setImageResolution(ImageResolution.REGULAR)
        }

        binding.buttonOriginalTheme.setOnClickListener {
            settingsViewModel.setApplicationTheme(ApplicationTheme.ORIGINAL)
            requireActivity().recreate()
        }

        binding.buttonMarsTheme.setOnClickListener {
            settingsViewModel.setApplicationTheme(ApplicationTheme.MARS)
            requireActivity().recreate()
        }

        binding.buttonEarthTheme.setOnClickListener {
            settingsViewModel.setApplicationTheme(ApplicationTheme.EARTH)
            requireActivity().recreate()
        }
    }

    private fun setIconsStrokeClear () {
        binding.cardviewIconOne.strokeWidth = 0
        binding.cardviewIconTwo.strokeWidth = 0
        binding.cardviewIconThree.strokeWidth = 0
        binding.cardviewIconFour.strokeWidth = 0
    }

    private fun dropLayout() {
        showBackLayout = !showBackLayout
        frontLayoutParams = binding.frontLayoutMain.layoutParams as RelativeLayout.LayoutParams
        if (showBackLayout) {
            val varl = ValueAnimator.ofInt(binding.backLayoutMain.height)
            varl.duration = 200
            varl.addUpdateListener { animation ->
                frontLayoutParams!!.setMargins(0, (animation.animatedValue as Int) - 72, 0, 0)
                binding.frontLayoutMain.layoutParams = frontLayoutParams
            }
            varl.start()
        } else {
            frontLayoutParams!!.setMargins(0, 18, 0, 0)
            binding.frontLayoutMain.layoutParams = frontLayoutParams
            val anim = TranslateAnimation(
                0F, 0F,
                binding.backLayoutMain.height.toFloat(), 0F
            )
            anim.startOffset = 0
            anim.duration = 200
            binding.frontLayoutMain.startAnimation(anim)
        }
    }

    override fun onResume() {
        super.onResume()
        //sets specific status bar color because of no appbar animation in this fragment
        val typedValue = TypedValue()
        val theme: Resources.Theme = requireContext().theme
        theme.resolveAttribute(com.google.android.material.R.attr.colorPrimaryVariant, typedValue, true)
        @ColorInt val mColor = typedValue.data
        val window: Window = requireActivity().window
        context?.let { window.statusBarColor = mColor }
    }

    override fun onStop() {
        super.onStop()
        //returning transparent status bar background color
        val window: Window = requireActivity().window
        window.statusBarColor = Color.parseColor("#00000000")
    }

    override fun onDestroy() {
        super.onDestroy()
        scopeMainModule.close()
    }

    private fun onApodClickListener() {
        findNavController().navigate(R.id.action_main_fragment_to_navigation_apod)
    }

    private fun onSolarFlareClickListener(itemRv: ItemRv) {
        if (itemRv.title == getString(R.string.title_main_solar))
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
}