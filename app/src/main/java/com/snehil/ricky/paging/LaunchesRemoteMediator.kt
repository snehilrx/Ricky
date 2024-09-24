package com.snehil.ricky.paging

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import com.snehil.ricky.Constants
import com.snehil.ricky.api.model.LaunchWithRocket
import com.snehil.ricky.db.rickyDb
import com.snehil.ricky.db.LaunchDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

@OptIn(ExperimentalPagingApi::class)
class LaunchesRemoteMediator @Inject constructor(
    val api: LaunchesApi,
    val db: rickyDb,
    val dao: LaunchDao
) : RemoteMediator<Int, LaunchWithRocket>() {
    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, LaunchWithRocket>
    ): MediatorResult {
        val endPaging = MediatorResult.Success(endOfPaginationReached = true)
        val continuePaging = MediatorResult.Success(endOfPaginationReached = false)
        val loadKey = when (loadType) {
            LoadType.REFRESH -> 0
            LoadType.PREPEND -> return endPaging
            LoadType.APPEND -> {
                state.lastItemOrNull()?.launchData?.pageNo?.plus(1) ?: 1
            }
        }
        val response = api.getLaunches(offset = loadKey * Constants.NETWORK_PAGING_SIZE)
        return if (response.isSuccess) {
            val apiResponse = response.getOrNull()!!
            withContext(Dispatchers.IO) {
                db.runInTransaction {
                    dao.insert(apiResponse.map {
                        it.copy(pageNo = loadKey)
                    })
                }
            }
            continuePaging
        } else {
            MediatorResult.Error(response.exceptionOrNull()!!)
        }
    }
}