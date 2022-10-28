package com.example.tmdb_app.remote

import com.example.tmdb_app.models.Genres
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface Api {

    @GET("genre/movie/list")
    suspend fun getGenres(
        @Query("api_key") apiKey: String = ApiService.API_KEY,
        @Query("language") language: String = ApiService.LANGUAGE
    ): Response<Genres>
}