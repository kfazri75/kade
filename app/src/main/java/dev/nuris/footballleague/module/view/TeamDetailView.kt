package dev.nuris.footballleague.module.view

import dev.nuris.footballleague.model.TeamResponse

interface TeamDetailView {
    fun showLoading()
    fun hideLoading()
    fun showTeamDetail(teamResponse: TeamResponse)
}