package dev.stukalo.domain

import dev.stukalo.database.repo.FavoriteAsteroidsRepository
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito
import org.mockito.kotlin.mock
import org.mockito.kotlin.atLeastOnce
import org.mockito.kotlin.verify

class GetFavoriteAsteroidsUseCaseTest {

    private lateinit var getFavoriteAsteroidsUseCase: GetFavoriteAsteroidsUseCase
    private val favoriteAsteroidsRepository: FavoriteAsteroidsRepository = mock()

    @Before
    fun setUp() {
        Mockito.reset(favoriteAsteroidsRepository)
        getFavoriteAsteroidsUseCase = GetFavoriteAsteroidsUseCase(favoriteAsteroidsRepository)
    }

    @Test
    fun `when get favorite asteroids use case invoked call getFavoriteAsteroids`() =
        runTest {
            getFavoriteAsteroidsUseCase()
            verify(favoriteAsteroidsRepository, atLeastOnce()).getFavoriteAsteroids()
        }
}