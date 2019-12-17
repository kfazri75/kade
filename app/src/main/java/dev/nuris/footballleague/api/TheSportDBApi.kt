package dev.nuris.footballleague.api

import dev.nuris.footballleague.helper.Constant

object TheSportDBApi {

    private const val API_URL = Constant.API_URL

    fun getAllLeague(): String {
        return Constant.API_URL + "search_all_leagues.php?s=Soccer"
    }

    fun getDetailLeague(leagueId: String) : String{
        return API_URL + "lookupleague.php?id=" + leagueId
    }

    fun getNextEvent(leagueId: String) : String {
        return API_URL + "eventsnextleague.php?id=" + leagueId
    }

    fun getPastEvent(leagueId: String) : String {
        return API_URL + "eventspastleague.php?id=" + leagueId
    }

    fun getDetailEvent(eventId: String): String {
        return API_URL + "lookupevent.php?id=" + eventId
    }

    fun getSearchEvent(query: String): String {
        return API_URL + "searchevents.php?e=" + query
    }
}