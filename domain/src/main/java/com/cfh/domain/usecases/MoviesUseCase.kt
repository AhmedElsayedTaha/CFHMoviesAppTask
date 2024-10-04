package com.cfh.domain.usecases

import androidx.paging.PagingData
import com.cfh.domain.model.Movies
import com.cfh.domain.repository.MoviesRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class MoviesUseCase @Inject constructor(
    private val moviesRepository: MoviesRepository
) {
     fun execute(language: String,includeAdult: Boolean): Flow<PagingData<Movies>>{
        return moviesRepository.getPopularMovies(language,includeAdult)
    }
}