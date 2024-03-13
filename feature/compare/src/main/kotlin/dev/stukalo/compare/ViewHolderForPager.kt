package dev.stukalo.compare

import android.content.Context
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import dev.stukalo.common.Constants
import dev.stukalo.common.model.AsteroidUi
import dev.stukalo.common.model.EstimatedDiameterUi
import dev.stukalo.common.model.MissDistanceUi
import dev.stukalo.common.model.RelativeVelocityUi
import dev.stukalo.compare.databinding.ItemViewPagerBinding
import dev.stukalo.datastore.PreferencesManager
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter

class ViewHolderForPager(
    val binding: ItemViewPagerBinding,
    private val datastore: PreferencesManager,
    private val context: Context,
) : RecyclerView.ViewHolder(binding.root) {
    fun bind(
        prev: AsteroidUi,
        curr: AsteroidUi,
        next: AsteroidUi,
    ) {
        CoroutineScope(Dispatchers.Main).launch {
            val velocityUnit = datastore.selectedVelocityUnit().first()
            val distanceUnit = datastore.selectedDistanceUnit().first()
            val diameterUnit = datastore.selectedDiameterUnit().first()

            with(binding) {
                binding.tvNamePrev.text = prev.name
                binding.tvNameCurr.text = curr.name
                binding.tvNameNext.text = next.name

                tvHazardousPrev.text = isHazardous(prev.isPotentiallyHazardousAsteroid)
                tvHazardousCurr.text = isHazardous(curr.isPotentiallyHazardousAsteroid)
                tvHazardousNext.text = isHazardous(next.isPotentiallyHazardousAsteroid)

                tvDatePrev.text = getDatetime(prev.closeApproachData?.epochDateCloseApproach)
                tvDateCurr.text = getDatetime(curr.closeApproachData?.epochDateCloseApproach)
                tvDateNext.text = getDatetime(next.closeApproachData?.epochDateCloseApproach)

                tvMagnitudePrev.apply {
                    text = prev.absoluteMagnitudeH.toString()
                    setTextColor(
                        colorBasedOnValue(
                            prev.absoluteMagnitudeH ?: 0.0,
                            curr.absoluteMagnitudeH ?: 0.0,
                        ),
                    )
                }
                tvMagnitudeCurr.text = curr.absoluteMagnitudeH.toString()
                tvMagnitudeNext.apply {
                    text = next.absoluteMagnitudeH.toString()
                    setTextColor(
                        colorBasedOnValue(
                            next.absoluteMagnitudeH ?: 0.0,
                            curr.absoluteMagnitudeH ?: 0.0,
                        ),
                    )
                }

                tvRelativeVelocity.text =
                    String.format(
                        ContextCompat.getString(context, R.string.compare_relative_velocity),
                        velocityUnit,
                    )
                tvVelocityPrev.apply {
                    text = getRelativeVelocity(prev.closeApproachData?.relativeVelocity, velocityUnit)
                    setTextColor(
                        colorBasedOnValue(
                            prev.closeApproachData?.relativeVelocity?.kilometersPerSecond?.toDouble() ?: 0.0,
                            curr.closeApproachData?.relativeVelocity?.kilometersPerSecond?.toDouble() ?: 0.0,
                        ),
                    )
                }
                tvVelocityCurr.text = getRelativeVelocity(curr.closeApproachData?.relativeVelocity, velocityUnit)
                tvVelocityNext.apply {
                    text = getRelativeVelocity(next.closeApproachData?.relativeVelocity, velocityUnit)
                    setTextColor(
                        colorBasedOnValue(
                            next.closeApproachData?.relativeVelocity?.kilometersPerSecond?.toDouble() ?: 0.0,
                            curr.closeApproachData?.relativeVelocity?.kilometersPerSecond?.toDouble() ?: 0.0,
                        ),
                    )
                }

                tvMissDistance.text =
                    String.format(
                        ContextCompat.getString(context, R.string.compare_miss_distance),
                        distanceUnit,
                    )
                tvDistancePrev.apply {
                    text = getMissDistance(prev.closeApproachData?.missDistance, distanceUnit)
                    setTextColor(
                        colorBasedOnValue(
                            prev.closeApproachData?.missDistance?.kilometers?.toDouble() ?: 0.0,
                            curr.closeApproachData?.missDistance?.kilometers?.toDouble() ?: 0.0,
                        ),
                    )
                }
                tvDistanceCurr.text = getMissDistance(curr.closeApproachData?.missDistance, distanceUnit)
                tvDistanceNext.apply {
                    text = getMissDistance(next.closeApproachData?.missDistance, distanceUnit)
                    setTextColor(
                        colorBasedOnValue(
                            next.closeApproachData?.missDistance?.kilometers?.toDouble() ?: 0.0,
                            curr.closeApproachData?.missDistance?.kilometers?.toDouble() ?: 0.0,
                        ),
                    )
                }

                tvEstimatedDiameter.text =
                    String.format(
                        ContextCompat.getString(context, R.string.compare_estimated_diameter),
                        diameterUnit,
                    )
                tvDiameterMinPrev.apply {
                    text = getDiameterMin(prev.estimatedDiameter, diameterUnit)
                    setTextColor(
                        colorBasedOnValue(
                            prev.estimatedDiameter?.meters?.estimatedDiameterMin ?: 0.0,
                            curr.estimatedDiameter?.meters?.estimatedDiameterMin ?: 0.0,
                        ),
                    )
                }
                tvDiameterMinCurr.text = getDiameterMin(curr.estimatedDiameter, diameterUnit)
                tvDiameterMinNext.apply {
                    text = getDiameterMin(next.estimatedDiameter, diameterUnit)
                    setTextColor(
                        colorBasedOnValue(
                            next.estimatedDiameter?.meters?.estimatedDiameterMin ?: 0.0,
                            curr.estimatedDiameter?.meters?.estimatedDiameterMin ?: 0.0,
                        ),
                    )
                }

                tvDiameterMaxPrev.apply {
                    text = getDiameterMax(prev.estimatedDiameter, diameterUnit)
                    setTextColor(
                        colorBasedOnValue(
                            prev.estimatedDiameter?.meters?.estimatedDiameterMax ?: 0.0,
                            curr.estimatedDiameter?.meters?.estimatedDiameterMax ?: 0.0,
                        ),
                    )
                }
                tvDiameterMaxCurr.text = getDiameterMax(curr.estimatedDiameter, diameterUnit)
                tvDiameterMaxNext.apply {
                    text = getDiameterMax(next.estimatedDiameter, diameterUnit)
                    setTextColor(
                        colorBasedOnValue(
                            next.estimatedDiameter?.meters?.estimatedDiameterMax ?: 0.0,
                            curr.estimatedDiameter?.meters?.estimatedDiameterMax ?: 0.0,
                        ),
                    )
                }
            }
        }
    }

    private fun isHazardous(isPotentiallyHazardous: Boolean?): String {
        return if (isPotentiallyHazardous == true) {
            ContextCompat.getString(context, dev.stukalo.ui.R.string.yes)
        } else {
            ContextCompat.getString(context, dev.stukalo.ui.R.string.no)
        }
    }

    private fun getDatetime(timestamp: Long?): String? {
        return timestamp?.run {
            val dateTime = LocalDateTime.ofInstant(Instant.ofEpochMilli(timestamp), ZoneId.systemDefault())
            val formatter = DateTimeFormatter.ofPattern(Constants.DATE_TIME_FORMATTER)
            dateTime.format(formatter)
        }
    }

    private fun getRelativeVelocity(
        relativeVelocityUi: RelativeVelocityUi?,
        unit: String,
    ): String {
        return String.format(
            ContextCompat.getString(context, R.string.round_to_two),
            when (unit) {
                dev.stukalo.datastore.Constants.Velocity.KILOMETERS_PER_SECOND.value -> {
                    relativeVelocityUi?.kilometersPerSecond?.toDouble()
                }
                dev.stukalo.datastore.Constants.Velocity.KILOMETERS_PER_HOUR.value -> {
                    relativeVelocityUi?.kilometersPerHour?.toDouble()
                }
                dev.stukalo.datastore.Constants.Velocity.MILES_PER_HOUR.value -> {
                    relativeVelocityUi?.milesPerHour?.toDouble()
                }
                else -> {}
            },
        )
    }

    private fun getMissDistance(
        missDistanceUi: MissDistanceUi?,
        unit: String,
    ): String {
        return String.format(
            ContextCompat.getString(context, R.string.round_to_two),
            when (unit) {
                dev.stukalo.datastore.Constants.Distance.ASTRONOMICAL.value -> {
                    missDistanceUi?.astronomical?.toDouble()
                }
                dev.stukalo.datastore.Constants.Distance.LUNAR.value -> {
                    missDistanceUi?.lunar?.toDouble()
                }
                dev.stukalo.datastore.Constants.Distance.KILOMETERS.value -> {
                    missDistanceUi?.kilometers?.toDouble()
                }
                dev.stukalo.datastore.Constants.Distance.MILES.value -> {
                    missDistanceUi?.miles?.toDouble()
                }
                else -> {}
            },
        )
    }

    private fun getDiameterMin(
        estimatedDiameterUi: EstimatedDiameterUi?,
        unit: String,
    ): String {
        return String.format(
            ContextCompat.getString(context, dev.stukalo.ui.R.string.estimated_diameter_value),
            when (unit) {
                dev.stukalo.datastore.Constants.Diameter.KILOMETERS.value -> {
                    estimatedDiameterUi?.kilometers?.estimatedDiameterMin
                }
                dev.stukalo.datastore.Constants.Diameter.METERS.value -> {
                    estimatedDiameterUi?.meters?.estimatedDiameterMin
                }
                dev.stukalo.datastore.Constants.Diameter.MILES.value -> {
                    estimatedDiameterUi?.miles?.estimatedDiameterMin
                }
                dev.stukalo.datastore.Constants.Diameter.FEET.value -> {
                    estimatedDiameterUi?.feet?.estimatedDiameterMin
                }
                else -> {}
            },
        )
    }

    private fun getDiameterMax(
        estimatedDiameterUi: EstimatedDiameterUi?,
        unit: String,
    ): String {
        return String.format(
            ContextCompat.getString(context, dev.stukalo.ui.R.string.estimated_diameter_value),
            when (unit) {
                dev.stukalo.datastore.Constants.Diameter.KILOMETERS.value -> {
                    estimatedDiameterUi?.kilometers?.estimatedDiameterMax
                }
                dev.stukalo.datastore.Constants.Diameter.METERS.value -> {
                    estimatedDiameterUi?.meters?.estimatedDiameterMax
                }
                dev.stukalo.datastore.Constants.Diameter.MILES.value -> {
                    estimatedDiameterUi?.miles?.estimatedDiameterMax
                }
                dev.stukalo.datastore.Constants.Diameter.FEET.value -> {
                    estimatedDiameterUi?.feet?.estimatedDiameterMax
                }
                else -> {}
            },
        )
    }

    private fun colorBasedOnValue(
        comp: Double,
        curr: Double,
    ): Int {
        return if (comp > curr) {
            ContextCompat.getColor(context, dev.stukalo.ui.R.color.green_900)
        } else if (comp < curr) {
            ContextCompat.getColor(context, dev.stukalo.ui.R.color.red_900)
        } else {
            ContextCompat.getColor(context, dev.stukalo.ui.R.color.primary)
        }
    }
}
