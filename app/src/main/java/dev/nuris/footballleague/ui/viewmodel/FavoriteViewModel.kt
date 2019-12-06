package dev.nuris.footballleague.ui.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import dev.nuris.footballleague.repository.FavoriteRepository

class FavoriteViewModel(private val favoriteRepository: FavoriteRepository) : ViewModel() {

    val favoriteData by lazy {
        favoriteRepository.favoritesData
    }

    fun getFavorite(context: Context) {
        favoriteRepository.getFavorites(context)
    }
}