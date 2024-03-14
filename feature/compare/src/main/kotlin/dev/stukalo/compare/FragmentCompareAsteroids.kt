package dev.stukalo.compare

import android.os.Bundle
import androidx.core.content.ContextCompat.getColor
import androidx.core.util.TypedValueCompat
import androidx.core.view.doOnPreDraw
import androidx.core.view.isVisible
import androidx.core.view.postDelayed
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.viewpager2.widget.ViewPager2
import by.kirich1409.viewbindingdelegate.viewBinding
import dagger.hilt.android.AndroidEntryPoint
import dev.stukalo.common.Constants.DATE_TIME_FORMATTER
import dev.stukalo.common.model.EstimatedDiameterUi
import dev.stukalo.common.model.MissDistanceUi
import dev.stukalo.common.model.RelativeVelocityUi
import dev.stukalo.common.utils.AsteroidAdapter
import dev.stukalo.compare.databinding.FragmentCompareAsteroidsBinding
import dev.stukalo.compare.viewpager.AsteroidsPagerAdapter
import dev.stukalo.datastore.Constants
import dev.stukalo.datastore.PreferencesManager
import dev.stukalo.navigation.NavigationDirection
import dev.stukalo.platform.BaseFragment
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import javax.inject.Inject
import kotlin.math.abs

@AndroidEntryPoint
class FragmentCompareAsteroids : BaseFragment(R.layout.fragment_compare_asteroids) {
    private val viewBinding: FragmentCompareAsteroidsBinding by viewBinding(FragmentCompareAsteroidsBinding::bind)
    private val viewModel: CompareAsteroidsViewModel by activityViewModels()

