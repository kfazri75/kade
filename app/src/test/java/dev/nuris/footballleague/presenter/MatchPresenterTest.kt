package dev.nuris.footballleague.presenter

import com.google.gson.Gson
import dev.nuris.footballleague.TestContextProvider
import dev.nuris.footballleague.api.ApiRepository
import dev.nuris.footballleague.model.Event
import dev.nuris.footballleague.model.EventResponse
import dev.nuris.footballleague.module.presenter.MatchPresenter
import dev.nuris.footballleague.module.view.MatchView
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.mockito.ArgumentMatchers
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

class MatchPresenterTest {

    @Mock
    private lateinit var matchView: MatchView

    @Mock
    private lateinit var gson: Gson

    @Mock
    private lateinit var apiRepository: ApiRepository

    @Mock
    private lateinit var apiResponse: Deferred<String>

    private lateinit var matchPresenter: MatchPresenter

    companion object{
        const val LEAGUE_ID = "4328"
    }

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        matchPresenter = MatchPresenter(matchView, apiRepository, gson, TestContextProvider())
    }

    @Test
    fun testLastMatchList() {
        val event: MutableList<Event> = mutableListOf()
        val response = EventResponse(event)

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

            matchPresenter.getLastMatchList(LEAGUE_ID)
            Mockito.verify(matchView).showLoading()
            Mockito.verify(matchView).showLastMatchList(response)
            Mockito.verify(matchView).hideLoading()
        }
    }

    @Test
    fun testNextMatchList() {
        val event: MutableList<Event> = mutableListOf()
        val response = EventResponse(event)

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

            matchPresenter.getNextMatchList(LEAGUE_ID)
            Mockito.verify(matchView).showLoading()
            Mockito.verify(matchView).showNextMatchList(response)
            Mockito.verify(matchView).hideLoading()
        }
    }
}