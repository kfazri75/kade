package dev.nuris.footballleague.model.response

import com.google.gson.annotations.SerializedName
import dev.nuris.footballleague.model.League

data class Leagues(
    @SerializedName("countrys")
    val leagues: List<League>? = null,
    @SerializedName("leagues")
    val leagueDetail: List<League>? = null
)