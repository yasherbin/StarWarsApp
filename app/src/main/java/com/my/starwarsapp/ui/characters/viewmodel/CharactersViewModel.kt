package com.my.starwarsapp.ui.characters.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.my.starwarsapp.data.api.Api
import com.my.starwarsapp.data.model.Character
import com.my.starwarsapp.utils.Resource
import kotlinx.coroutines.launch

class CharactersViewModel() : ViewModel() {

    private val _characters = MutableLiveData<Resource<List<Character>>>()

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