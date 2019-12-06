package dev.nuris.footballleague.source

import android.content.Context
import androidx.lifecycle.LiveData
import dev.nuris.footballleague.model.Favorite

interface FavoriteDataSource {

    val favoritesData: LiveData<List<Favorite>>

    fun getFavorites(context: Context)
}