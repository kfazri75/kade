package dev.nuris.footballleague.source

import androidx.lifecycle.LiveData
import dev.nuris.footballleague.http.DataWrapper
import dev.nuris.footballleague.model.response.Leagues

interface LeagueDataSource {
    val leagues: LiveData<DataWrapper<Leagues>>

    fun getLeague()
}