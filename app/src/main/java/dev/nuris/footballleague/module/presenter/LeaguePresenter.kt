package dev.nuris.footballleague.module.presenter

import com.google.gson.Gson
import dev.nuris.footballleague.api.ApiRepository
import dev.nuris.footballleague.api.TheSportDBApi
import dev.nuris.footballleague.helper.CoroutineContextProvider
import dev.nuris.footballleague.model.LeagueResponse
import dev.nuris.footballleague.module.view.LeagueView
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class LeaguePresenter(private val view: LeagueView,
                      private val apiRepository: ApiRepository,
                      private val gson: Gson,
                      private val context: CoroutineContextProvider = CoroutineContextProvider()) {

    fun getLeagueList() {
        view.showLoading()
        GlobalScope.launch(context.main) {
            val data = gson.fromJson(apiRepository.doRequestAsync(TheSportDBApi.getAllLeague()).await(),
                LeagueResponse::class.java)
            view.showListLeague(data)
            view.hideLoading()
        }
    }
}