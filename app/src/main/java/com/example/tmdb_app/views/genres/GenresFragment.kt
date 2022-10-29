package com.example.tmdb_app.views.genres

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
import androidx.navigation.fragment.findNavController
import com.example.tmdb_app.MainActivity
import com.example.tmdb_app.adapters.GenresAdapter
import com.example.tmdb_app.databinding.FragmentGenresBinding
import kotlinx.coroutines.launch

class GenresFragment : Fragment() {

    private val viewModel: GenresViewModel by activityViewModels()
    private lateinit var progressBar: ProgressBar
    private lateinit var adapter: GenresAdapter

    private var _binding: FragmentGenresBinding? = null
    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentGenresBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        progressBar = binding.genresProgressBar

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
                    viewModel.genresList.collect { genres ->
                        adapter.updateData(genres)
                    }
                }
            }
        }

        adapter = GenresAdapter{
            val action = GenresFragmentDirections.actionGenresFragmentToMoviesFragment(it.id)
            findNavController().navigate(action)
        }

        val recyclerView = binding.genresRecyclerView
        recyclerView.adapter = adapter

        viewModel.getGenres()
    }

    override fun onResume() {
        super.onResume()
        (activity as MainActivity).supportActionBar?.setDisplayHomeAsUpEnabled(false)
    }
}