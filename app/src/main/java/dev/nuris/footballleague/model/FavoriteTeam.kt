package dev.nuris.footballleague.model

class FavoriteTeam(val id: Long?, val teamId: String?, val name: String?, val desc: String?, val badge: String) {

    companion object {
        const val TABLE_FAVORITE_TEAM = "TABLE_FAVORITE_TEAM"
        const val ID = "ID_"
        const val TEAM_ID = "TEAM_ID"
        const val TEAM_NAME = "TEAM_NAME"
        const val TEAM_DESC = "TEAM_DATE"
        const val BADGE = "BADGE"
    }
}