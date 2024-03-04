package dev.stukalo.network.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class MissDistance(
    @Json(name = "astronomical")
    val astronomical: String?,
    @Json(name = "kilometers")
    val kilometers: String?,
    @Json(name = "lunar")
    val lunar: String?,
    @Json(name = "miles")
    val miles: String?,
)
