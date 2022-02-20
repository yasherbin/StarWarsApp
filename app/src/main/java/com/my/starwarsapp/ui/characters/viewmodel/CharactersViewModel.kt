package com.my.starwarsapp.ui.characters.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.my.starwarsapp.data.api.Api
import com.my.starwarsapp.data.model.Character
import com.my.starwarsapp.data.model.FavoriteCharacter
import com.my.starwarsapp.data.roomdatabase.AppDatabase
import com.my.starwarsapp.utils.Resource
import kotlinx.coroutines.launch

class CharactersViewModel(application: Application) : AndroidViewModel(application) {

    private val _characters = MutableLiveData<Resource<List<Character>>>()

    private val db: AppDatabase = AppDatabase.getDatabase(application)

    fun addFavoriteCharacter(favoriteCharacter: FavoriteCharacter) =
        viewModelScope.launch {
            db.favoriteCharacterDao().insertFavoriteCharacter(favoriteCharacter)
        }

    fun deleteFavoriteCharacter(favoriteCharacter: FavoriteCharacter) =
        viewModelScope.launch {
            db.favoriteCharacterDao().deleteFavoriteCharacter(favoriteCharacter.name)
        }

    fun isFavorite (name: String): LiveData<Int> {
        val res = MutableLiveData<Int>()
        viewModelScope.launch {
            val myres = db.favoriteCharacterDao().isFavorite(name)
            res.postValue(myres)
        }
        return res
    }

    fun getCharacters(): LiveData<Resource<List<Character>>> {
        viewModelScope.launch {
            try {
                _characters.postValue(Resource.success(Api.retrofitService.getCharacters().list))
            } catch (e: Exception) {
                e.printStackTrace()
                println(e.message)
                _characters.postValue(Resource.error("Something Went Wrong", null))
            }
        }
        return _characters
    }


}