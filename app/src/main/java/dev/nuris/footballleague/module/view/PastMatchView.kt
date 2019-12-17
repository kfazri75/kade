package dev.nuris.footballleague.module.view

import dev.nuris.footballleague.model.EventResponse

interface PastMatchView {
    fun showLoading()
    fun hideLoading()
    fun showPastMatchList(eventResponse: EventResponse)
}