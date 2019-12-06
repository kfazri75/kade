package dev.nuris.footballleague.helper

import android.content.Context
import android.view.View
import androidx.core.content.ContextCompat
import java.text.SimpleDateFormat
import java.util.*

fun View.setVisible() {
    visibility = View.VISIBLE
}

fun View.setGone() {
    visibility = View.GONE
}

fun Context.color(res: Int): Int = ContextCompat.getColor(this, res)

fun String?.checkNull(): String {
    return this ?: "-"
}

fun String.convertGTMFormat() : String {
    val df = SimpleDateFormat("yyyy-MM-dd hh:mm:ss", Locale.US)
    df.timeZone = TimeZone.getTimeZone("GMT")
    val date = df.parse(this)
    return df.format(date!!).convertDateTimeFormat()
}

fun String.convertDateTimeFormat() : String {
    val calendar = Calendar.getInstance()
    val dateFormat = SimpleDateFormat(Constant.FORMAT_DATETIME, Locale.US)

    calendar.apply {
        set(Calendar.YEAR, substring(Constant.DATE_ISO_YEAR_RANGE).toInt())
        set(Calendar.MONTH, substring(Constant.DATE_ISO_MONTH_RANGE).toInt() - 1)
        set(Calendar.DAY_OF_MONTH, substring(Constant.DATE_ISO_DATE_RANGE).toInt())
        set(Calendar.HOUR_OF_DAY, substring(Constant.DATE_ISO_HOUR_RANGE).toInt())
        set(Calendar.MINUTE, substring(Constant.DATE_ISO_MINUTE_RANGE).toInt())
        set(Calendar.SECOND, substring(Constant.DATE_ISO_SECOND_RANGE).toInt())
    }

    return dateFormat.format(calendar.time)
}