package com.example.rundown.repository

import com.example.rundown.api.RetrofitInstance
import com.example.rundown.model.*
import retrofit2.Response

class Repository {

    suspend fun getTeam(id: Int): Response<Teams> {
        return RetrofitInstance.api.getTeam(id)
    }

    suspend fun getSeasons(): Response<Seasons> {
        return RetrofitInstance.api.getSeasons()
    }

    suspend fun getEvents(id: Int, round: Int, season: String): Response<Events> {
        return RetrofitInstance.api.getEvents(id, round, season)
    }

}