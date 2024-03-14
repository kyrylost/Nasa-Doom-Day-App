package dev.stukalo.network.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class RelativeVelocity(
    @Json(name = "kilometers_per_hour")
    val kilometersPerHour: String?,
    @Json(name = "kilometers_per_second")
    val kilometersPerSecond: String?,
    @Json(name = "miles_per_hour")
    val milesPerHour: String?,
)
