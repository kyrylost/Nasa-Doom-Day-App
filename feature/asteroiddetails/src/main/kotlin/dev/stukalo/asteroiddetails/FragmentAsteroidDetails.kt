package dev.stukalo.asteroiddetails

import android.content.Intent
import android.net.Uri
import android.view.ViewTreeObserver
import android.widget.LinearLayout
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import dev.stukalo.asteroiddetails.databinding.FragmentAsteroidDetailsBinding
import dev.stukalo.asteroiddetails.util.AsteroidAdapter
import dev.stukalo.common.model.EstimatedDiameterUi
import dev.stukalo.common.model.MissDistanceUi
import dev.stukalo.common.model.RelativeVelocityUi
import dev.stukalo.platform.BaseFragment
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter

const val CERES_DIAMETER = 939.4
const val DATE_TIME_FORMATTER = "yyyy MMM dd HH:mm"

class FragmentAsteroidDetails : BaseFragment(R.layout.fragment_asteroid_details) {
    private val viewBinding: FragmentAsteroidDetailsBinding by viewBinding(FragmentAsteroidDetailsBinding::bind)
    private val viewModel: AsteroidDetailsViewModel by activityViewModels()
    private var jobOnSizeLayout: ViewTreeObserver.OnGlobalLayoutListener? = null
    private var jobOnDistanceLayout: ViewTreeObserver.OnGlobalLayoutListener? = null
    private var sizeComparisonInitialized: Boolean = false