    @Inject lateinit var datastore: PreferencesManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.getFavoriteAsteroids()
    }

    override fun configureUi() {
        super.configureUi()
        showLoader(true)
        collectStateFlow()
    }

    private fun collectStateFlow() {
        val startAsteroidId = arguments?.getString("start_asteroid_id")

        val displayMetrics = requireContext().resources.displayMetrics
        val scaleMax = 0.95F
        val alphaMax = 0.7F

        viewLifecycleOwner.lifecycleScope.launch {
            lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.compareStateFlow.collect { favoriteAsteroids ->
                    if (favoriteAsteroids.count() >= 3) {
                        viewBinding.pagerComparison.apply {
                            val asteroidsPagerAdapter = AsteroidsPagerAdapter(favoriteAsteroids, datastore)
                            adapter = asteroidsPagerAdapter
                            clipToPadding = false
                            offscreenPageLimit = 2
                            setPageTransformer { page, position ->
                                val offset = TypedValueCompat.dpToPx(48F, displayMetrics) * position
                                val scale: Float =
                                    if (position < 0) (1 - scaleMax) * position + 1 else (scaleMax - 1) * position + 1
                                val alpha: Float =
                                    if (position < 0) (1 - alphaMax) * position + 1 else (alphaMax - 1) * position + 1
                                if (position < 0) {
                                    page.pivotX = page.width.toFloat()
                                    page.pivotY = (page.height / 2).toFloat()
                                } else {
                                    page.pivotX = 0F
                                    page.pivotY = (page.height / 2).toFloat()
                                }
                                page.translationX = -offset
                                page.scaleX = scale
                                page.scaleY = scale
                                page.alpha = abs(alpha)
                            }
                            doOnPreDraw {
                                currentItem = favoriteAsteroids.indexOf(
                                    favoriteAsteroids.find {
                                        it.id == startAsteroidId
                                    },
                                ) + 1
                                postDelayed(500) {
                                    if (this@FragmentCompareAsteroids.isResumed) {
                                        isVisible = true
                                        showLoader(false)
                                    }
                                }
                            }
                            asteroidsPagerAdapter.onItemClick = {
                                navigateTo(
                                    NavigationDirection.AsteroidDetails,
                                    arg = AsteroidAdapter.toJson(it),
                                )
                            }
                        }
                        onInfinitePageChangeCallback(favoriteAsteroids.count() + 2)
                    } else if (favoriteAsteroids.isNotEmpty()) {
                        val velocityUnit = datastore.selectedVelocityUnit().first()
                        val distanceUnit = datastore.selectedDistanceUnit().first()
                        val diameterUnit = datastore.selectedDiameterUnit().first()

                        val firstAsteroid = favoriteAsteroids[0]
                        val secondAsteroid = favoriteAsteroids[1]

                        with(viewBinding) {
                            tvNameFirst.text = firstAsteroid.name
                            tvNameSecond.text = secondAsteroid.name

                            tvHazardousFirst.text = isHazardous(firstAsteroid.isPotentiallyHazardousAsteroid)
                            tvHazardousSecond.text = isHazardous(secondAsteroid.isPotentiallyHazardousAsteroid)

                            tvDateFirst.text = getDatetime(firstAsteroid.closeApproachData?.epochDateCloseApproach)
                            tvDateSecond.text = getDatetime(secondAsteroid.closeApproachData?.epochDateCloseApproach)

                            tvMagnitudeFirst.apply {
                                text = firstAsteroid.absoluteMagnitudeH.toString()
                                setTextColor(
                                    colorBasedOnValue(
                                        firstAsteroid.absoluteMagnitudeH,
                                        secondAsteroid.absoluteMagnitudeH,
                                    ),
                                )
                            }
                            tvMagnitudeSecond.apply {
                                text = secondAsteroid.absoluteMagnitudeH.toString()
                                setTextColor(
                                    colorBasedOnValue(
                                        secondAsteroid.absoluteMagnitudeH,
                                        firstAsteroid.absoluteMagnitudeH,
                                    ),
                                )
                            }

                            setupRelativeVelocityFields(
                                firstAsteroid.closeApproachData?.relativeVelocity,
                                secondAsteroid.closeApproachData?.relativeVelocity,
                                velocityUnit,
                            )

                            setupMissDistanceFields(
                                firstAsteroid.closeApproachData?.missDistance,
                                secondAsteroid.closeApproachData?.missDistance,
                                distanceUnit,
                            )

                            setupEstimatedDiameterFields(
                                firstAsteroid.estimatedDiameter,
                                secondAsteroid.estimatedDiameter,
                                diameterUnit,
                            )

                            pagerComparison.isVisible = false
                            cvTwoAsteroidComparison.isVisible = true
                            showLoader(false)

                            cvTwoAsteroidComparison.setOnClickListener {
                                findNavController().popBackStack()
                            }
                        }
                    }
                }
            }
        }
    }

    private fun onInfinitePageChangeCallback(listSize: Int) {
        viewBinding.pagerComparison.apply {
            registerOnPageChangeCallback(
                object : ViewPager2.OnPageChangeCallback() {
                    override fun onPageScrollStateChanged(state: Int) {
                        super.onPageScrollStateChanged(state)
                        if (state == ViewPager2.SCROLL_STATE_IDLE) {
                            when (currentItem) {
                                listSize - 1 -> setCurrentItem(1, false)
                                0 -> setCurrentItem(listSize - 2, false)
                            }
                        }
                    }
                },
            )
        }
    }

    private fun isHazardous(isPotentiallyHazardous: Boolean?): String {
        return if (isPotentiallyHazardous == true) {
            getString(dev.stukalo.ui.R.string.yes)
        } else {
            getString(dev.stukalo.ui.R.string.no)
        }
    }

    private fun getDatetime(timestamp: Long?): String? {
        return timestamp?.run {
            val dateTime = LocalDateTime.ofInstant(Instant.ofEpochMilli(timestamp), ZoneId.systemDefault())
            val formatter = DateTimeFormatter.ofPattern(DATE_TIME_FORMATTER)
            dateTime.format(formatter)
        }
    }

    private fun setupRelativeVelocityFields(
        firstRelativeVelocity: RelativeVelocityUi?,
        secondRelativeVelocity: RelativeVelocityUi?,
        unit: String,
    ) {
        with(viewBinding) {
            tvRelativeVelocity.text =
                String.format(
                    getString(R.string.compare_relative_velocity),
                    unit,
                )

            var firstVelocityValue: Double? = null
            var secondVelocityValue: Double? = null

            when (unit) {
                Constants.Velocity.KILOMETERS_PER_SECOND.value -> {
                    firstVelocityValue = firstRelativeVelocity?.kilometersPerSecond?.toDouble()
                    secondVelocityValue = secondRelativeVelocity?.kilometersPerSecond?.toDouble()
                }
                Constants.Velocity.KILOMETERS_PER_HOUR.value -> {
                    firstVelocityValue = firstRelativeVelocity?.kilometersPerHour?.toDouble()
                    secondVelocityValue = secondRelativeVelocity?.kilometersPerHour?.toDouble()
                }
                Constants.Velocity.MILES_PER_HOUR.value -> {
                    firstVelocityValue = firstRelativeVelocity?.milesPerHour?.toDouble()
                    secondVelocityValue = secondRelativeVelocity?.milesPerHour?.toDouble()
                }
                else -> {}
            }

            tvVelocityFirst.apply {
                text =
                    String.format(
                        getString(R.string.round_to_two),
                        firstVelocityValue,
                    )
                setTextColor(colorBasedOnValue(firstVelocityValue, secondVelocityValue))
            }

            tvVelocitySecond.apply {
                text =
                    String.format(
                        getString(R.string.round_to_two),
                        secondVelocityValue,
                    )
                setTextColor(colorBasedOnValue(secondVelocityValue, firstVelocityValue))
            }
        }
    }

    private fun setupMissDistanceFields(
        firstMissDistance: MissDistanceUi?,
        secondMissDistance: MissDistanceUi?,
        unit: String,
    ) {
        with(viewBinding) {
            tvMissDistance.text =
                String.format(
                    getString(R.string.compare_miss_distance),
                    unit,
                )

            var firstDistanceValue: Double? = null
            var secondDistanceValue: Double? = null

            when (unit) {
                Constants.Distance.ASTRONOMICAL.value -> {
                    firstDistanceValue = firstMissDistance?.astronomical?.toDouble()
                    secondDistanceValue = secondMissDistance?.astronomical?.toDouble()
                }
                Constants.Distance.LUNAR.value -> {
                    firstDistanceValue = firstMissDistance?.lunar?.toDouble()
                    secondDistanceValue = secondMissDistance?.lunar?.toDouble()
                }
                Constants.Distance.KILOMETERS.value -> {
                    firstDistanceValue = firstMissDistance?.kilometers?.toDouble()
                    secondDistanceValue = secondMissDistance?.kilometers?.toDouble()
                }
                Constants.Distance.MILES.value -> {
                    firstDistanceValue = firstMissDistance?.miles?.toDouble()
                    secondDistanceValue = secondMissDistance?.miles?.toDouble()
                }
                else -> {}
            }

            tvDistanceFirst.apply {
                text =
                    String.format(
                        getString(R.string.round_to_two),
                        firstDistanceValue,
                    )
                setTextColor(colorBasedOnValue(firstDistanceValue, secondDistanceValue))
            }

            tvDistanceSecond.apply {
                text =
                    String.format(
                        getString(R.string.round_to_two),
                        secondDistanceValue,
                    )
                setTextColor(colorBasedOnValue(secondDistanceValue, firstDistanceValue))
            }
        }
    }

    private fun setupEstimatedDiameterFields(
        firstEstimatedDiameter: EstimatedDiameterUi?,
        secondEstimatedDiameter: EstimatedDiameterUi?,
        unit: String,
    ) {
        with(viewBinding) {
            tvEstimatedDiameter.text =
                String.format(
                    getString(R.string.compare_estimated_diameter),
                    unit,
                )

            var minDiameterFirst: Double? = null
            var maxDiameterFirst: Double? = null

            var minDiameterSecond: Double? = null
            var maxDiameterSecond: Double? = null

            when (unit) {
                Constants.Diameter.KILOMETERS.value -> {
                    minDiameterFirst = firstEstimatedDiameter?.kilometers?.estimatedDiameterMin
                    maxDiameterFirst = firstEstimatedDiameter?.kilometers?.estimatedDiameterMax

                    minDiameterSecond = secondEstimatedDiameter?.kilometers?.estimatedDiameterMin
                    maxDiameterSecond = secondEstimatedDiameter?.kilometers?.estimatedDiameterMax
                }
                Constants.Diameter.METERS.value -> {
                    minDiameterFirst = firstEstimatedDiameter?.meters?.estimatedDiameterMin
                    maxDiameterFirst = firstEstimatedDiameter?.meters?.estimatedDiameterMax

                    minDiameterSecond = secondEstimatedDiameter?.meters?.estimatedDiameterMin
                    maxDiameterSecond = secondEstimatedDiameter?.meters?.estimatedDiameterMax
                }
                Constants.Diameter.MILES.value -> {
                    minDiameterFirst = firstEstimatedDiameter?.miles?.estimatedDiameterMin
                    maxDiameterFirst = firstEstimatedDiameter?.miles?.estimatedDiameterMax

                    minDiameterSecond = secondEstimatedDiameter?.miles?.estimatedDiameterMin
                    maxDiameterSecond = secondEstimatedDiameter?.miles?.estimatedDiameterMax
                }
                Constants.Diameter.FEET.value -> {
                    minDiameterFirst = firstEstimatedDiameter?.feet?.estimatedDiameterMin
                    maxDiameterFirst = firstEstimatedDiameter?.feet?.estimatedDiameterMax

                    minDiameterSecond = secondEstimatedDiameter?.feet?.estimatedDiameterMin
                    maxDiameterSecond = secondEstimatedDiameter?.feet?.estimatedDiameterMax
                }
            }

            val diameterFormat = getString(dev.stukalo.ui.R.string.estimated_diameter_value)
            tvDiameterMinFirst.apply {
                text = String.format(diameterFormat, minDiameterFirst)
                setTextColor(colorBasedOnValue(minDiameterFirst, minDiameterSecond))
            }
            tvDiameterMaxFirst.apply {
                text = String.format(diameterFormat, maxDiameterFirst)
                setTextColor(colorBasedOnValue(maxDiameterFirst, maxDiameterSecond))
            }
            tvDiameterMinSecond.apply {
                text = String.format(diameterFormat, minDiameterSecond)
                setTextColor(colorBasedOnValue(minDiameterSecond, minDiameterFirst))
            }
            tvDiameterMaxSecond.apply {
                text = String.format(diameterFormat, maxDiameterSecond)
                setTextColor(colorBasedOnValue(maxDiameterSecond, maxDiameterFirst))
            }
        }
    }

    private fun colorBasedOnValue(
        comp: Double?,
        curr: Double?,
    ): Int {
        comp?.let {
            curr?.let {
                return if (comp > curr) {
                    getColor(requireContext(), dev.stukalo.ui.R.color.green_900)
                } else if (comp < curr) {
                    getColor(requireContext(), dev.stukalo.ui.R.color.red_900)
                } else {
                    getColor(requireContext(), dev.stukalo.ui.R.color.primary)
                }
            }
        }
        return getColor(requireContext(), dev.stukalo.ui.R.color.primary)
    }

    override fun onDestroyView() {
        showLoader(false)
        super.onDestroyView()
    }

    override fun onDestroy() {
        super.onDestroy()
        viewModel.clear()
    }
}
