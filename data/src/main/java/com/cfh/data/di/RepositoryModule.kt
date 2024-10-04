package com.cfh.data.di

import com.cfh.data.local.MoviesDB
import com.cfh.data.remote.MoviesApiServices
import com.cfh.data.repository.MoviesRepositoryImp
import com.cfh.domain.repository.MoviesRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun provideMoviesRepository(
        moviesApiServices: MoviesApiServices,
        moviesDB: MoviesDB,
    ): MoviesRepository {
        return MoviesRepositoryImp(moviesApiServices, moviesDB)
    }
}