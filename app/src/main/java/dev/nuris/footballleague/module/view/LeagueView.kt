package dev.nuris.footballleague.module.view

import dev.nuris.footballleague.model.LeagueResponse

interface LeagueView {
    fun showLoading()
    fun hideLoading()
    fun showListLeague(leagueResponse: LeagueResponse)
}