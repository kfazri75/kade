package dev.nuris.footballleague.repository

import androidx.lifecycle.LiveData
import dev.nuris.footballleague.helper.lib.SingleLiveEvent
import dev.nuris.footballleague.http.DataWrapper
import dev.nuris.footballleague.model.response.Events
import dev.nuris.footballleague.model.response.Leagues
import dev.nuris.footballleague.source.LeagueDetailDataSource

class LeagueDetailRepositoryImpl (private val leagueDetailDataSource: LeagueDetailDataSource) : LeagueDetailRepository {

    init {
        leagueDetailDataSource.getLeaguesResponse.observeForever {
            try {
                _getLeaguesResponse.postValue(it)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }

        leagueDetailDataSource.getPastEventResponse.observeForever {
            try {
                _getPastEventResponse.postValue(it)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }

        leagueDetailDataSource.getNextEventResponse.observeForever {
            try {
                _getNextEventResponse.postValue(it)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    private val _getLeaguesResponse = SingleLiveEvent<DataWrapper<Leagues>>()
    override val getLeaguesResponse: LiveData<DataWrapper<Leagues>>
        get() = _getLeaguesResponse

    private val _getPastEventResponse = SingleLiveEvent<DataWrapper<Events>>()
    override val getPastEventResponse: LiveData<DataWrapper<Events>>
        get() = _getPastEventResponse

    private val _getNextEventResponse = SingleLiveEvent<DataWrapper<Events>>()
    override val getNextEventResponse: LiveData<DataWrapper<Events>>
        get() = _getNextEventResponse


    override fun getLeagueDetail(id: String) {
        leagueDetailDataSource.getLeagueDetail(id)
    }

    override fun getPastEvent(id: String) {
        leagueDetailDataSource.getPastEvent(id)
    }

    override fun getNextEvent(id: String) {
        leagueDetailDataSource.getNextEvent(id)
    }

}