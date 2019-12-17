package dev.nuris.footballleague.module.view

import dev.nuris.footballleague.model.EventResponse

interface DetailMatchView {
    fun showLoading()
    fun hideLoading()
    fun showDetailMatch(eventResponse: EventResponse)
}