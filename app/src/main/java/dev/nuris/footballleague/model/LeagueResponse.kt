package dev.nuris.footballleague.model

import com.google.gson.annotations.SerializedName

data class LeagueResponse(
    @SerializedName("countrys")
    val leagues: List<League>? = null,
    @SerializedName("leagues")
    val leagueDetail: List<League>? = null
)