package dev.nuris.footballleague.module.presenter

import com.google.gson.Gson
import dev.nuris.footballleague.api.ApiRepository
import dev.nuris.footballleague.api.TheSportDBApi
import dev.nuris.footballleague.helper.CoroutineContextProvider
import dev.nuris.footballleague.model.EventResponse
import dev.nuris.footballleague.module.view.MatchView
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class MatchPresenter(private val view: MatchView,
                     private val apiRepository: ApiRepository,
                     private val gson: Gson,
                     private val context: CoroutineContextProvider = CoroutineContextProvider()) {

    fun getNextMatchList(leagueId: String) {
        view.showLoading()
        GlobalScope.launch(context.main) {
            val data = gson.fromJson(apiRepository.doRequestAsync(TheSportDBApi.getNextEvent(leagueId)).await(),
                EventResponse::class.java)
            view.showNextMatchList(data)
            view.hideLoading()
        }
    }

    fun getLastMatchList(leagueId: String) {
        view.showLoading()
        GlobalScope.launch(context.main) {
            val data = gson.fromJson(apiRepository.doRequestAsync(TheSportDBApi.getLastEvent(leagueId)).await(),
                EventResponse::class.java)
            view.showLastMatchList(data)
            view.hideLoading()
        }
    }
}