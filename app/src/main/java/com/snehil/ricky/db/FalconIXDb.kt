package com.snehil.ricky.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.snehil.ricky.api.model.LaunchData
import com.snehil.ricky.api.model.Rocket

@Database(
    entities = [Rocket::class, Payload::class, LaunchData::class, LaunchRocketEntity::class, RocketPayloadEntity::class],
    version = 1,
    exportSchema = true
)
@TypeConverters(StringListTypeConverter::class)
abstract class rickyDb : RoomDatabase() {
    abstract fun launchDao(): LaunchDao
}