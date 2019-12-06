package dev.nuris.footballleague.repository

import androidx.lifecycle.LiveData
import dev.nuris.footballleague.http.DataWrapper
import dev.nuris.footballleague.model.response.Events

interface SearchMatchRepository {
    val getEventResponse: LiveData<DataWrapper<Events>>

    fun getSearchEvent(query: String)
}