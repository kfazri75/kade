package dev.nuris.footballleague.module.presenter

import com.google.gson.Gson
import dev.nuris.footballleague.api.ApiRepository
import dev.nuris.footballleague.api.TheSportDBApi
import dev.nuris.footballleague.helper.CoroutineContextProvider
import dev.nuris.footballleague.model.LeagueResponse
import dev.nuris.footballleague.module.view.LeagueDetailView
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class LeagueDetailPresenter(private val view: LeagueDetailView,
                            private val apiRepository: ApiRepository,
                            private val gson: Gson,
                            private val context: CoroutineContextProvider = CoroutineContextProvider()) {

    fun getLeagueDetail(leagueId: String) {
        view.showLoading()
        GlobalScope.launch(context.main) {
            val data = gson.fromJson(apiRepository.doRequestAsync(TheSportDBApi.getDetailLeague(leagueId)).await(),
                LeagueResponse::class.java)
            view.showLeagueDetail(data)
            view.hideLoading()
        }
    }
}