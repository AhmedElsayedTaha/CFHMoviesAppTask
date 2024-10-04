package com.cfh.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.cfh.data.local.entity.MoviesEntity
import com.cfh.data.local.entity.PagingInfo

@Database(entities = [MoviesEntity::class,PagingInfo::class], version = 1 )
abstract class MoviesDB: RoomDatabase() {
    abstract val dao: MoviesDao
}