package dev.nuris.footballleague.module.presenter

import com.google.gson.Gson
import dev.nuris.footballleague.api.ApiRepository
import dev.nuris.footballleague.api.TheSportDBApi
import dev.nuris.footballleague.helper.CoroutineContextProvider
import dev.nuris.footballleague.model.EventResponse
import dev.nuris.footballleague.module.view.PastMatchView
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class PastMatchPresenter(private val view: PastMatchView,
                         private val apiRepository: ApiRepository,
                         private val gson: Gson,
                         private val context: CoroutineContextProvider = CoroutineContextProvider()) {

    fun getPastMatchList(leagueId: String) {
        view.showLoading()
        GlobalScope.launch(context.main) {
            val data = gson.fromJson(apiRepository.doRequestAsync(TheSportDBApi.getPastEvent(leagueId)).await(),
                EventResponse::class.java)
            view.showPastMatchList(data)
            view.hideLoading()
        }
    }
}