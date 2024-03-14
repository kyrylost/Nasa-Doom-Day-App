package dev.stukalo.network.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class CloseApproachData(
    @Json(name = "close_approach_date")
    val closeApproachDate: String?,
    @Json(name = "close_approach_date_full")
    val closeApproachDateFull: String?,
    @Json(name = "epoch_date_close_approach")
    val epochDateCloseApproach: Long?,
    @Json(name = "miss_distance")
    val missDistance: MissDistance?,
    @Json(name = "orbiting_body")
    val orbitingBody: String?,
    @Json(name = "relative_velocity")
    val relativeVelocity: RelativeVelocity?,
)
