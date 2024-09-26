package com.snehil.ricky.paging

import android.os.Parcelable
import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import com.snehil.ricky.api.model.Response

@OptIn(ExperimentalPagingApi::class)
abstract class ResponseRemoteMediator<T : Parcelable, K : Any> : RemoteMediator<Int, K>() {

    private var lastKey = -1

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, K>
    ): MediatorResult {
        val endPaging = MediatorResult.Success(endOfPaginationReached = true)
        val continuePaging = MediatorResult.Success(endOfPaginationReached = false)
        val loadKey = when (loadType) {
            LoadType.REFRESH -> {
                lastKey = -1
                1
            }

            LoadType.PREPEND -> {
                lastKey = -1
                return endPaging
            }

            LoadType.APPEND -> {
                val newKey = key(state.lastItemOrNull())
                if (newKey != lastKey) {
                    lastKey = newKey
                    newKey
                } else {
                    return endPaging
                }
            }
        }
        val response = if (loadKey > 0) {
            getResponse(loadKey)
        } else {
            return endPaging
        }
        return if (response.isSuccess) {
            val apiResponse = response.getOrNull() ?: return MediatorResult.Error(Exception())
            saveResponse(apiResponse)
            if (apiResponse.info?.next == null) {
                endPaging
            } else {
                continuePaging
            }
        } else {
            MediatorResult.Error(response.exceptionOrNull() ?: Exception())
        }
    }

    abstract suspend fun getResponse(loadKey: Int): Result<Response<T>>

    abstract suspend fun saveResponse(response: Response<T>)

    abstract fun key(item: K?): Int
}