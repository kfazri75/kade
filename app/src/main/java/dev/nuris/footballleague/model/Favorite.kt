package dev.nuris.footballleague.model

class Favorite(val id: Long?, val eventId: String?, val eventName: String?, val eventDate: String?, val homeId: String, val awayId: String, val homeTeamName: String,
               val awayTeamName: String, val homeScore: String, val awayScore: String) {

    companion object {
        const val TABLE_FAVORITE = "TABLE_FAVORITE"
        const val ID = "ID_"
        const val EVENT_ID = "EVENT_ID"
        const val EVENT_NAME = "EVENT_NAME"
        const val EVENT_DATE = "EVENT_DATE"
        const val HOME_ID = "HOME_ID"
        const val AWAY_ID = "AWAY_ID"
        const val HOME_TEAM_NAME = "HOME_TEAM_NAME"
        const val AWAY_TEAM_NAME = "AWAY_TEAM_NAME"
        const val HOME_SCORE = "HOME_SCORE"
        const val AWAY_SCORE = "AWAY_SCORE"
    }
}