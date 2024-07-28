package com.snehil.falconix.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.snehil.falconix.api.model.LaunchData
import com.snehil.falconix.api.model.LaunchRocketEntity
import com.snehil.falconix.api.model.Payload
import com.snehil.falconix.api.model.Rocket
import com.snehil.falconix.api.model.RocketPayloadEntity

@Database(
    entities = [Rocket::class, Payload::class, LaunchData::class, LaunchRocketEntity::class, RocketPayloadEntity::class],
    version = 1,
    exportSchema = true
)
@TypeConverters(StringListTypeConverter::class)
abstract class FalconIXDb : RoomDatabase() {
    abstract fun launchDao(): LaunchDao
}