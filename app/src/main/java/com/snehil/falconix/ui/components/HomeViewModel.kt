package com.snehil.falconix.ui.components

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.snehil.falconix.Constants
import com.snehil.falconix.db.LaunchDao
import com.snehil.falconix.paging.LaunchesRemoteMediator
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val launchesRemoteMediator: LaunchesRemoteMediator,
    private val dao: LaunchDao
) : ViewModel() {

    @OptIn(ExperimentalPagingApi::class)
    val pager = Pager(
        config = PagingConfig(
            pageSize = Constants.NETWORK_PAGING_SIZE,
            enablePlaceholders = true,
        ),
        remoteMediator = launchesRemoteMediator
    ) {
        dao.getLaunches()
    }.flow.cachedIn(viewModelScope)

    @OptIn(ExperimentalPagingApi::class)
    fun getPager(query: String?) = if (query.isNullOrBlank()) {
        pager
    } else {
        Pager(
            config = PagingConfig(
                pageSize = Constants.NETWORK_PAGING_SIZE,
                enablePlaceholders = true,
            ),
            remoteMediator = launchesRemoteMediator
        ) {
            dao.getLaunches(query)
        }.flow.cachedIn(viewModelScope)
    }
}