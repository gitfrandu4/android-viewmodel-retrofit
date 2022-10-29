package com.example.tmdb_app.remote

import com.example.tmdb_app.models.Genres
import com.example.tmdb_app.models.Movie
import com.example.tmdb_app.models.Movies
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface Api {

    @GET("genre/movie/list")
    suspend fun getGenres(
        @Query("api_key") apiKey: String = ApiService.API_KEY,
        @Query("language") language: String = ApiService.LANGUAGE
    ): Response<Genres>

    @GET("discover/movie")
    suspend fun getMovies(
        @Query("api_key") apiKey: String = ApiService.API_KEY,
        @Query("language") language: String = ApiService.LANGUAGE,
        @Query("sort_by") sortBy: String = ApiService.SORT_BY,
        @Query("include_adult") adult: String = ApiService.ADULT,
        @Query("include_video") video: String = "false",
        @Query("page") page: Int = 1,
        @Query("with_genres") genreId: Int
    ): Response<Movies>

    @GET("movie/{movie_id}")
    suspend fun getMovie(
        @Path("movie_id") movieId: Int,
        @Query("api_key") apiKey: String = ApiService.API_KEY,
        @Query("language") language: String = ApiService.LANGUAGE
    ): Response<Movie>
}