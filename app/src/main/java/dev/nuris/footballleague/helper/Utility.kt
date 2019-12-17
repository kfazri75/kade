package dev.nuris.footballleague.helper

object Utility {

    fun linkJersey(idTeam: String): String {
        return Constant.LINK_JERSEY + idTeam + "-Jersey.png"
    }

}