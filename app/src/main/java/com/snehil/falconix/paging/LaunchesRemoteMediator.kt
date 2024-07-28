package com.snehil.falconix.paging

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import com.snehil.falconix.api.LaunchesApi
import com.snehil.falconix.api.model.LaunchData
import com.snehil.falconix.db.LaunchDao
import javax.inject.Inject

@OptIn(ExperimentalPagingApi::class)
class LaunchesRemoteMediator @Inject constructor(
    val api : LaunchesApi,
    val dao : LaunchDao
) : RemoteMediator<Int, LaunchData>() {
    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, LaunchData>
    ): MediatorResult {
        val endPaging = MediatorResult.Success(endOfPaginationReached = true)
        val continuePaging = MediatorResult.Success(endOfPaginationReached = false)
        val loadKey = when (loadType) {
            LoadType.REFRESH -> 1
            LoadType.PREPEND -> return endPaging
            LoadType.APPEND -> {
                state.lastItemOrNull()?.pageNo?.plus(1) ?: 1
            }
        }
        val response = api.getLaunches(offset = loadKey)
        return if(response.isSuccess) {
            val apiResponse = response.getOrNull()!!
            dao.insert(apiResponse.launches.map {
                it.copy(pageNo = loadKey)
            })
            continuePaging
        } else {
            MediatorResult.Error(response.exceptionOrNull()!!)
        }
    }
}