    override fun configureUi() {
        super.configureUi()
        collectSharedFlows()
        val asteroidUiJson = arguments?.getString("asteroid_ui_json")

        asteroidUiJson?.let {
            AsteroidAdapter.fromJson(asteroidUiJson)?.apply {
                lifecycleScope.launch {
                    val isAsteroidInFavorite =
                        with(viewModel) {
                            val navigateFromPush = arguments?.getBoolean("navigate_from_push")
                            if (navigateFromPush == true) {
                                updateIsShownField(id)
                            }
                            isAsteroidInFavorite(id)
                        }

                    with(viewBinding) {
                        rgComparison.check(R.id.rb_distance)
                        setupDistanceComparison(closeApproachData?.missDistance?.astronomical?.toDouble() ?: 0.0)

                        tvName.text = name
                        ibLink.setOnClickListener {
                            processExternalLink(nasaJplUrl)
                        }
                        tvIdValue.text = id
                        tvMagnitudeValue.text = absoluteMagnitudeH.toString()
                        tvPotentiallyHazardousValue.text = isHazardous(isPotentiallyHazardousAsteroid)
                        tvCloseApproachDateValue.text = getDatetime(closeApproachData?.epochDateCloseApproach)
                        tvOrbitingBodyValue.text = closeApproachData?.orbitingBody
                        tvIsSentryObjectValue.text = isSentryObject(isSentryObject)

                        setupRelativeVelocityField(closeApproachData?.relativeVelocity)
                        setupMissDistanceField(closeApproachData?.missDistance)
                        setupEstimatedDiameterField(estimatedDiameter)

                        if (isAsteroidInFavorite) {
                            ibFavorite.setColorFilter(
                                ContextCompat.getColor(
                                    requireContext(),
                                    dev.stukalo.ui.R.color.orange_900,
                                ),
                            )
                        } else {
                            ibFavorite.setOnClickListener {
                                viewModel.addToFavorite(this@apply)
                            }
                        }

                        ibBack.setOnClickListener {
                            findNavController().popBackStack()
                        }

                        rgComparison.setOnCheckedChangeListener { _, checkedId ->
                            when (checkedId) {
                                R.id.rb_distance -> {
                                    svDistanceComparison.isVisible = true
                                    zlSizeComparison.isVisible = false
                                }
                                R.id.rb_size -> {
                                    svDistanceComparison.isVisible = false
                                    zlSizeComparison.isVisible = true
                                    if (!sizeComparisonInitialized) {
                                        setupSizeComparison(estimatedDiameter)
                                        sizeComparisonInitialized = true
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    private fun collectSharedFlows() {
        viewLifecycleOwner.lifecycleScope.launch {
            lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.addStatusSharedFlow.collectLatest { name ->
                    if (name != null) {
                        viewBinding.ibFavorite.setColorFilter(
                            ContextCompat.getColor(
                                requireContext(),
                                dev.stukalo.ui.R.color.orange_900,
                            ),
                        )
                        operationSucceedSnackBar(
                            String.format(
                                getString(R.string.asteroid_added),
                                name,
                            ),
                        )
                    } else {
                        operationFailedSnackBar(getString(R.string.asteroid_not_added))
                    }
                }
            }
        }
    }

    private fun processExternalLink(link: String?) {
        link?.let {
            startActivity(
                Intent().apply {
                    action = Intent.ACTION_VIEW
                    addCategory(Intent.CATEGORY_BROWSABLE)
                    data = Uri.parse(it)
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

    private fun isSentryObject(isSentryObject: Boolean?): String {
        return if (isSentryObject == true) {
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

    private fun setupRelativeVelocityField(relativeVelocityUi: RelativeVelocityUi?) {
        with(viewBinding) {
            tvRelativeVelocity.text =
                String.format(
                    getString(R.string.details_relative_velocity),
                    getString(R.string.km_per_sec),
                )
            tvRelativeVelocityValue.text =
                String.format(
                    getString(R.string.round_to_two),
                    relativeVelocityUi?.kilometersPerHour?.toDouble(),
                )
        }
    }

    private fun setupMissDistanceField(missDistanceUi: MissDistanceUi?) {
        with(viewBinding) {
            tvMissDistance.text =
                String.format(
                    getString(R.string.details_miss_distance),
                    getString(R.string.kilometer_distance),
                )
            tvMissDistanceValue.text =
                String.format(
                    getString(R.string.round_to_two),
                    missDistanceUi?.kilometers?.toDouble(),
                )
        }
    }

    private fun setupEstimatedDiameterField(estimatedDiameterUi: EstimatedDiameterUi?) {
        with(viewBinding) {
            tvEstimatedDiameter.text =
                String.format(
                    getString(R.string.details_estimated_diameter),
                    getString(R.string.kilometer_distance),
                )
            tvMinDiameterValue.text =
                String.format(
                    getString(dev.stukalo.ui.R.string.estimated_diameter_value),
                    estimatedDiameterUi?.kilometers?.estimatedDiameterMin,
                )
            tvMaxDiameterValue.text =
                String.format(
                    getString(dev.stukalo.ui.R.string.estimated_diameter_value),
                    estimatedDiameterUi?.kilometers?.estimatedDiameterMax,
                )
        }
    }

    private fun setupSizeComparison(estimatedDiameter: EstimatedDiameterUi?) {
        with(viewBinding) {
            jobOnSizeLayout =
                ViewTreeObserver.OnGlobalLayoutListener {
                    val generalWidth = zlSizeComparison.width.toDouble()
                    val asteroidDiameter = estimatedDiameter?.kilometers?.estimatedDiameterMax

                    asteroidDiameter?.let {
                        val diameterSum = CERES_DIAMETER + asteroidDiameter

                        val scalingFactor = generalWidth / diameterSum
                        val ceresSize = CERES_DIAMETER * scalingFactor
                        val asteroidSize = asteroidDiameter * scalingFactor

                        val layoutParamsCeres = ivCeres.layoutParams
                        val layoutParamsAsteroid = ivAsteroid.layoutParams

                        if (asteroidSize < 100) {
                            val extraScaleFactor = 100 / asteroidSize

                            layoutParamsCeres.width = (ceresSize * extraScaleFactor).toInt()
                            layoutParamsCeres.height = (ceresSize * extraScaleFactor).toInt()

                            layoutParamsAsteroid.width = (asteroidSize * extraScaleFactor).toInt()
                            layoutParamsAsteroid.height = (asteroidSize * extraScaleFactor).toInt()
                        } else {
                            layoutParamsCeres.width = ceresSize.toInt()
                            layoutParamsCeres.height = ceresSize.toInt()

                            layoutParamsAsteroid.width = asteroidSize.toInt()
                            layoutParamsAsteroid.height = asteroidSize.toInt()
                        }

                        ivCeres.layoutParams = layoutParamsCeres
                        ivAsteroid.layoutParams = layoutParamsAsteroid

                        zlSizeComparison.viewTreeObserver.removeOnGlobalLayoutListener(
                            jobOnSizeLayout,
                        )
                        jobOnSizeLayout = null
                    }
                }

            zlSizeComparison.viewTreeObserver.addOnGlobalLayoutListener(jobOnSizeLayout)
        }
    }

    private fun setupDistanceComparison(au: Double) {
        with(viewBinding) {
            jobOnDistanceLayout =
                ViewTreeObserver.OnGlobalLayoutListener {
                    val asteroidToEarthDistance = spaceBetweenAsteroidAndEarth.width.toFloat().times(au)
                    var scaleFactor = 1.0
                    if (asteroidToEarthDistance < 10) {
                        scaleFactor = 10 / asteroidToEarthDistance
                    }
                    val spaceBetweenAsteroidAndEarthParams =
                        (spaceBetweenAsteroidAndEarth.layoutParams as? LinearLayout.LayoutParams)?.apply {
                            weight = 0F
                            width = (asteroidToEarthDistance * scaleFactor).toInt()
                        }

                    val spaceBetweenEarthAndSunParams =
                        (spaceBetweenEarthAndSun.layoutParams as? LinearLayout.LayoutParams)?.apply {
                            weight = 0F
                            width = (spaceBetweenEarthAndSun.width * scaleFactor).toInt()
                        }

                    spaceBetweenAsteroidAndEarth.layoutParams = spaceBetweenAsteroidAndEarthParams
                    spaceBetweenEarthAndSun.layoutParams = spaceBetweenEarthAndSunParams

                    svDistanceComparison.viewTreeObserver.removeOnGlobalLayoutListener(
                        jobOnDistanceLayout,
                    )
                    jobOnDistanceLayout = null
                }
            svDistanceComparison.viewTreeObserver.addOnGlobalLayoutListener(jobOnDistanceLayout)
        }
    }
}
