package dev.nuris.footballleague.module.presenter

import com.google.gson.Gson
import dev.nuris.footballleague.api.ApiRepository
import dev.nuris.footballleague.api.TheSportDBApi
import dev.nuris.footballleague.helper.CoroutineContextProvider
import dev.nuris.footballleague.model.EventResponse
import dev.nuris.footballleague.module.view.SearchMatchView
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class SearchMatchPresenter(private val view: SearchMatchView,
                           private val apiRepository: ApiRepository,
                           private val gson: Gson,
                           private val context: CoroutineContextProvider = CoroutineContextProvider()) {

    fun getMatchList(query: String) {
        view.showLoading()
        GlobalScope.launch(context.main) {
            val data = gson.fromJson(apiRepository.doRequestAsync(TheSportDBApi.getSearchEvent(query)).await(),
                EventResponse::class.java)
            view.showMatchList(data)
            view.hideLoading()
        }
    }
}