@file:OptIn(ExperimentalPagingApi::class)

package com.cfh.data.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.cfh.data.local.MoviesDB
import com.cfh.data.local.entity.MoviesEntity
import com.cfh.data.local.entity.PagingInfo
import com.cfh.data.mappers.toMoviesEntity
import com.cfh.data.remote.MoviesApiServices


class MoviesRemoteMediator(
    private val moviesApiServices: MoviesApiServices,
    private val moviesDB: MoviesDB,
    private val language: String,
    private val includeAdult: Boolean
) : RemoteMediator<Int, MoviesEntity>() {
    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, MoviesEntity>
    ): MediatorResult {
        return try {
            val page = when (loadType) {
                LoadType.REFRESH -> {
                    1
                }
                LoadType.PREPEND -> return MediatorResult.Success(endOfPaginationReached = true)
                LoadType.APPEND -> {
                    val lastItem = state.lastItemOrNull()
                    if (lastItem == null) {
                        return MediatorResult.Success(endOfPaginationReached = true)
                    } else {
                        val lastFetchedPage = moviesDB.dao.getLastFetchedPage() ?: 1
                        lastFetchedPage + 1
                    }
                }
            }

            val response = moviesApiServices.getPopularMovies(includeAdult,language, page)
            moviesDB.apply {
                withTransaction {
                    if (loadType == LoadType.REFRESH) {
                        dao.clearMovies()
                    }
                    response.moviesDtos?.let {
                        dao.upsertMovies(it.mapNotNull { moviesDto ->
                            moviesDto?.toMoviesEntity()
                        })
                    }
                }
                dao.savePagingInfo(PagingInfo(lastFetchedPage = page))
            }

            val endOfPaginationReached = response.moviesDtos?.isEmpty() == true

            MediatorResult.Success(endOfPaginationReached = endOfPaginationReached)
        } catch (e: Exception) {
            MediatorResult.Error(e)
        }
    }

}