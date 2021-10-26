package com.example.rundown.api

import com.example.rundown.model.*
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface SportsAPI {

    @GET("lookupteam.php")
    suspend fun getTeam(
        @Query("id") idTeam: Int
    ): Response<Teams>

    @GET("search_all_seasons.php?id=4391")
    suspend fun getSeasons(): Response<Seasons>

    @GET("eventsround.php")
    suspend fun getEvents(
        @Query("id") idLeague: Int,
        @Query("r") round: Int,
        @Query("s") season: String
    ): Response<Events>

}