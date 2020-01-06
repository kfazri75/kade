package dev.nuris.footballleague.module.presenter

import com.google.gson.Gson
import dev.nuris.footballleague.api.ApiRepository
import dev.nuris.footballleague.api.TheSportDBApi
import dev.nuris.footballleague.helper.CoroutineContextProvider
import dev.nuris.footballleague.model.StandingResponse
import dev.nuris.footballleague.module.view.StandingView
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class StandingPresenter(private val view: StandingView,
                        private val apiRepository: ApiRepository,
                        private val gson: Gson,
                        private val context: CoroutineContextProvider = CoroutineContextProvider()) {

    fun getLeagueList(leagueId: String) {
        view.showLoading()
        GlobalScope.launch(context.main) {
            val data = gson.fromJson(apiRepository.doRequestAsync(TheSportDBApi.getStanding(leagueId)).await(),
                StandingResponse::class.java)
            view.showStandingList(data)
            view.hideLoading()
        }
    }
}