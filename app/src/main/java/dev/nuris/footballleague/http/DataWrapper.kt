package dev.nuris.footballleague.http

class DataWrapper<T>(val apiData: T?, val apiException: String? = null) {
    fun checkNullOrError(): String? {
        if (apiException != null)
            return apiException

        if (apiData == null)
            return "Empty response data."

        return null
    }
}