package com.cfh.cfhmoviesapptask.ui.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.cfh.cfhmoviesapptask.R
import com.cfh.cfhmoviesapptask.databinding.FragmentMoviesDetailsBinding
import com.cfh.cfhmoviesapptask.utils.Consts.IMAGE_URL
import com.cfh.data.utils.Consts
import java.text.DecimalFormat


class MoviesDetailsFragment : Fragment() {

    private lateinit var binding: FragmentMoviesDetailsBinding
    private val args: MoviesDetailsFragmentArgs by navArgs()
    @SuppressLint("SetTextI18n")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMoviesDetailsBinding.inflate(inflater,container,false)
        args.apply {
            Glide.with(requireActivity())
                .load(IMAGE_URL + movie.backdropPath)
                .placeholder(R.drawable.image_placeholder)
                .into(binding.posterImg)
            val decimalFormat = DecimalFormat("#.#")
            binding.movieNameTv.text = movie.title
            binding.descriptionTv.text = movie.overview
            binding.ratingTv.text = "${decimalFormat.format(movie.voteAverage)}/10"
        }

        return binding.root
    }

}