@file:Suppress("DeferredResultUnused")

package dev.nuris.footballleague.api

import org.junit.Test
import org.mockito.Mockito

class ApiRepositoryTest {

    @Test
    fun testDoRequest() {
        val apiRepository = Mockito.mock(ApiRepository::class.java)
        val url = "https://www.thesportsdb.com/api/v1/json/1/search_all_leagues.php?s=Soccer"
        apiRepository.doRequestAsync(url)
        Mockito.verify(apiRepository).doRequestAsync(url)
    }
}