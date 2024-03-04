package dev.stukalo.impl

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import dev.stukalo.common.exception.ApiException
import dev.stukalo.network.source.AsteroidsNetSource
import dev.stukalo.repository.mapper.mapToAsteroidRepo
import dev.stukalo.repository.model.AsteroidRepo
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class AsteroidsPagingSource(
    private val asteroidsNetSource: AsteroidsNetSource,
    startDate: String,
    endDate: String,
    sortOrder: Int,
) : PagingSource<Int, Pair<String, List<AsteroidRepo>>>() {
    private var dates: MutableSet<String> = mutableSetOf()

    init {
        val dateFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd")
        var fromDate = LocalDate.parse(startDate, dateFormat)
        val toDate = LocalDate.parse(endDate, dateFormat)

        while (fromDate != toDate) {
            dates.add(fromDate.toString())
            fromDate = fromDate.plusDays(1)
        }
        dates.add(toDate.toString())
        if (sortOrder == 1) {
            dates = dates.reversed().toMutableSet()
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Pair<String, List<AsteroidRepo>>> {
        val currentDay = params.key ?: 0
        val dateToGet = dates.elementAt(currentDay)

        return try {
            val response = asteroidsNetSource.getAsteroids(dateToGet, dateToGet)
            val asteroids = response.nearEarthObjects?.get(dateToGet)?.map {
                it.mapToAsteroidRepo()
            } ?: emptyList()

            LoadResult.Page(
                data = listOf(Pair(dateToGet, asteroids)),
                prevKey = if (currentDay > 0) currentDay - 1 else null,
                nextKey = if (currentDay < (dates.count()-1)) currentDay + 1 else null
            )
        } catch (e: ApiException) {
            Log.e("Load Error", e.toString())
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Pair<String, List<AsteroidRepo>>>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey
        }
    }
}