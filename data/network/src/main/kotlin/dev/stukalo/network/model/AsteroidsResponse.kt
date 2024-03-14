package dev.stukalo.network.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class AsteroidsResponse(
    @Json(name = "element_count")
    val elementCount: Int?,
    @Json(name = "links")
    val links: Links?,
    @Json(name = "near_earth_objects")
    val nearEarthObjects: Map<String, List<Asteroid>>?,
)
