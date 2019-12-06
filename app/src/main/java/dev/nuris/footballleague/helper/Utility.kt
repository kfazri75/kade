package dev.nuris.footballleague.helper

import android.content.Context
import androidx.appcompat.app.AlertDialog
import dev.nuris.footballleague.R
import dev.nuris.footballleague.http.DataWrapper

object Utility {

    private fun showRequestErrorDialog(context: Context, message: String) {
        AlertDialog.Builder(context).apply {
            setTitle(context.getString(R.string.request_error))
            setMessage(message)
            setCancelable(true)
            setPositiveButton(context.getString(R.string.close), null)
        }.create().show()
    }

    fun checkApiResponse(context: Context, res: DataWrapper<*>?): Boolean {
        if (res == null) return false
        if (res.apiData == null && res.apiException == null) return false
        if (res.checkNullOrError() != null) {
            showRequestErrorDialog(context, res.checkNullOrError()!!)
            return false
        }
        return true
    }

    fun linkJersey(idTeam: String): String {
        return Constant.LINK_JERSEY + idTeam + "-Jersey.png"
    }

}