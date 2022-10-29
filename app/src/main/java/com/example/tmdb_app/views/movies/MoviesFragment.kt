package com.example.tmdb_app.views.movies

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
import com.example.tmdb_app.adapters.MoviesAdapter
import com.example.tmdb_app.databinding.FragmentMoviesBinding
import kotlinx.coroutines.launch

class MoviesFragment : Fragment() {

    private val viewModel: MoviesViewModel by activityViewModels()
    private lateinit var progressBar: ProgressBar
    private lateinit var adapter: MoviesAdapter

    private var _binding: FragmentMoviesBinding? = null
    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMoviesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val genreId = arguments?.getInt("genreId") ?: 0

        progressBar = binding.moviesProgressBar

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
                    viewModel.moviesList.collect { movies ->
                        adapter.updateData(movies)
                    }
                }
            }
        }

        adapter = MoviesAdapter{ movie ->
//            val action = MoviesFragmentDirections.actionMoviesFragmentToMovieDetailsFragment(movie)
//            findNavController().navigate(action)
        }

        val recyclerView = binding.moviesRecyclerView
        recyclerView.adapter = adapter

        viewModel.getMoviesByGenre(genreId)
    }
}