package dev.nuris.footballleague.presenter

import com.google.gson.Gson
import dev.nuris.footballleague.TestContextProvider
import dev.nuris.footballleague.api.ApiRepository
import dev.nuris.footballleague.model.Event
import dev.nuris.footballleague.model.EventResponse
import dev.nuris.footballleague.module.presenter.DetailMatchPresenter
import dev.nuris.footballleague.module.view.DetailMatchView
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.mockito.ArgumentMatchers
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

class DetailMatchPresenterTest {

    @Mock
    private lateinit var detailMatchView: DetailMatchView

    @Mock
    private lateinit var gson: Gson

    @Mock
    private lateinit var apiRepository: ApiRepository

    @Mock
    private lateinit var apiResponse: Deferred<String>

    private lateinit var detailMatchPresenter: DetailMatchPresenter

    companion object{
        const val MATCH_ID = "441613"
    }

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        detailMatchPresenter = DetailMatchPresenter(detailMatchView, apiRepository, gson, TestContextProvider())
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

            detailMatchPresenter.getDetailMatch(MATCH_ID)
            Mockito.verify(detailMatchView).showLoading()
            Mockito.verify(detailMatchView).showDetailMatch(response)
            Mockito.verify(detailMatchView).hideLoading()
        }
    }
}