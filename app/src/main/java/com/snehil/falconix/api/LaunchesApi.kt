package com.snehil.falconix.api

import com.snehil.falconix.Constraints.LAUNCHES
import com.snehil.falconix.api.model.LaunchesApiResponse
import retrofit2.http.GET

interface LaunchesApi {
    @GET(LAUNCHES)
    suspend fun getLaunches() : LaunchesApiResponse
}