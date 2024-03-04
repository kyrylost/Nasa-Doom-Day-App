package dev.stukalo.network.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Meters(
    @Json(name = "estimated_diameter_max")
    val estimatedDiameterMax: Double?,
    @Json(name = "estimated_diameter_min")
    val estimatedDiameterMin: Double?,
)
