package dev.stukalo.network.source

import dev.stukalo.network.model.AsteroidsResponse
import dev.stukalo.network.util.ExceptionHandler

interface AsteroidsNetSource: ExceptionHandler {
    suspend fun getAsteroids(
        startDate: String,
        endDate: String,
    ): AsteroidsResponse
}
