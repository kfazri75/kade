package dev.nuris.footballleague.module.view

import dev.nuris.footballleague.model.TeamResponse

interface TeamView {
    fun showLoading()
    fun hideLoading()
    fun showTeamList(teamResponse: TeamResponse)
}