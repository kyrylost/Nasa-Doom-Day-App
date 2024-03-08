package dev.stukalo.asteroiddetails.util

import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dev.stukalo.common.model.AsteroidUi

object AsteroidAdapter {
    private var moshi: Moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()
    private var jsonAdapter: JsonAdapter<AsteroidUi> = moshi.adapter(AsteroidUi::class.java)

    fun fromJson(asteroidUiJson: String): AsteroidUi? = jsonAdapter.fromJson(asteroidUiJson)
}
