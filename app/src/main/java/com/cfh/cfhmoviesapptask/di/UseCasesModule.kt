package com.cfh.cfhmoviesapptask.di

import com.cfh.domain.repository.MoviesRepository
import com.cfh.domain.usecases.MoviesUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object UseCasesModule {

    @Provides
    fun provideMoviesUseCase(moviesRepository: MoviesRepository): MoviesUseCase{
        return MoviesUseCase(moviesRepository)
    }
}