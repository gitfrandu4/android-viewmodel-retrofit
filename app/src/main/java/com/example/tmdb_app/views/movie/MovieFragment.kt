package com.example.tmdb_app.views.movie

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.bumptech.glide.Glide
import com.example.tmdb_app.databinding.FragmentMovieBinding
import kotlinx.coroutines.launch

class MovieFragment : Fragment() {

    private val viewModel: MovieViewModel by activityViewModels()
    private lateinit var progressBar: ProgressBar

    private var _binding: FragmentMovieBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMovieBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val movieId = arguments?.getInt("movieId") ?: 0

        progressBar = binding.progressBar

        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    viewModel.loading.collect { loading ->
                        if (loading) {
                            progressBar.visibility = View.VISIBLE
                        } else {
                            progressBar.visibility = View.GONE
                        }
                    }
                }

                launch {
                    viewModel.movie.collect { movie ->
                        binding.movieTitle.text = movie.title
                        binding.movieOverview.text = movie.overview
                        binding.movieReleaseDate.text = movie.release_date

                        Glide.with(this@MovieFragment)
                            .load("https://image.tmdb.org/t/p/w500${movie.poster_path}")
                            .into(binding.imageView)
                    }
                }
            }
        }

        viewModel.getMovie(movieId)
    }
}