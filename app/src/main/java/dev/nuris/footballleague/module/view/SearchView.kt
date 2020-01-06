package dev.nuris.footballleague.module.view

import dev.nuris.footballleague.model.EventResponse
import dev.nuris.footballleague.model.TeamResponse

interface SearchView {
    fun showLoading()
    fun hideLoading()
    fun showMatchList(eventResponse: EventResponse)
    fun showTeamList(teamResponse: TeamResponse)
}