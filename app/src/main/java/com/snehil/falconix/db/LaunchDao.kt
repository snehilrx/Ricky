package com.snehil.falconix.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.snehil.falconix.api.model.LaunchData
import com.snehil.falconix.api.model.LaunchRocketEntity
import com.snehil.falconix.api.model.Payload
import com.snehil.falconix.api.model.Rocket
import com.snehil.falconix.api.model.RocketPayloadEntity

@Dao
abstract class LaunchDao {
    @Query("SELECT * FROM launches")
    abstract fun getAllLaunches(): List<LaunchData>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insertAllLaunchData(launches: List<LaunchData>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insertAllLaunchRocket(launches: List<LaunchRocketEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insertAllRocketPayload(launches: List<RocketPayloadEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insertAllRocket(launches: List<Rocket>)


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insertAllPayload(launches: List<Payload>)

    fun insert(launches: List<LaunchData>) {
        insertAllLaunchData(launches)
        val launchRocketEntities = launches.map {
            LaunchRocketEntity(it.rocket.rocketId, it.id)
        }
        val rocketPayloadEntities = launches.flatMap { launch ->
            launch.rocket.secondStage?.payloads?.map {
                RocketPayloadEntity(launch.rocket.rocketId, it.payloadId)
            } ?: emptyList()
        }
        insertAllRocket(launches.map { it.rocket })
        insertAllPayload(
            launches.flatMap { launch ->
                launch.rocket.secondStage?.payloads ?: emptyList()
            }
        )
        insertAllLaunchRocket(launchRocketEntities)
        insertAllRocketPayload(rocketPayloadEntities)
    }

    @Delete
    abstract fun delete(launch: LaunchData)
}
