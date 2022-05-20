package com.project.apod.ui

import android.graphics.PointF
import android.graphics.Rect
import android.os.Bundle
import android.view.*
import androidx.core.view.doOnLayout
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.navArgs
import com.project.apod.databinding.ScaleImageApodFragmentBinding
import com.project.core.entities.ImageResolution
import com.project.core.ui.BaseFragment
import com.project.core.utils.animateWithDetach
import com.project.core.utils.scale
import com.project.core.utils.setPivot
import com.project.core.viewmodel.SettingsViewModel

class APODScaleImageFragment :
    BaseFragment<ScaleImageApodFragmentBinding>(ScaleImageApodFragmentBinding::inflate) {

    companion object {
        //константы для жестов (ч. 1 из 3)
        private const val MAX_SCALE_FACTOR = 5f
        private const val MIN_SCALE_FACTOR = 1f
        private const val CORRECT_LOCATION_ANIMATION_DURATION = 300L
    }

    private val navArgs: APODScaleImageFragmentArgs by navArgs()
    private val settingsViewModel by activityViewModels<SettingsViewModel>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return providePersistentView(inflater, container, savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        hasInitializedRootView = !hasInitializedRootView
        val apodResponse = navArgs.apodResponse

        // Методы, относящиеся к жестам (ч. 2 из 3)
        var resolution = apodResponse.url.toString()
        settingsViewModel.imageResolution.observe(viewLifecycleOwner) {
            resolution = when (it) {
                ImageResolution.REGULAR -> apodResponse.url.toString()
                ImageResolution.HD -> apodResponse.hdurl.toString()
            }
        }
        useCoilToLoadPhoto(binding.myImageView, resolution)
        binding.myImageView.doOnLayout { originContentRect }
        binding.viewTouchHandler.setOnTouchListener { view, event ->
            scaleGestureDetector.onTouchEvent(event)
            translationHandler.onTouch(view, event)
            true
        }
    }

    // Ниже методы, относящиеся к жестам (ч. 3 из 3)
    private val originContentRect by lazy {
        binding.myImageView.run {
            val array = IntArray(2)
            getLocationOnScreen(array)
            Rect(array[0], array[1], array[0] + width, array[1] + height)
        }
    }

    private val translationHandler by lazy {
        object : View.OnTouchListener {
            private var prevX = 0f
            private var prevY = 0f
            private var moveStarted = false
            override fun onTouch(v: View?, event: MotionEvent?): Boolean {
                if (event == null || (binding.myImageView?.scaleX ?: 1f) == 1f) return false

                when (event.actionMasked) {
                    MotionEvent.ACTION_DOWN -> {
                        prevX = event.x
                        prevY = event.y
                    }

                    MotionEvent.ACTION_POINTER_UP -> {
                        if (event.actionIndex == 0) {
                            try {
                                prevX = event.getX(1)
                                prevY = event.getY(1)
                            } catch (e: Exception) {
                            }
                        }
                    }

                    MotionEvent.ACTION_MOVE -> {
                        if (event.pointerCount > 1) {
                            prevX = event.x
                            prevY = event.y
                            return false
                        }
                        moveStarted = true
                        binding.myImageView?.run {
                            translationX += (event.x - prevX)
                            translationY += (event.y - prevY)
                        }
                        prevX = event.x
                        prevY = event.y
                    }

                    MotionEvent.ACTION_UP -> {
                        if (!moveStarted) return false
                        reset()
                        translateToOriginalRect()
                    }
                }
                return true
            }

            private fun reset() {
                prevX = 0f
                prevY = 0f
                moveStarted = false
            }
        }
    }

    private val scaleGestureDetector by lazy {
        ScaleGestureDetector(context, object : ScaleGestureDetector.OnScaleGestureListener {
            var totalScale = 1f

            override fun onScaleBegin(detector: ScaleGestureDetector): Boolean {
                binding.myImageView.run {
                    val actualPivot = PointF(
                        (detector.focusX - translationX + pivotX * (totalScale - 1)) / totalScale,
                        (detector.focusY - translationY + pivotY * (totalScale - 1)) / totalScale,
                    )

                    translationX -= (pivotX - actualPivot.x) * (totalScale - 1)
                    translationY -= (pivotY - actualPivot.y) * (totalScale - 1)
                    setPivot(actualPivot)
                }
                return true
            }

            override fun onScale(detector: ScaleGestureDetector): Boolean {
                totalScale *= detector.scaleFactor
                totalScale = totalScale.coerceIn(MIN_SCALE_FACTOR, MAX_SCALE_FACTOR)
                binding.myImageView.run {
                    scale(totalScale)
                    getContentViewTranslation().run {
                        translationX += x
                        translationY += y
                    }
                }
                return true
            }

            override fun onScaleEnd(detector: ScaleGestureDetector) = Unit
        })
    }

    private fun translateToOriginalRect() {
        getContentViewTranslation().takeIf { it != PointF(0f, 0f) }?.let { translation ->
            binding.myImageView?.let { view ->
                view.animateWithDetach()
                    .translationXBy(translation.x)
                    .translationYBy(translation.y)
                    .apply { duration = CORRECT_LOCATION_ANIMATION_DURATION }
                    .start()
            }
        }
    }

    private fun getContentViewTranslation(): PointF {
        return binding.myImageView.run {
            originContentRect.let { rect ->
                val array = IntArray(2)
                getLocationOnScreen(array)
                PointF(
                    when {
                        array[0] > rect.left -> rect.left - array[0].toFloat()
                        array[0] + width * scaleX < rect.right -> rect.right - (array[0] + width * scaleX)
                        else -> 0f
                    },
                    when {
                        array[1] > rect.top -> rect.top - array[1].toFloat()
                        array[1] + height * scaleY < rect.bottom -> rect.bottom - (array[1] + height * scaleY)
                        else -> 0f
                    }
                )
            }
        }
    }
}