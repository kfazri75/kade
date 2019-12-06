package dev.nuris.footballleague.source

import androidx.lifecycle.LiveData
import dev.nuris.footballleague.http.DataWrapper
import dev.nuris.footballleague.model.response.Events

interface SearchMatchDataSource {
    val getEventResponse: LiveData<DataWrapper<Events>>

    fun getSearchEvent(query: String)
}