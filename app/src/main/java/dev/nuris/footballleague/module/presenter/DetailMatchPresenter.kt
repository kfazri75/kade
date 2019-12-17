package dev.nuris.footballleague.module.presenter

import com.google.gson.Gson
import dev.nuris.footballleague.api.ApiRepository
import dev.nuris.footballleague.api.TheSportDBApi
import dev.nuris.footballleague.helper.CoroutineContextProvider
import dev.nuris.footballleague.model.EventResponse
import dev.nuris.footballleague.module.view.DetailMatchView
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class DetailMatchPresenter(private val view: DetailMatchView,
                           private val apiRepository: ApiRepository,
                           private val gson: Gson,
                           private val context: CoroutineContextProvider = CoroutineContextProvider()) {

    fun getDetailMatch(eventId: String) {
        view.showLoading()
        GlobalScope.launch(context.main) {
            val data = gson.fromJson(apiRepository.doRequestAsync(TheSportDBApi.getDetailEvent(eventId)).await(),
                EventResponse::class.java)
            view.showDetailMatch(data)
            view.hideLoading()
        }
    }
}