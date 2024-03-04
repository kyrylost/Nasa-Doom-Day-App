package dev.stukalo.network.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class LinksToDetails(
    @Json(name = "self")
    val self: String?,
)
