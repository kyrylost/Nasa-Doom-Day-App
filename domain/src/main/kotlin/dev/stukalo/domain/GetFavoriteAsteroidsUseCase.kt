package dev.stukalo.domain

import dev.stukalo.database.repo.FavoriteAsteroidsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class GetFavoriteAsteroidsUseCase
    @Inject
    constructor(
        private val favoriteAsteroidsRepository: FavoriteAsteroidsRepository,
    ) {
        suspend operator fun invoke() =
            withContext(Dispatchers.IO) {
                favoriteAsteroidsRepository.getFavoriteAsteroids()
            }
    }
