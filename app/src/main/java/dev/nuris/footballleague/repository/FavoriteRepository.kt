package dev.nuris.footballleague.repository

import android.content.Context
import androidx.lifecycle.LiveData
import dev.nuris.footballleague.model.Favorite

interface FavoriteRepository {

    val favoritesData: LiveData<List<Favorite>>

    fun getFavorites(context: Context)
}