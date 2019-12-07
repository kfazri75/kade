package dev.nuris.footballleague.api

import dev.nuris.footballleague.http.ApiServices
import kotlinx.coroutines.GlobalScope
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify
import kotlinx.coroutines.launch

class ApiServicesTest {

    companion object{
        const val ID_LEAGUE = "4328"
        const val ID_MATCH = "441613"
        const val QUERY_SEARCH = "united"
    }

    @Test
    fun testRequestLeague() {
        val apiServices = mock(ApiServices::class.java)
        GlobalScope.launch {
            apiServices.getAllLeagueAsync().await().body()?.leagues
            verify(apiServices.getAllLeagueAsync().await().body()?.leagues)
        }
    }

    @Test
    fun testRequestDetailLeague() {
        val apiServices = mock(ApiServices::class.java)
        GlobalScope.launch {
            apiServices.getDetailLeagueAsync(ID_LEAGUE).await().body()?.leagues
            verify(apiServices.getDetailLeagueAsync(ID_LEAGUE).await().body()?.leagues)
        }
    }

    @Test
    fun testRequestPastMatch() {
        val apiServices = mock(ApiServices::class.java)
        GlobalScope.launch {
            apiServices.getPastEventAsync(ID_LEAGUE).await().body()?.events
            verify(apiServices.getPastEventAsync(ID_LEAGUE).await().body()?.events)
        }
    }

    @Test
    fun testRequestNextMatch() {
        val apiServices = mock(ApiServices::class.java)
        GlobalScope.launch {
            apiServices.getNextEventAsync(ID_LEAGUE).await().body()?.events
            verify(apiServices.getNextEventAsync(ID_LEAGUE).await().body()?.events)
        }
    }

    @Test
    fun testRequestDetailsMatch() {
        val apiServices = mock(ApiServices::class.java)
        GlobalScope.launch {
            apiServices.getLookUpEventAsync(ID_MATCH).await().body()?.events
            verify(apiServices.getLookUpEventAsync(ID_MATCH).await().body()?.events)
        }
    }

    @Test
    fun testRequestSearchMatch() {
        val apiServices = mock(ApiServices::class.java)
        GlobalScope.launch {
            apiServices.getSearchEventAsync(QUERY_SEARCH).await().body()?.events
            verify(apiServices.getSearchEventAsync(QUERY_SEARCH).await().body()?.events)
        }
    }
}