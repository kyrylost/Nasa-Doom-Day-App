package dev.stukalo.impl

import dev.stukalo.network.model.AsteroidsResponse
import dev.stukalo.network.service.AsteroidsService
import dev.stukalo.network.source.AsteroidsNetSource

class AsteroidsNetSourceImpl(
    private val asteroidsService: AsteroidsService,
) : AsteroidsNetSource {
    override suspend fun getAsteroids(
        startDate: String,
        endDate: String,
    ): AsteroidsResponse {
        return try {
            asteroidsService.getAsteroids(startDate, endDate)
        } catch (e: Exception) {
            throw handleException(e)
        }
    }
}
