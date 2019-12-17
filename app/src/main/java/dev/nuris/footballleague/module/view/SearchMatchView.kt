package dev.nuris.footballleague.module.view

import dev.nuris.footballleague.model.EventResponse

interface SearchMatchView {
    fun showLoading()
    fun hideLoading()
    fun showMatchList(eventResponse: EventResponse)
}