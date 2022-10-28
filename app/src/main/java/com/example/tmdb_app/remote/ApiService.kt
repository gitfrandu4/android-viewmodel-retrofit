package com.example.tmdb_app.remote

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.net.URL

object ApiService {
    lateinit var api: Api

    private const val URL = "https://api.themoviedb.org/3/"
    const val API_KEY = "509d1a5ea9e6f3d82518365bfff8e014"
    const val LANGUAGE = "es-ES"

    init {
        initService()
    }

    private fun initService() {
        Retrofit.Builder()
            .baseUrl(URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(Api::class.java)
            .also {
                api = it
            }
    }
}