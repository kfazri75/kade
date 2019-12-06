package dev.nuris.footballleague.repository

import androidx.lifecycle.LiveData
import dev.nuris.footballleague.helper.lib.SingleLiveEvent
import dev.nuris.footballleague.http.DataWrapper
import dev.nuris.footballleague.model.response.Events
import dev.nuris.footballleague.source.EventDetailsDataSource

class EventDetailsRepositoryImpl(private val eventDetailsDataSource: EventDetailsDataSource) :
    EventDetailsRepository {

    init {
        eventDetailsDataSource.getEventResponse.observeForever {
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


    override fun getEventDetail(id: String) {
        eventDetailsDataSource.getEventDetails(id)
    }
}