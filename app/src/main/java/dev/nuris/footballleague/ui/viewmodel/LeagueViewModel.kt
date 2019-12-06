package dev.nuris.footballleague.ui.viewmodel

import androidx.lifecycle.ViewModel
import dev.nuris.footballleague.repository.LeagueRepository

class LeagueViewModel(private val leagueRepository: LeagueRepository) : ViewModel() {

    val leagueResponse by lazy {
        leagueRepository.leagues
    }

    fun getLeague() {
        leagueRepository.getLeague()
    }
}