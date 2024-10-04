package com.cfh.cfhmoviesapptask.ui.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.GridLayout
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import androidx.recyclerview.widget.GridLayoutManager
import com.cfh.cfhmoviesapptask.databinding.FragmentMoviesBinding
import com.cfh.cfhmoviesapptask.intent.MoviesIntent
import com.cfh.cfhmoviesapptask.ui.adapter.MovieLoadStateAdapter
import com.cfh.cfhmoviesapptask.ui.adapter.MoviesPagingAdapter
import com.cfh.cfhmoviesapptask.utils.ApiState
import com.cfh.cfhmoviesapptask.utils.Consts
import com.cfh.cfhmoviesapptask.viewmodels.MoviesViewModel
import com.cfh.domain.model.Movies
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch


@AndroidEntryPoint
class MoviesFragment : Fragment() {
    private lateinit var binding: FragmentMoviesBinding
    private lateinit var moviesAdapter: MoviesPagingAdapter
    private val viewModel: MoviesViewModel by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMoviesBinding.inflate(inflater,container,false)
        initAdapter()
        getPopularMovies()

        return binding.root
    }

    private fun initAdapter(){
        moviesAdapter = MoviesPagingAdapter{
            onMovieItemClick(it)
        }
        binding.movieRec.apply {
            layoutManager = GridLayoutManager(requireActivity(),2)
            adapter = moviesAdapter.withLoadStateFooter(
                footer = MovieLoadStateAdapter {moviesAdapter.retry()}
            )
        }

        lifecycleScope.launch {
            moviesAdapter.loadStateFlow.collect{
                val state = it.refresh

                binding.progressBar.isVisible = state is LoadState.Loading
            }
        }

       observeLoadState()
    }

    private fun getPopularMovies(){
        showProgressBar(true)
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED){
                viewModel.send(MoviesIntent.PopularMoviesIntent(Consts.LANGUAGE_VALUE,false))
                viewModel.moviesStateFlow.collectLatest { apiState ->
                    when(apiState){
                        is ApiState.Loading -> {
                            showProgressBar(true)
                        }
                        is ApiState.Success -> {
                            showProgressBar(false)
                            moviesAdapter.submitData(apiState.data)
                        }
                        is ApiState.Error -> {
                            showProgressBar(false)
                        }
                        is ApiState.Idle -> {}
                    }

                }
            }
        }
        observeLoadState()
    }

    private fun showProgressBar(isShow: Boolean){
        binding.progressBar.isVisible = isShow
    }

    override fun onStop() {
        super.onStop()
        viewModel.removeObserver()
    }

    private fun observeLoadState() {
        moviesAdapter.addLoadStateListener { loadState ->
            when (val refresh = loadState.source.refresh) {
                is LoadState.Loading -> {
                    showProgressBar(true)
                }
                is LoadState.Error -> {
                    Log.e("MoviesFragment", "LoadState Error: ${refresh.error.message}")
                    showProgressBar(false)
                }
                is LoadState.NotLoading -> {
                    showProgressBar(false)
                }
            }
        }
    }

    private fun onMovieItemClick(movies: Movies){
        val action = MoviesFragmentDirections.actionMoviesFragmentToMoviesDetailsFragment(movies)
        findNavController().navigate(action)
    }
}