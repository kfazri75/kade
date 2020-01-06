package dev.nuris.footballleague.module.view

import dev.nuris.footballleague.model.EventResponse

interface MatchView {
    fun showLoading()
    fun hideLoading()
    fun showNextMatchList(eventResponse: EventResponse)
    fun showLastMatchList(eventResponse: EventResponse)
}