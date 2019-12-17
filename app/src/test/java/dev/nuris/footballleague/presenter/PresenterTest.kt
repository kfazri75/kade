package dev.nuris.footballleague.presenter

import com.google.gson.Gson
import dev.nuris.footballleague.TestContextProvider
import dev.nuris.footballleague.api.ApiRepository
import dev.nuris.footballleague.model.Event
import dev.nuris.footballleague.model.EventResponse
import dev.nuris.footballleague.model.League
import dev.nuris.footballleague.model.LeagueResponse
import dev.nuris.footballleague.module.presenter.*
import dev.nuris.footballleague.module.view.*
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.mockito.ArgumentMatchers
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

class PresenterTest {

    @Mock
    private lateinit var leagueView: LeagueView
    @Mock
    private lateinit var leagueDetailView: LeagueDetailView
    @Mock
    private lateinit var pastMatchView: PastMatchView
    @Mock
    private lateinit var nextMatchView: NextMatchView
    @Mock
    private lateinit var detailMatchView: DetailMatchView
    @Mock
    private lateinit var searchMatchView: SearchMatchView


    @Mock
    private lateinit var gson: Gson

    @Mock
    private lateinit var apiRepository: ApiRepository

    @Mock
    private lateinit var apiResponse: Deferred<String>

    private lateinit var leaguePresenter: LeaguePresenter
    private lateinit var leagueDetailPresenter: LeagueDetailPresenter
    private lateinit var pastMatchPresenter: PastMatchPresenter
    private lateinit var nextMatchPresenter: NextMatchPresenter
    private lateinit var detailMatchPresenter: DetailMatchPresenter
    private lateinit var searchMatchPresenter: SearchMatchPresenter

    companion object{
        const val ID_LEAGUE = "4328"
        const val ID_MATCH = "441613"
        const val QUERY_SEARCH = "united"
    }

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        leaguePresenter = LeaguePresenter(leagueView, apiRepository, gson, TestContextProvider())
        leagueDetailPresenter = LeagueDetailPresenter(leagueDetailView, apiRepository, gson, TestContextProvider())
        pastMatchPresenter = PastMatchPresenter(pastMatchView, apiRepository, gson, TestContextProvider())
        nextMatchPresenter = NextMatchPresenter(nextMatchView, apiRepository, gson, TestContextProvider())
        detailMatchPresenter = DetailMatchPresenter(detailMatchView, apiRepository, gson, TestContextProvider())
        searchMatchPresenter = SearchMatchPresenter(searchMatchView, apiRepository, gson, TestContextProvider())
    }

    @Test
    fun testGetLeagueList() {
        val leagues: MutableList<League> = mutableListOf()
        val response = LeagueResponse(leagues)

        runBlocking {
            Mockito.`when`(apiRepository.doRequestAsync(ArgumentMatchers.anyString()))
                .thenReturn(apiResponse)
            Mockito.`when`(apiResponse.await()).thenReturn("")
            Mockito.`when`(
                gson.fromJson(
                    "",
                    LeagueResponse::class.java
                )
            ).thenReturn(response)

            leaguePresenter.getLeagueList()
            Mockito.verify(leagueView).showLoading()
            Mockito.verify(leagueView).showListLeague(response)
            Mockito.verify(leagueView).hideLoading()
        }
    }

    @Test
    fun testGetLeagueDetails() {
        val leagues: MutableList<League> = mutableListOf()
        val response = LeagueResponse(leagues)

        runBlocking {
            Mockito.`when`(apiRepository.doRequestAsync(ArgumentMatchers.anyString()))
                .thenReturn(apiResponse)
            Mockito.`when`(apiResponse.await()).thenReturn("")
            Mockito.`when`(
                gson.fromJson(
                    "",
                    LeagueResponse::class.java
                )
            ).thenReturn(response)

            leagueDetailPresenter.getLeagueDetail(ID_LEAGUE)
            Mockito.verify(leagueDetailView).showLoading()
            Mockito.verify(leagueDetailView).showLeagueDetail(response)
            Mockito.verify(leagueDetailView).hideLoading()
        }
    }

    @Test
    fun testGetPastMatchList() {
        val events: MutableList<Event> = mutableListOf()
        val response = EventResponse(events)

        runBlocking {
            Mockito.`when`(apiRepository.doRequestAsync(ArgumentMatchers.anyString()))
                .thenReturn(apiResponse)
            Mockito.`when`(apiResponse.await()).thenReturn("")
            Mockito.`when`(
                gson.fromJson(
                    "",
                    EventResponse::class.java
                )
            ).thenReturn(response)

            pastMatchPresenter.getPastMatchList(ID_LEAGUE)
            Mockito.verify(pastMatchView).showLoading()
            Mockito.verify(pastMatchView).showPastMatchList(response)
            Mockito.verify(pastMatchView).hideLoading()
        }
    }

    @Test
    fun testGetNextMatchList() {
        val events: MutableList<Event> = mutableListOf()
        val response = EventResponse(events)

        runBlocking {
            Mockito.`when`(apiRepository.doRequestAsync(ArgumentMatchers.anyString()))
                .thenReturn(apiResponse)
            Mockito.`when`(apiResponse.await()).thenReturn("")
            Mockito.`when`(
                gson.fromJson(
                    "",
                    EventResponse::class.java
                )
            ).thenReturn(response)

            nextMatchPresenter.getNextMatchList(ID_LEAGUE)
            Mockito.verify(nextMatchView).showLoading()
            Mockito.verify(nextMatchView).showNextMatchList(response)
            Mockito.verify(nextMatchView).hideLoading()
        }
    }

    @Test
    fun testGetDetailMatch() {
        val events: MutableList<Event> = mutableListOf()
        val response = EventResponse(events)

        runBlocking {
            Mockito.`when`(apiRepository.doRequestAsync(ArgumentMatchers.anyString()))
                .thenReturn(apiResponse)
            Mockito.`when`(apiResponse.await()).thenReturn("")
            Mockito.`when`(
                gson.fromJson(
                    "",
                    EventResponse::class.java
                )
            ).thenReturn(response)

            detailMatchPresenter.getDetailMatch(ID_MATCH)
            Mockito.verify(detailMatchView).showLoading()
            Mockito.verify(detailMatchView).showDetailMatch(response)
            Mockito.verify(detailMatchView).hideLoading()
        }
    }

    @Test
    fun testGetSearchMatchList() {
        val events: MutableList<Event> = mutableListOf()
        val response = EventResponse(events)

        runBlocking {
            Mockito.`when`(apiRepository.doRequestAsync(ArgumentMatchers.anyString()))
                .thenReturn(apiResponse)
            Mockito.`when`(apiResponse.await()).thenReturn("")
            Mockito.`when`(
                gson.fromJson(
                    "",
                    EventResponse::class.java
                )
            ).thenReturn(response)

            searchMatchPresenter.getMatchList(QUERY_SEARCH)
            Mockito.verify(searchMatchView).showLoading()
            Mockito.verify(searchMatchView).showMatchList(response)
            Mockito.verify(searchMatchView).hideLoading()
        }
    }
}