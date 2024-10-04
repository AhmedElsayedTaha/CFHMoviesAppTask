package com.cfh.cfhmoviesapptask

import android.util.Log
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import androidx.paging.PagingData
import com.cfh.cfhmoviesapptask.intent.MoviesIntent
import com.cfh.cfhmoviesapptask.utils.ApiState
import com.cfh.cfhmoviesapptask.utils.Consts
import com.cfh.cfhmoviesapptask.viewmodels.MoviesViewModel
import com.cfh.data.local.entity.MoviesEntity
import com.cfh.domain.model.Movies
import com.cfh.domain.usecases.MoviesUseCase
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.fail
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.cancel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito.verify
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever

@FlowPreview
@ExperimentalCoroutinesApi
class MoviesViewModelTest {


        @get:Rule
        val instantExecutorRule = InstantTaskExecutorRule()

        private val moviesUseCase: MoviesUseCase = mock()
        private lateinit var viewModel: MoviesViewModel

        private val testDispatcher = StandardTestDispatcher()

        @Before
        fun setUp() {
            // Set the main dispatcher for coroutines
            Dispatchers.setMain(testDispatcher)

            viewModel = MoviesViewModel(moviesUseCase)
        }

        @After
        fun tearDown() {
            Dispatchers.resetMain()
        }
    @Test
    fun `getPopularMovies success should emit ApiState Success`() = runBlocking {
        // Arrange
        val language = Consts.LANGUAGE_VALUE
        val includeAdult = false
        val mockPagingData: PagingData<Movies> = mock() // This will be returned

        // Return an empty or different mock
        whenever(moviesUseCase.execute(language, includeAdult)).thenReturn(flowOf(mockPagingData))

        // Act
        viewModel.send(MoviesIntent.PopularMoviesIntent(language, includeAdult))
        // Collect the stateFlow values
        val job = launch {
            viewModel.moviesStateFlow.collect { state ->
                // Assert
                when (state) {
                    is ApiState.Success -> {
                        // Use an actual assertion instead of logging
                        assertEquals(mockPagingData, state.data)
                        // Optionally break or cancel the job if success is expected only once
                        cancel()
                    }
                    is ApiState.Error -> {
                        fail("Expected ApiState.Success but got ApiState.Error: ${state.message}")
                    }

                    ApiState.Idle -> {
                        fail("Expected ApiState.Success but got ApiState.Idle: ${state}")
                    }
                    ApiState.Loading -> {
                        fail("Expected ApiState.Success but got ApiState.Loading: ${state}")
                    }
                }
            }
        }



        // Delay to allow for the flow to emit
        delay(100) // Adjust this if necessary

        job.cancel() // Cancel the collection job
    }

        @Test
        fun `getPopularMovies failure should emit ApiState Error`() = runBlocking {
            // Arrange
            val language = "en"
            val includeAdult = false
            val errorMessage = "Network error"
            whenever(moviesUseCase.execute(language, includeAdult)).thenThrow(RuntimeException(errorMessage))

            // Act
            viewModel.send(MoviesIntent.PopularMoviesIntent(language, includeAdult))

            // Collect the stateFlow values
            val job = launch {
                viewModel.moviesStateFlow.collect { state ->
                    // Assert
                    if (state is ApiState.Error) {
                        assertEquals(errorMessage, state.message)
                    }
                }
            }

            // Delay to allow for the flow to emit
            delay(100) // Adjust this if necessary

            job.cancel() // Cancel the collection job
        }

    }