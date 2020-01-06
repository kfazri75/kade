package dev.nuris.footballleague.model

import com.google.gson.annotations.SerializedName

data class StandingResponse(
    @SerializedName("table")
    val table: List<Table>?
)