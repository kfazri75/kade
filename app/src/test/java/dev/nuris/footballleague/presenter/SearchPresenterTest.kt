package dev.nuris.footballleague.presenter

import com.google.gson.Gson
import dev.nuris.footballleague.TestContextProvider
import dev.nuris.footballleague.api.ApiRepository
import dev.nuris.footballleague.model.Event
import dev.nuris.footballleague.model.EventResponse
import dev.nuris.footballleague.model.Team
import dev.nuris.footballleague.model.TeamResponse
import dev.nuris.footballleague.module.presenter.SearchPresenter
import dev.nuris.footballleague.module.view.SearchView
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.mockito.ArgumentMatchers
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

class SearchPresenterTest {

    @Mock
    private lateinit var searchView: SearchView

    @Mock
    private lateinit var gson: Gson

    @Mock
    private lateinit var apiRepository: ApiRepository

    @Mock
    private lateinit var apiResponse: Deferred<String>

    private lateinit var searchPresenter: SearchPresenter

    companion object{
        const val SEARCH_QUERY_MATCH = "united"
        const val SEARCH_QUERY_TEAM = "liverpool"
    }

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        searchPresenter = SearchPresenter(searchView, apiRepository, gson, TestContextProvider())
    }

    @Test
    fun testGetResultEvent() {
        val events: MutableList<Event> = mutableListOf()
        val eventResponse = EventResponse(events)

        runBlocking {
            Mockito.`when`(apiRepository.doRequestAsync(ArgumentMatchers.anyString()))
                .thenReturn(apiResponse)
            Mockito.`when`(apiResponse.await()).thenReturn("")
            Mockito.`when`(
                gson.fromJson(
                    "",
                    EventResponse::class.java
                )
            ).thenReturn(eventResponse)

            searchPresenter.getMatchList(SEARCH_QUERY_MATCH)
            Mockito.verify(searchView).showLoading()
            Mockito.verify(searchView).showMatchList(eventResponse)
            Mockito.verify(searchView).hideLoading()
        }
    }

    @Test
    fun testGetResultTeam() {

        val teams: MutableList<Team> = mutableListOf()
        val teamResponse = TeamResponse(teams)

        runBlocking {
            Mockito.`when`(apiRepository.doRequestAsync(ArgumentMatchers.anyString()))
                .thenReturn(apiResponse)
            Mockito.`when`(apiResponse.await()).thenReturn("")

            Mockito.`when`(
                gson.fromJson(
                    "",
                    TeamResponse::class.java
                )
            ).thenReturn(teamResponse)

            searchPresenter.getTeamList(SEARCH_QUERY_TEAM)
            Mockito.verify(searchView).showLoading()
            Mockito.verify(searchView).showTeamList(teamResponse)
            Mockito.verify(searchView).hideLoading()
        }
    }
}