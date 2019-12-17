package dev.nuris.footballleague.model

import com.google.gson.annotations.SerializedName

data class EventResponse(
    @SerializedName("events")
    val events: List<Event>? = null,
    @SerializedName("event")
    val event: List<Event>? = null
)