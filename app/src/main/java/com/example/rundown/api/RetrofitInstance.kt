package com.example.rundown.api

import retrofit2.Retrofit
import com.example.rundown.util.Constants.Companion.BASE_URL
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {

    private val retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val api: SportsAPI by lazy {
        retrofit.create(SportsAPI::class.java)
    }
}