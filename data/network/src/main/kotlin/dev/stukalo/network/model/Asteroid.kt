package dev.stukalo.network.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Asteroid(
    @Json(name = "absolute_magnitude_h")
    val absoluteMagnitudeH: Double?,
    @Json(name = "close_approach_data")
    val closeApproachData: List<CloseApproachData>?,
    @Json(name = "estimated_diameter")
    val estimatedDiameter: EstimatedDiameter?,
    @Json(name = "id")
    val id: String?,
    @Json(name = "is_potentially_hazardous_asteroid")
    val isPotentiallyHazardousAsteroid: Boolean?,
    @Json(name = "is_sentry_object")
    val isSentryObject: Boolean?,
    @Json(name = "links")
    val links: LinksToDetails?,
    @Json(name = "name")
    val name: String?,
    @Json(name = "nasa_jpl_url")
    val nasaJplUrl: String?,
    @Json(name = "neo_reference_id")
    val neoReferenceId: String?,
)
