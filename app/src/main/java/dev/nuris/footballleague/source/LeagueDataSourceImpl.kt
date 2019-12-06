package dev.nuris.footballleague.source

import androidx.lifecycle.LiveData
import dev.nuris.footballleague.helper.lib.SingleLiveEvent
import dev.nuris.footballleague.http.ApiServices
import dev.nuris.footballleague.http.DataWrapper
import dev.nuris.footballleague.model.response.Leagues
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class LeagueDataSourceImpl (private val apiServices: ApiServices) :
    LeagueDataSource {

    private val _leagues = SingleLiveEvent<DataWrapper<Leagues>>()
    override val leagues: LiveData<DataWrapper<Leagues>>
        get() = _leagues

    override fun getLeague() {
        GlobalScope.launch {
            val leaguesRequests = apiServices.getAllLeagueAsync()
            try {
                val response = leaguesRequests.await()
                _leagues.postValue(DataWrapper(response.body()))
            } catch (e: Exception){
                e.printStackTrace()
            }
        }
    }

}