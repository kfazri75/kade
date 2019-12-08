package dev.nuris.footballleague.api

import com.google.gson.Gson
import dev.nuris.footballleague.http.ApiServices
import dev.nuris.footballleague.model.response.Events
import dev.nuris.footballleague.model.response.Leagues
import kotlinx.coroutines.*
import org.junit.Test
import org.junit.Assert
import java.net.URL

class ApiServicesTest {

    companion object{
        const val ID_LEAGUE = "4328"
        const val ID_MATCH = "441613"
        const val QUERY_SEARCH = "united"
    }

    private fun doRequestAsync(url: String): String = URL(url).readText()

    @Test
    fun testRequestLeague() {
        val expected = Gson().fromJson(doRequestAsync("https://www.thesportsdb.com/api/v1/json/1/search_all_leagues.php?s=Soccer"), Leagues::class.java)
        val actual = runBlocking { ApiServices().getAllLeagueAsync().await().body() }
        Assert.assertEquals(expected, actual)
    }

    @Test
    fun testRequestDetailLeague() {
        val expected = Gson().fromJson(doRequestAsync("https://www.thesportsdb.com/api/v1/json/1/lookupleague.php?id=$ID_LEAGUE"), Leagues::class.java)
        val actual = runBlocking { ApiServices().getDetailLeagueAsync(ID_LEAGUE).await().body() }
        Assert.assertEquals(expected, actual)
    }

    @Test
    fun testRequestPastMatch() {
        val expected = Gson().fromJson(doRequestAsync("https://www.thesportsdb.com/api/v1/json/1/eventspastleague.php?id=$ID_LEAGUE"), Events::class.java)
        val actual = runBlocking { ApiServices().getPastEventAsync(ID_LEAGUE).await().body() }
        Assert.assertEquals(expected, actual)
    }

    @Test
    fun testRequestNextMatch() {
        val expected = Gson().fromJson(doRequestAsync("https://www.thesportsdb.com/api/v1/json/1/eventsnextleague.php?id=$ID_LEAGUE"), Events::class.java)
        val actual = runBlocking { ApiServices().getNextEventAsync(ID_LEAGUE).await().body() }
        Assert.assertEquals(expected, actual)
    }

    @Test
    fun testRequestDetailsMatch() {
        val expected = Gson().fromJson(doRequestAsync("https://www.thesportsdb.com/api/v1/json/1/lookupevent.php?id=$ID_MATCH"), Events::class.java)
        val actual = runBlocking { ApiServices().getLookUpEventAsync(ID_MATCH).await().body() }
        Assert.assertEquals(expected, actual)
    }

    @Test
    fun testRequestSearchMatch() {
        val expected = Gson().fromJson(doRequestAsync("https://www.thesportsdb.com/api/v1/json/1/searchevents.php?e=$QUERY_SEARCH"), Events::class.java)
        val actual = runBlocking { ApiServices().getSearchEventAsync(QUERY_SEARCH).await().body() }
        Assert.assertEquals(expected, actual)
    }
}