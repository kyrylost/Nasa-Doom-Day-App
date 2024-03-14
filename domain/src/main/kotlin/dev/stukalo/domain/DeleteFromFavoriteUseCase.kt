package dev.stukalo.domain

import dev.stukalo.database.repo.FavoriteAsteroidsRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class DeleteFromFavoriteUseCase
    @Inject
    constructor(
        private val favoriteAsteroidsRepository: FavoriteAsteroidsRepository,
    ) {
        operator fun invoke(id: String?) {
            CoroutineScope(Dispatchers.IO).launch {
                id?.let {
                    favoriteAsteroidsRepository.deleteAsteroid(id)
                }
            }
        }
    }
