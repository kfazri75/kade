package dev.nuris.footballleague.repository

import androidx.lifecycle.LiveData
import dev.nuris.footballleague.http.DataWrapper
import dev.nuris.footballleague.model.response.Events

interface EventDetailsRepository {
    val getEventResponse: LiveData<DataWrapper<Events>>

    fun getEventDetail(id: String)
}