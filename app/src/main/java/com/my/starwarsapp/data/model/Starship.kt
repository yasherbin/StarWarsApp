package com.my.starwarsapp.data.model

import com.squareup.moshi.Json

data class Starship(
    val name: String,
    val model: String,
    @Json(name = "starship_class") val starshipClass: String,
    @Json(name = "cargo_capacity") val capacity: String,
    val manufacturer: String
)
