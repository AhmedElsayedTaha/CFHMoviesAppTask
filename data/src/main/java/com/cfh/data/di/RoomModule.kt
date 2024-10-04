package com.cfh.data.di

import android.content.Context
import androidx.room.Room
import com.cfh.data.local.MoviesDB
import com.cfh.data.local.MoviesDao
import com.cfh.data.utils.Consts.DB_NAME
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RoomModule {

    @Provides
    @Singleton
    fun provideDatabase(
        @ApplicationContext context: Context
    ): MoviesDB {
        return Room.databaseBuilder(
            context,
            MoviesDB::class.java,
            DB_NAME
        )
            .fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    fun provideDao(
        moviesDB: MoviesDB
    ): MoviesDao {
        return moviesDB.dao
    }

}