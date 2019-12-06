package dev.nuris.footballleague.http

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import dev.nuris.footballleague.helper.Constant
import dev.nuris.footballleague.model.response.Events
import dev.nuris.footballleague.model.response.Leagues
import kotlinx.coroutines.Deferred
import okhttp3.OkHttpClient
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query
import java.util.concurrent.TimeUnit

interface ApiServices {

    @GET("search_all_leagues.php?s=Soccer")
    fun getAllLeagueAsync() : Deferred<Response<Leagues>>

    @GET("lookupleague.php")
    fun getDetailLeagueAsync(
        @Query("id") idLeague: String
    ) : Deferred<Response<Leagues>>

    @GET("eventsnextleague.php")
    fun getNextEventAsync(
        @Query("id") idLeague: String
    ) : Deferred<Response<Events>>

    @GET("eventspastleague.php")
    fun getPastEventAsync(
        @Query("id") idLeague: String
    ) : Deferred<Response<Events>>

    @GET("lookupevent.php")
    fun getLookUpEventAsync(
        @Query("id") idEvent: String
    ) : Deferred<Response<Events>>

    @GET("searchevents.php")
    fun getSearchEventAsync(
        @Query("e") query: String
    ) : Deferred<Response<Events>>


    companion object {
        operator fun invoke(): ApiServices {
            val okHttpClient = OkHttpClient.Builder()
                .readTimeout(20, TimeUnit.SECONDS)
                .writeTimeout(20, TimeUnit.SECONDS)
                .connectTimeout(20, TimeUnit.SECONDS)
                .build()

            return Retrofit.Builder()
                .client(okHttpClient)
                .baseUrl(Constant.API_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(CoroutineCallAdapterFactory())
                .build()
                .create(ApiServices::class.java)
        }
    }
}