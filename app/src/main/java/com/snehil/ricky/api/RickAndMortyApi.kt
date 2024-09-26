package com.snehil.ricky.api

import com.snehil.ricky.Constants
import com.snehil.ricky.api.model.Character
import com.snehil.ricky.api.model.Episode
import com.snehil.ricky.api.model.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface RickAndMortyApi {

    @GET(Constants.ApiUrls.CHARACTERS)
    suspend fun getCharacters(@Query("page") page: Int): Result<Response<Character>>

    @GET("${Constants.ApiUrls.EPISODES}/{id}")
    suspend fun getEpisode(@Path("id") id: Int): Result<Episode>


}