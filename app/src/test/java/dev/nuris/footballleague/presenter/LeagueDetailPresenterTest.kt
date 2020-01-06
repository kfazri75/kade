package dev.nuris.footballleague.presenter

import com.google.gson.Gson
import dev.nuris.footballleague.TestContextProvider
import dev.nuris.footballleague.api.ApiRepository
import dev.nuris.footballleague.model.League
import dev.nuris.footballleague.model.LeagueResponse
import dev.nuris.footballleague.module.presenter.LeagueDetailPresenter
import dev.nuris.footballleague.module.view.LeagueDetailView
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.mockito.ArgumentMatchers
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

class LeagueDetailPresenterTest {

    @Mock
    private lateinit var leagueDetailView: LeagueDetailView

    @Mock
    private lateinit var gson: Gson

    @Mock
    private lateinit var apiRepository: ApiRepository

    @Mock
    private lateinit var apiResponse: Deferred<String>

    private lateinit var leagueDetailPresenter: LeagueDetailPresenter

    companion object{
        const val LEAGUE_ID = "4328"
    }

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        leagueDetailPresenter = LeagueDetailPresenter(leagueDetailView, apiRepository, gson, TestContextProvider())
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

            leagueDetailPresenter.getLeagueDetail(LEAGUE_ID)
            Mockito.verify(leagueDetailView).showLoading()
            Mockito.verify(leagueDetailView).showLeagueDetail(response)
            Mockito.verify(leagueDetailView).hideLoading()
        }
    }
}