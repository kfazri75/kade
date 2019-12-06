package dev.nuris.footballleague.repository

import androidx.lifecycle.LiveData
import dev.nuris.footballleague.helper.lib.SingleLiveEvent
import dev.nuris.footballleague.http.DataWrapper
import dev.nuris.footballleague.model.response.Leagues
import dev.nuris.footballleague.source.LeagueDataSource

class LeagueRepositoryImpl(private val leagueDataSource: LeagueDataSource) :
    LeagueRepository {

    init {
        leagueDataSource.leagues.observeForever {
            try {
                _leagues.postValue(it)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    private val _leagues = SingleLiveEvent<DataWrapper<Leagues>>()
    override val leagues: LiveData<DataWrapper<Leagues>>
        get() = _leagues

    override fun getLeague() {
        leagueDataSource.getLeague()
    }
}