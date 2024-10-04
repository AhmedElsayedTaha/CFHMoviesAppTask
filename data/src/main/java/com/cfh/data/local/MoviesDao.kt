package com.cfh.data.local

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.cfh.data.local.entity.MoviesEntity
import com.cfh.data.local.entity.PagingInfo
import com.cfh.data.utils.Consts

@Dao
interface MoviesDao {
    @Upsert
    suspend fun upsertMovies(movies: List<MoviesEntity>)

    @Query("SELECT * FROM ${Consts.MOVIES_TABLE_NAME}")
    fun getMoviesFromDB(): PagingSource<Int, MoviesEntity>

    @Query("DELETE FROM ${Consts.MOVIES_TABLE_NAME}")
    suspend fun clearMovies()

    @Upsert
    suspend fun savePagingInfo(pagingInfo: PagingInfo)

    @Query("SELECT lastFetchedPage FROM ${Consts.PAGING_INFO} WHERE id = 1")
    suspend fun getLastFetchedPage(): Int?
}