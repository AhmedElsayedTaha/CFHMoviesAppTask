package com.cfh.data.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import com.cfh.data.local.MoviesDB
import com.cfh.data.mappers.toMovies
import com.cfh.data.remote.MoviesApiServices
import com.cfh.data.utils.Consts
import com.cfh.domain.model.Movies
import com.cfh.domain.repository.MoviesRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

@OptIn(ExperimentalPagingApi::class)
class MoviesRepositoryImp @Inject constructor(
    private val moviesApiServices: MoviesApiServices,
    private val moviesDB: MoviesDB,
) : MoviesRepository {
    override fun getPopularMovies(language: String,includeAdult: Boolean): Flow<PagingData<Movies>> {
        return Pager(
            config = PagingConfig(
                20,
                initialLoadSize = 20,
                prefetchDistance = 1,
                enablePlaceholders = false
            ),
            remoteMediator = MoviesRemoteMediator(
                moviesApiServices = moviesApiServices,
                moviesDB = moviesDB,
                language = language,
                includeAdult
            ),
            pagingSourceFactory = { moviesDB.dao.getMoviesFromDB() }
        ).flow.map { pagingData ->
            pagingData.map {
                it.toMovies()
            }
        }
    }
}