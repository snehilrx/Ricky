package com.snehil.falconix.paging

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingConfig
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import com.snehil.falconix.api.model.LaunchData
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import junit.framework.Assert.assertFalse
import junit.framework.Assert.assertTrue
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import javax.inject.Inject

@HiltAndroidTest
class LaunchesRemoteMediatorTest {

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @Before
    fun setup() {
        hiltRule.inject()
    }

    @Inject
    lateinit var remoteMediator: LaunchesRemoteMediator


    @OptIn(ExperimentalPagingApi::class)
    @Test
    fun testRemoteMediator(){

        val pagingState = PagingState<Int, LaunchData>(
            listOf(),
            null,
            PagingConfig(10),
            10
        )
        val result = runBlocking {
            remoteMediator.load(LoadType.REFRESH, pagingState)
        }
        assertTrue(result is RemoteMediator.MediatorResult.Success )
        assertFalse((result as RemoteMediator.MediatorResult.Success).endOfPaginationReached)
    }
}