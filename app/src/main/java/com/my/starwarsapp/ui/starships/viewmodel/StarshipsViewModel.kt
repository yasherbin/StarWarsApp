package com.my.starwarsapp.ui.starships.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.my.starwarsapp.data.api.Api
import com.my.starwarsapp.data.model.Starship
import com.my.starwarsapp.utils.Resource
import kotlinx.coroutines.launch

class StarshipsViewModel: ViewModel() {

    private val _starships = MutableLiveData<Resource<List<Starship>>>()

    fun getStarships(name: String): LiveData<Resource<List<Starship>>> {
        viewModelScope.launch {
            try {
                _starships.postValue(Resource.success(Api.retrofitService.getStarships(name).list))
            } catch (e: Exception) {
                e.printStackTrace()
                println(e.message)
                _starships.postValue(Resource.error("Something Went Wrong", null))
            }
        }
        return _starships
    }


}