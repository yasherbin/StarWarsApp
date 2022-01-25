package com.my.starwarsapp.ui.main.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.my.starwarsapp.data.api.CharacterApi
import com.my.starwarsapp.data.model.Character
import com.my.starwarsapp.utils.Resource
import kotlinx.coroutines.launch

class MainViewModel() : ViewModel() {

    private val _characters = MutableLiveData<Resource<List<Character>>>()

    fun getCharacters(): LiveData<Resource<List<Character>>> {
        viewModelScope.launch {
            try {
                _characters.postValue(Resource.success(CharacterApi.retrofitService.getCharacters().list))
            } catch (e: Exception) {
                e.printStackTrace()
                println(e.message)
                _characters.postValue(Resource.error("Something Went Wrong", null))
            }
        }
        return _characters
    }

}