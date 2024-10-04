package com.cfh.cfhmoviesapptask.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.cfh.cfhmoviesapptask.intent.MoviesIntent
import com.cfh.cfhmoviesapptask.utils.ApiState
import com.cfh.domain.model.Movies
import com.cfh.domain.usecases.MoviesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MoviesViewModel @Inject constructor(
    private val moviesUseCase: MoviesUseCase
) : ViewModel() {

    private val moviesIntentMutableLiveData = MutableLiveData<MoviesIntent>()
    private val moviesIntentObserver = Observer<MoviesIntent> {
        when (it) {
            is MoviesIntent.PopularMoviesIntent -> getPopularMovies(it.language,it.includeAdult)
        }
    }
    init {
        moviesIntentMutableLiveData.observeForever(moviesIntentObserver)
    }

    internal fun send(moviesIntent: MoviesIntent) {
        moviesIntentMutableLiveData.value = moviesIntent
    }

    private val _moviesStateFlow = MutableStateFlow<ApiState<PagingData<Movies>>>(ApiState.Idle)
    val moviesStateFlow = _moviesStateFlow


    private fun getPopularMovies(language: String,includeAdult: Boolean) {

        viewModelScope.launch {
            try {
                _moviesStateFlow.emit(ApiState.Loading)
                moviesUseCase.execute(language,includeAdult)
                    .cachedIn(viewModelScope)
                    .collectLatest { pagingData ->
                        _moviesStateFlow.emit(ApiState.Success(pagingData))
                    }
            } catch (e: Exception) {
                _moviesStateFlow.emit(ApiState.Error(e.message.toString()))
            }
        }
    }

    fun removeObserver(){
        moviesIntentMutableLiveData.removeObserver(moviesIntentObserver)
    }
}