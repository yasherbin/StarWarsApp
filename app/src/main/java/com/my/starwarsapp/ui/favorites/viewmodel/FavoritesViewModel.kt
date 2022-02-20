package com.my.starwarsapp.ui.favorites.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.my.starwarsapp.data.model.FavoriteCharacter
import com.my.starwarsapp.data.roomdatabase.AppDatabase

class FavoritesViewModel (application: Application) : AndroidViewModel(application) {

    private val db: AppDatabase = AppDatabase.getDatabase(application)
    internal val allFavorites : LiveData<List<FavoriteCharacter>> = db.favoriteCharacterDao().getAllFavoriteCharacters()
}