package dev.nuris.footballleague.repository

import androidx.lifecycle.LiveData
import dev.nuris.footballleague.helper.lib.SingleLiveEvent
import dev.nuris.footballleague.http.DataWrapper
import dev.nuris.footballleague.model.response.Events
import dev.nuris.footballleague.source.SearchMatchDataSource

class SearchMatchRepositoryImpl(private val searchMatchDataSource: SearchMatchDataSource) :
    SearchMatchRepository {

    init {
        searchMatchDataSource.getEventResponse.observeForever {
            try {
                _getEventResponse.postValue(it)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    private val _getEventResponse = SingleLiveEvent<DataWrapper<Events>>()
    override val getEventResponse: LiveData<DataWrapper<Events>>
        get() = _getEventResponse


    override fun getSearchEvent(query: String) {
        searchMatchDataSource.getSearchEvent(query)
    }
}