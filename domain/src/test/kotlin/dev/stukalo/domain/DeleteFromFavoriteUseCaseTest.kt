package dev.stukalo.domain

import dev.stukalo.database.repo.FavoriteAsteroidsRepository
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito
import org.mockito.kotlin.any
import org.mockito.kotlin.mock
import org.mockito.kotlin.never
import org.mockito.kotlin.atLeastOnce
import org.mockito.kotlin.doNothing
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

class DeleteFromFavoriteUseCaseTest {

    private lateinit var deleteFromFavoriteUseCase: DeleteFromFavoriteUseCase
    private val favoriteAsteroidsRepository: FavoriteAsteroidsRepository = mock()

    @Before
    fun setUp() {
        Mockito.reset(favoriteAsteroidsRepository)
        deleteFromFavoriteUseCase = DeleteFromFavoriteUseCase(favoriteAsteroidsRepository)
    }

    @Test
    fun `if id is not null asteroid must be deleted`() =
        runTest {
            val id = "id"
            doNothing().whenever(favoriteAsteroidsRepository).deleteAsteroid(any())

            deleteFromFavoriteUseCase(id)
            verify(favoriteAsteroidsRepository, atLeastOnce()).deleteAsteroid(any())
        }

    @Test
    fun `if id is null asteroid must not deleted`() =
        runTest {
            val id = null
            doNothing().whenever(favoriteAsteroidsRepository).deleteAsteroid(any())

            deleteFromFavoriteUseCase(id)
            verify(favoriteAsteroidsRepository, never()).deleteAsteroid(any())
        }

}
