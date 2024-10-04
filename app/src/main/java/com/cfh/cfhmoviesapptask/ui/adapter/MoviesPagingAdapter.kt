package com.cfh.cfhmoviesapptask.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.cfh.cfhmoviesapptask.R
import com.cfh.cfhmoviesapptask.databinding.ItemMovieLayoutBinding
import com.cfh.cfhmoviesapptask.utils.Consts
import com.cfh.domain.model.Movies

class MoviesPagingAdapter(
    private val onMovieItemClick: (Movies) -> Unit
) : PagingDataAdapter<Movies, MoviesPagingAdapter.MovieViewHolder>(MovieDiffCallBack()) {

    class MovieViewHolder(
        private val binding: ItemMovieLayoutBinding,
        private val onMovieItemClick: (Movies) -> Unit
    ) : RecyclerView.ViewHolder(
        binding.root
    ) {
        fun bind(movies: Movies) {
            movies.apply {
                Glide.with(binding.root.context)
                    .load(Consts.IMAGE_URL + posterPath)
                    .placeholder(R.drawable.image_placeholder)
                    .into(binding.imageView)

                binding.root.setOnClickListener {
                    onMovieItemClick.invoke(this)
                }
            }
        }
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val movies = getItem(position)
        movies?.let {
            holder.bind(it)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val binding = ItemMovieLayoutBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return MovieViewHolder(binding, onMovieItemClick)
    }


}

class MovieDiffCallBack : DiffUtil.ItemCallback<Movies>() {
    override fun areItemsTheSame(oldItem: Movies, newItem: Movies): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Movies, newItem: Movies): Boolean {
        return oldItem == newItem
    }

}