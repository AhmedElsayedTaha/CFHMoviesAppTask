package com.cfh.domain.repository

import androidx.paging.PagingData
import com.cfh.domain.model.Movies
import kotlinx.coroutines.flow.Flow

interface MoviesRepository {
    fun getPopularMovies(language: String,includeAdult: Boolean): Flow<PagingData<Movies>>
}