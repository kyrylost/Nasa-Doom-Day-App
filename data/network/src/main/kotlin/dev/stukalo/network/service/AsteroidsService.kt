package dev.stukalo.network.service

import dev.stukalo.network.model.AsteroidsResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface AsteroidsService {
    @GET("/neo/rest/v1/feed")
    suspend fun getAsteroids(
        @Query("start_date") startDate: String,
        @Query("end_date") endDate: String,
        @Query("api_key") apiKey: String = API_KEY,
    ): AsteroidsResponse
}

const val API_KEY = "DBKbDbj4Wj6k0FTeIvqjU1vIr0cuUaFvFBfh4OjQ"
