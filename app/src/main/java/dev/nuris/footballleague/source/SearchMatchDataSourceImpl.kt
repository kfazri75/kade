package dev.nuris.footballleague.source

import androidx.lifecycle.LiveData
import dev.nuris.footballleague.helper.lib.SingleLiveEvent
import dev.nuris.footballleague.http.ApiServices
import dev.nuris.footballleague.http.DataWrapper
import dev.nuris.footballleague.model.response.Events
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class SearchMatchDataSourceImpl (private val apiServices: ApiServices) : SearchMatchDataSource {

    private val _getEventResponse = SingleLiveEvent<DataWrapper<Events>>()
    override val getEventResponse: LiveData<DataWrapper<Events>>
        get() = _getEventResponse

    override fun getSearchEvent(query: String) {
        GlobalScope.launch {
            val searchEventRequests = apiServices.getSearchEventAsync(query)
            try {
                val response = searchEventRequests.await()
                _getEventResponse.postValue(DataWrapper(response.body()))
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}