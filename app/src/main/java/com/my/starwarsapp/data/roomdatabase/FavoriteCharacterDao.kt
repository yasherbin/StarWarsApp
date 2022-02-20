package com.my.starwarsapp.data.roomdatabase

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.my.starwarsapp.data.model.FavoriteCharacter

@Dao
interface FavoriteCharacterDao {
    @Insert
    suspend fun insertFavoriteCharacter(favoriteCharacter: FavoriteCharacter)

    @Query("DELETE FROM favoriteCharacters WHERE name = :name")
    suspend fun deleteFavoriteCharacter(name: String)

    @Query("SELECT EXISTS (SELECT 1 FROM favoriteCharacters WHERE name=:name)")
    suspend fun isFavorite(name: String): Int

    @Query("SELECT * FROM favoriteCharacters ORDER BY name")
    fun getAllFavoriteCharacters(): LiveData<List<FavoriteCharacter>>
}