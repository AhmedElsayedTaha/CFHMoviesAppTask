package com.cfh.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize


@Parcelize
data class Movies(
    val backdropPath: String = "",
    val id: Int= 0,
    val originalTitle: String = "",
    val overview: String = "",
    val popularity: Double = 0.0,
    val posterPath: String = "",
    val releaseDate: String = "",
    val title: String = "",
    val voteAverage: Double = 0.0,
    val voteCount: Int = 0,
): Parcelable
