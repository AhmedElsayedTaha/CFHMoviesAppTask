package com.cfh.data.mappers

import com.cfh.data.local.entity.MoviesEntity
import com.cfh.data.remote.response.MoviesDto
import com.cfh.data.remote.response.MoviesResponse
import com.cfh.domain.model.Movies


fun MoviesDto.toMoviesEntity() =
    MoviesEntity(
        backdropPath = backdropPath.orEmpty(),
        id = id ?: 0,
        originalTitle = originalTitle.orEmpty(),
        overview = overview.orEmpty(),
        popularity = popularity ?: 0.0,
        posterPath = posterPath.orEmpty(),
        releaseDate = releaseDate.orEmpty(),
        title = title.orEmpty(),
        voteAverage = voteAverage ?: 0.0,
        voteCount = voteCount ?: 0
    )

fun MoviesEntity.toMovies() =
    Movies(
        backdropPath = backdropPath,
        id = id,
        originalTitle = originalTitle,
        overview = overview,
        popularity = popularity,
        posterPath = posterPath,
        releaseDate = releaseDate,
        title = title,
        voteAverage = voteAverage,
        voteCount = voteCount
    )