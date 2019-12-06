package dev.nuris.footballleague.ui.viewmodel

import androidx.lifecycle.ViewModel
import dev.nuris.footballleague.repository.SearchMatchRepository

class SearchMatchViewModel(private val searchMatchRepository: SearchMatchRepository) : ViewModel() {

    val searchMatchResponse by lazy {
        searchMatchRepository.getEventResponse
    }

    fun getSearchMatch(query: String) {
        searchMatchRepository.getSearchEvent(query)
    }
}