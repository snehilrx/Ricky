package com.snehil.falconix.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.snehil.falconix.api.model.LaunchData
import com.snehil.falconix.api.model.LaunchesApiResponse

@Database(
    entities = [LaunchesApiResponse::class],
    version = 1
)
abstract class FalconIXDb : RoomDatabase() {
    abstract fun launchDao(): LaunchDao
}