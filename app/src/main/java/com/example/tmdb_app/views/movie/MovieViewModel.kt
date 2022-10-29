package com.example.tmdb_app.views.movie

import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.tmdb_app.models.Genres
import com.example.tmdb_app.remote.ApiService
import kotlinx.coroutines.flow.MutableStateFlow
import androidx.lifecycle.viewModelScope
import com.example.tmdb_app.models.Movie
import com.example.tmdb_app.models.Movies
import kotlinx.coroutines.launch

class MovieViewModel: ViewModel() {
    val movie = MutableStateFlow(Movie())
    val loading = MutableStateFlow(false)

    fun getMovie(movieId: Int) {
        loading.value = true
        viewModelScope.launch {
            val response = ApiService.api.getMovie(movieId = movieId)
            if (response.isSuccessful) {
                movie.value = response.body() ?: Movie()
                Log.v("MovieViewModel", "Movie: ${movie.value}")
            } else {
                Log.e("MovieViewModel", "Error: ${response.errorBody()}")
            }
            loading.value = false
        }
    }
}