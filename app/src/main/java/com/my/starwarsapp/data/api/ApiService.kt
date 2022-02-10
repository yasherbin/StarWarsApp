package com.my.starwarsapp.data.api

import com.my.starwarsapp.data.model.ListCharacter
import com.my.starwarsapp.data.model.ListStarship
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

private const val BASE_URL="https://swapi.dev/api/"

private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

private val retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .baseUrl(BASE_URL)
    .build()

interface ApiService {
    @GET("people")
    suspend fun getCharacters(): ListCharacter
    @GET("starships/?")
    suspend fun getStarships(@Query("search") search:String): ListStarship
}

object Api {
    val retrofitService: ApiService by lazy { retrofit.create(ApiService::class.java) }
}