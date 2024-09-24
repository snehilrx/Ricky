package com.snehil.ricky.api

import com.snehil.ricky.Constants
import com.snehil.ricky.api.model.Character
import com.snehil.ricky.api.model.Episode
import com.snehil.ricky.api.model.Location
import com.snehil.ricky.api.model.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface RickyApi {

    @GET(Constants.ApiUrls.EPISODES)
    suspend fun getEpisodes(@Query("page") page: Int): Response<Episode>

    @GET(Constants.ApiUrls.LOCATIONS)
    suspend fun getLocations(@Query("page") page: Int): Response<Location>

    @GET(Constants.ApiUrls.CHARACTERS)
    suspend fun getCharacters(@Query("page") page: Int): Response<Character>

}