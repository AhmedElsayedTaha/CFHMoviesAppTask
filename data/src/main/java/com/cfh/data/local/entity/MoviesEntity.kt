package com.cfh.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.cfh.data.utils.Consts.MOVIES_TABLE_NAME


@Entity(tableName = MOVIES_TABLE_NAME)
data class MoviesEntity(
    @PrimaryKey
    val id: Int,
    val backdropPath: String ,
    val originalTitle: String,
    val overview: String,
    val popularity: Double,
    val posterPath: String,
    val releaseDate: String,
    val title: String,
    val voteAverage: Double,
    val voteCount: Int
)
