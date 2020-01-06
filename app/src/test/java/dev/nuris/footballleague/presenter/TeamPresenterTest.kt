package dev.nuris.footballleague.presenter

import com.google.gson.Gson
import dev.nuris.footballleague.TestContextProvider
import dev.nuris.footballleague.api.ApiRepository
import dev.nuris.footballleague.model.Team
import dev.nuris.footballleague.model.TeamResponse
import dev.nuris.footballleague.module.presenter.TeamPresenter
import dev.nuris.footballleague.module.view.TeamView
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.mockito.ArgumentMatchers
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

class TeamPresenterTest {

    @Mock
    private lateinit var teamView: TeamView

    @Mock
    private lateinit var gson: Gson

    @Mock
    private lateinit var apiRepository: ApiRepository

    @Mock
    private lateinit var apiResponse: Deferred<String>

    private lateinit var teamPresenter: TeamPresenter

    companion object{
        const val LEAGUE_ID = ""
    }

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        teamPresenter = TeamPresenter(teamView, apiRepository, gson, TestContextProvider())
    }

    @Test
    fun testGetTeamList() {
        val teams: MutableList<Team> = mutableListOf()
        val response = TeamResponse(teams)

        runBlocking {
            Mockito.`when`(apiRepository.doRequestAsync(ArgumentMatchers.anyString()))
                .thenReturn(apiResponse)
            Mockito.`when`(apiResponse.await()).thenReturn("")
            Mockito.`when`(
                gson.fromJson(
                    "",
                    TeamResponse::class.java
                )
            ).thenReturn(response)

            teamPresenter.getTeamList(LEAGUE_ID)
            Mockito.verify(teamView).showLoading()
            Mockito.verify(teamView).showTeamList(response)
            Mockito.verify(teamView).hideLoading()
        }
    }
}