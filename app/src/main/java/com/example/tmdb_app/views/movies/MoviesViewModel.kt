package com.example.tmdb_app.views.movies

import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.tmdb_app.models.Genres
import com.example.tmdb_app.remote.ApiService
import kotlinx.coroutines.flow.MutableStateFlow
import androidx.lifecycle.viewModelScope
import com.example.tmdb_app.models.Movies
import kotlinx.coroutines.launch

class MoviesViewModel: ViewModel() {
    val moviesList = MutableStateFlow(Movies())
    val loading = MutableStateFlow(false)

    fun getMoviesByGenre(genreId: Int) {
        loading.value = true
        viewModelScope.launch {
            val response = ApiService.api.getMovies(genreId = genreId)
            if (response.isSuccessful) {
                moviesList.value = response.body() ?: Movies()
                Log.v("GenresViewModel", "Genres: ${moviesList.value}")
            } else {
                Log.e("GenresViewModel", "Error: ${response.errorBody()}")
            }
            loading.value = false
        }
    }
}