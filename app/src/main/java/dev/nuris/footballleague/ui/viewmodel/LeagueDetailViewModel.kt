package dev.nuris.footballleague.ui.viewmodel

import androidx.lifecycle.ViewModel
import dev.nuris.footballleague.repository.LeagueDetailRepository

class LeagueDetailViewModel(private val leagueDetailRepository: LeagueDetailRepository) : ViewModel() {

    val leagueDetailResponse by lazy {
        leagueDetailRepository.getLeaguesResponse
    }

    val pastEventResponse by lazy {
        leagueDetailRepository.getPastEventResponse
    }

    val nextEventResponse by lazy {
        leagueDetailRepository.getNextEventResponse
    }

    fun getLeagueDetails(id: String) {
        leagueDetailRepository.getLeagueDetail(id)
    }

    fun getPastEvent(id: String) {
        leagueDetailRepository.getPastEvent(id)
    }

    fun getNextEvent(id: String) {
        leagueDetailRepository.getNextEvent(id)
    }
}