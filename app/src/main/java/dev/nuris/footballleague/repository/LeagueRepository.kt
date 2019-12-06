package dev.nuris.footballleague.repository

import androidx.lifecycle.LiveData
import dev.nuris.footballleague.http.DataWrapper
import dev.nuris.footballleague.model.response.Leagues

interface LeagueRepository {
    val leagues: LiveData<DataWrapper<Leagues>>

    fun getLeague()
}