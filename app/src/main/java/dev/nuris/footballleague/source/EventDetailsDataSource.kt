package dev.nuris.footballleague.source

import androidx.lifecycle.LiveData
import dev.nuris.footballleague.http.DataWrapper
import dev.nuris.footballleague.model.response.Events

interface EventDetailsDataSource {
    val getEventResponse: LiveData<DataWrapper<Events>>

    fun getEventDetails(id: String)
}