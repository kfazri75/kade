package dev.nuris.footballleague.source

import androidx.lifecycle.LiveData
import dev.nuris.footballleague.helper.lib.SingleLiveEvent
import dev.nuris.footballleague.http.ApiServices
import dev.nuris.footballleague.http.DataWrapper
import dev.nuris.footballleague.model.response.Events
import dev.nuris.footballleague.model.response.Leagues
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class LeagueDetailDataSourceImpl (private val apiServices: ApiServices) : LeagueDetailDataSource {

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
        GlobalScope.launch {
            val leagueDetailReq = apiServices.getDetailLeagueAsync(id)
            try {
                val response = leagueDetailReq.await()
                _getLeaguesResponse.postValue(DataWrapper(response.body()))
            } catch (e: Exception){
                e.printStackTrace()
            }
        }
    }

    override fun getPastEvent(id: String) {
        GlobalScope.launch {
            val requests = apiServices.getPastEventAsync(id)
            try {
                val response = requests.await()
                _getPastEventResponse.postValue(DataWrapper(response.body()))
            } catch (e: Exception){
                e.printStackTrace()
            }
        }
    }

    override fun getNextEvent(id: String) {
        GlobalScope.launch {
            val requests = apiServices.getNextEventAsync(id)
            try {
                val response = requests.await()
                _getNextEventResponse.postValue(DataWrapper(response.body()))
            } catch (e: Exception){
                e.printStackTrace()
            }
        }
    }

}