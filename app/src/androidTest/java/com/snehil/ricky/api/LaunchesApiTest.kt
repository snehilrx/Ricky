package com.snehil.ricky.api

import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import javax.inject.Inject

@HiltAndroidTest
class LaunchesApiTest {

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @Inject
    lateinit var launchesApi: LaunchesApi

    @Before
    fun setup() {
        hiltRule.inject()
    }

    @Test
    fun testApiCall() {
        val result = runBlocking {
            launchesApi.getLaunches(offset = 1)
        }

        assert(result.isSuccess)
    }
}