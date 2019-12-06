package dev.nuris.footballleague.source

import android.content.Context
import androidx.lifecycle.LiveData
import dev.nuris.footballleague.helper.database
import dev.nuris.footballleague.helper.lib.SingleLiveEvent
import dev.nuris.footballleague.model.Favorite
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.jetbrains.anko.db.classParser
import org.jetbrains.anko.db.select

class FavoriteDataSourceImpl : FavoriteDataSource {

    private val _favoritesData = SingleLiveEvent<List<Favorite>>()
    override val favoritesData: LiveData<List<Favorite>>
        get() = _favoritesData

    override fun getFavorites(context: Context) {
        GlobalScope.launch {
            context.database.use {
                val result = select(Favorite.TABLE_FAVORITE)
                val favorites = result.parseList(classParser<Favorite>())
                _favoritesData.postValue(favorites)
            }
        }
    }
}