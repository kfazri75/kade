package dev.nuris.footballleague.repository

import android.content.Context
import androidx.lifecycle.LiveData
import dev.nuris.footballleague.helper.lib.SingleLiveEvent
import dev.nuris.footballleague.model.Favorite
import dev.nuris.footballleague.source.FavoriteDataSource

class FavoriteRepositoryImpl(private val favoriteDataSource: FavoriteDataSource) : FavoriteRepository {

    init {
        favoriteDataSource.favoritesData.observeForever {
            try {
                _favoritesData.postValue(it)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    private val _favoritesData = SingleLiveEvent<List<Favorite>>()
    override val favoritesData: LiveData<List<Favorite>>
        get() = _favoritesData

    override fun getFavorites(context: Context) {
        favoriteDataSource.getFavorites(context)
    }
}