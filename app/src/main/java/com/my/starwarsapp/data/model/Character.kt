package com.my.starwarsapp.data.model

import com.squareup.moshi.Json

data class Character(
    val name: String,
    val gender: String,
    @Json(name = "birth_year") val birthYear: String
)
