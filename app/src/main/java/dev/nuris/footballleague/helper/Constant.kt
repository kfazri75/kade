package dev.nuris.footballleague.helper

object Constant {
    const val API_URL = "https://www.thesportsdb.com/api/v1/json/1/"
    const val LINK_JERSEY = "https://www.thesportsdb.com/images/media/team/jersey/2019-"
    const val FORMAT_DATETIME = "dd MMM yyyy HH:mm"

    val DATE_ISO_YEAR_RANGE = 0..3
    val DATE_ISO_MONTH_RANGE = 5..6
    val DATE_ISO_DATE_RANGE = 8..9
    val DATE_ISO_HOUR_RANGE = 11..12
    val DATE_ISO_MINUTE_RANGE = 14..15
    val DATE_ISO_SECOND_RANGE = 17..18

    const val LEAGUE_ID = "leagueId"
    const val EVENT_ID = "eventId"
}