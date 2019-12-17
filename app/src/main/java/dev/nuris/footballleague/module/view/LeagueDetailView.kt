package dev.nuris.footballleague.module.view

import dev.nuris.footballleague.model.LeagueResponse

interface LeagueDetailView {
    fun showLoading()
    fun hideLoading()
    fun showLeagueDetail(leagueResponse: LeagueResponse)
}