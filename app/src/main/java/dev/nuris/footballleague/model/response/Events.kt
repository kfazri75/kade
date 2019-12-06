package dev.nuris.footballleague.model.response

import com.google.gson.annotations.SerializedName
import dev.nuris.footballleague.model.Event

data class Events(
    @SerializedName("events")
    val events: List<Event>? = null,
    @SerializedName("event")
    val event: List<Event>? = null
)