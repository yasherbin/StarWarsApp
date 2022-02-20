package com.my.starwarsapp.ui.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.my.starwarsapp.ui.starships.viewmodel.StarshipsViewModel

class ViewModelFactory() : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(StarshipsViewModel::class.java)) {
            return StarshipsViewModel() as T
        }
        throw IllegalArgumentException("Unknown class name")
    }

}