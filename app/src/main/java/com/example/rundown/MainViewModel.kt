package com.example.rundown

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.rundown.model.*
import com.example.rundown.repository.Repository
import kotlinx.coroutines.launch
import retrofit2.Response

class MainViewModel(private val repository: Repository): ViewModel() {

    var teamResponse: MutableLiveData<Response<Teams>> = MutableLiveData()
    var seasonsResponse: MutableLiveData<Response<Seasons>> = MutableLiveData()
    var eventsResponse: MutableLiveData<Response<Events>> = MutableLiveData()

    fun getTeam(id: Int) {
        viewModelScope.launch {
            val response = repository.getTeam(id)
            teamResponse.value = response
        }
    }

    fun getSeasons() {
        viewModelScope.launch {
            val response = repository.getSeasons()
            seasonsResponse.value = response
        }
    }

    fun getEvents(id: Int, round: Int, season: String) {
        viewModelScope.launch {
            val response = repository.getEvents(id, round, season)
            eventsResponse.value = response
        }
    }
}