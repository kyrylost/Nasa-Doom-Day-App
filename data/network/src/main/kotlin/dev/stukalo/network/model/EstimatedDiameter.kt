package dev.stukalo.network.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class EstimatedDiameter(
    @Json(name = "feet")
    val feet: Feet?,
    @Json(name = "kilometers")
    val kilometers: Kilometers?,
    @Json(name = "meters")
    val meters: Meters?,
    @Json(name = "miles")
    val miles: Miles?,
)
