package com.my.starwarsapp.data.model

import com.squareup.moshi.Json

data class ListStarship (
    @Json(name = "results") var list: List<Starship>
)