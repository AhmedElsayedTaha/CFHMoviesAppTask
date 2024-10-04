package com.cfh.data.remote


import com.cfh.data.remote.response.MoviesResponse
import com.cfh.data.utils.Consts
import retrofit2.http.GET
import retrofit2.http.Query

interface MoviesApiServices {
    @GET(Consts.POPULAR_MOVIES_END_POINTS)
    suspend fun getPopularMovies(
        @Query(Consts.INCLUDE_ADULT) includeAdult: Boolean,
        @Query(Consts.LANGUAGE_PARAM) language: String,
        @Query(Consts.PAGE_PARAM) page: Int
    ): MoviesResponse
}