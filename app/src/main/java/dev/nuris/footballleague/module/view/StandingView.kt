package dev.nuris.footballleague.module.view

import dev.nuris.footballleague.model.StandingResponse

interface StandingView {
    fun showLoading()
    fun hideLoading()
    fun showStandingList(standingResponse: StandingResponse)
}