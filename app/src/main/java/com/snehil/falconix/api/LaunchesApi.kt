package com.snehil.falconix.api

import com.snehil.falconix.Constraints.LAUNCHES
import com.snehil.falconix.Constraints.NETWORK_PAGING_SIZE
import com.snehil.falconix.api.model.LaunchData
import retrofit2.http.GET
import retrofit2.http.Query

interface LaunchesApi {
    @GET(LAUNCHES)
    suspend fun getLaunches(
        @Query("limit") limit: Int = NETWORK_PAGING_SIZE,
        @Query("offset") offset: Int,
        @Query("id") id: Boolean = true
    ): Result<List<LaunchData>>
}