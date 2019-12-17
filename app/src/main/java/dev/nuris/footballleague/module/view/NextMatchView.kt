package dev.nuris.footballleague.module.view

import dev.nuris.footballleague.model.EventResponse

interface NextMatchView {
    fun showLoading()
    fun hideLoading()
    fun showNextMatchList(eventResponse: EventResponse)
}