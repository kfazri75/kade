package dev.nuris.footballleague.presenter

import com.google.gson.Gson
import dev.nuris.footballleague.TestContextProvider
import dev.nuris.footballleague.api.ApiRepository
import dev.nuris.footballleague.model.Team
import dev.nuris.footballleague.model.TeamResponse
import dev.nuris.footballleague.module.presenter.TeamDetailPresenter
import dev.nuris.footballleague.module.view.TeamDetailView
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.mockito.ArgumentMatchers
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

class TeamDetailPresenterTest {

    @Mock
    private lateinit var teamDetailView: TeamDetailView

    @Mock
    private lateinit var gson: Gson

    @Mock
    private lateinit var apiRepository: ApiRepository

    @Mock
    private lateinit var apiResponse: Deferred<String>

    private lateinit var teamDetailPresenter: TeamDetailPresenter

    companion object{
        const val ID_TEAM = "133604"
    }

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        teamDetailPresenter = TeamDetailPresenter(teamDetailView, apiRepository, gson, TestContextProvider())
    }

    @Test
    fun testGetTeamDetail() {
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

            teamDetailPresenter.getTeamList(ID_TEAM)
            Mockito.verify(teamDetailView).showLoading()
            Mockito.verify(teamDetailView).showTeamDetail(response)
            Mockito.verify(teamDetailView).hideLoading()
        }
    }
}