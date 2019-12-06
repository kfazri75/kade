package dev.nuris.footballleague.repository

import androidx.lifecycle.LiveData
import dev.nuris.footballleague.http.DataWrapper
import dev.nuris.footballleague.model.response.Events
import dev.nuris.footballleague.model.response.Leagues

interface LeagueDetailRepository {
    val getLeaguesResponse: LiveData<DataWrapper<Leagues>>
    val getNextEventResponse: LiveData<DataWrapper<Events>>
    val getPastEventResponse: LiveData<DataWrapper<Events>>

    fun getLeagueDetail(id: String)
    fun getNextEvent(id: String)
    fun getPastEvent(id: String)
}