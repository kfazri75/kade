package dev.nuris.footballleague.presenter

import com.google.gson.Gson
import dev.nuris.footballleague.TestContextProvider
import dev.nuris.footballleague.api.ApiRepository
import dev.nuris.footballleague.model.League
import dev.nuris.footballleague.model.LeagueResponse
import dev.nuris.footballleague.module.presenter.LeaguePresenter
import dev.nuris.footballleague.module.view.LeagueView
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.mockito.ArgumentMatchers
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

class LeaguePresenterTest {

    @Mock
    private lateinit var leagueView: LeagueView

    @Mock
    private lateinit var gson: Gson

    @Mock
    private lateinit var apiRepository: ApiRepository

    @Mock
    private lateinit var apiResponse: Deferred<String>

    private lateinit var leaguePresenter: LeaguePresenter

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        leaguePresenter = LeaguePresenter(leagueView, apiRepository, gson, TestContextProvider())
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
}