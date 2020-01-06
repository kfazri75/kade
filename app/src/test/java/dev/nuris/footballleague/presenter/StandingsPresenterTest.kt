package dev.nuris.footballleague.presenter

import com.google.gson.Gson
import dev.nuris.footballleague.TestContextProvider
import dev.nuris.footballleague.api.ApiRepository
import dev.nuris.footballleague.model.StandingResponse
import dev.nuris.footballleague.model.Table
import dev.nuris.footballleague.module.presenter.StandingPresenter
import dev.nuris.footballleague.module.view.StandingView
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.mockito.ArgumentMatchers
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

class StandingsPresenterTest {

    @Mock
    private lateinit var standingView: StandingView

    @Mock
    private lateinit var gson: Gson

    @Mock
    private lateinit var apiRepository: ApiRepository

    @Mock
    private lateinit var apiResponse: Deferred<String>

    private lateinit var standingPresenter: StandingPresenter

    companion object{
        const val LEAGUE_ID = "4328"
    }

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        standingPresenter = StandingPresenter(standingView, apiRepository, gson, TestContextProvider())
    }

    @Test
    fun testGetStandings() {
        val table: MutableList<Table> = mutableListOf()
        val response = StandingResponse(table)

        runBlocking {
            Mockito.`when`(apiRepository.doRequestAsync(ArgumentMatchers.anyString()))
                .thenReturn(apiResponse)
            Mockito.`when`(apiResponse.await()).thenReturn("")
            Mockito.`when`(
                gson.fromJson(
                    "",
                    StandingResponse::class.java
                )
            ).thenReturn(response)

            standingPresenter.getLeagueList(LEAGUE_ID)
            Mockito.verify(standingView).showLoading()
            Mockito.verify(standingView).showStandingList(response)
            Mockito.verify(standingView).hideLoading()
        }
    }
}