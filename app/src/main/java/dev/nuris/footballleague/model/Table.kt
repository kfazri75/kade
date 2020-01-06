package dev.nuris.footballleague.model


import com.google.gson.annotations.SerializedName

data class Table(
    @SerializedName("draw")
    val draw: String?,
    @SerializedName("goalsagainst")
    val goalsagainst: String?,
    @SerializedName("goalsdifference")
    val goalsdifference: String?,
    @SerializedName("goalsfor")
    val goalsfor: String?,
    @SerializedName("loss")
    val loss: String?,
    @SerializedName("name")
    val name: String?,
    @SerializedName("played")
    val played: String?,
    @SerializedName("teamid")
    val teamid: String?,
    @SerializedName("total")
    val total: String?,
    @SerializedName("win")
    val win: String?
)