package com.my.starwarsapp.data.model

import com.squareup.moshi.Json

data class ListCharacter (
    @Json(name = "results") var list: List<Character>
)