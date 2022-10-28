package com.example.tmdb_app.views.genres

import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.tmdb_app.models.Genres
import com.example.tmdb_app.remote.ApiService
import kotlinx.coroutines.flow.MutableStateFlow
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class GenresViewModel: ViewModel() {
    val genresList = MutableStateFlow(Genres())
    val loading = MutableStateFlow(false)

    fun getGenres() {
        loading.value = true
        viewModelScope.launch {
            val response = ApiService.api.getGenres()
            if (response.isSuccessful) {
                genresList.value = response.body() ?: Genres()
                Log.v("GenresViewModel", "Genres: ${genresList.value}")
            } else {
                Log.e("GenresViewModel", "Error: ${response.errorBody()}")
            }
            loading.value = false
        }
    }
}