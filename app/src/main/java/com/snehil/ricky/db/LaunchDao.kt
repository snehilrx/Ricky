package com.snehil.ricky.db

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.snehil.ricky.api.model.LaunchData
import com.snehil.ricky.api.model.LaunchWithRocket
import com.snehil.ricky.api.model.Rocket

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

    @Query("select * from launches")
    @Transaction
    abstract fun getLaunches(): PagingSource<Int, LaunchWithRocket>

    @Query("select l.* from launches as l, rocket as r, launchrocketentity lr where missionName like '%' || :query || '%' or launchYear like '%' || :query || '%' or (lr.rocketId = r.rocketId and r.rocketName like '%' || :query || '%' and lr.id = l.id)")
    @Transaction
    abstract fun getLaunches(query: String): PagingSource<Int, LaunchWithRocket>

    @Query("select * from launches where id = :id")
    @Transaction
    abstract suspend fun getLaunch(id: String): LaunchWithRocket

    @Delete
    abstract fun delete(launch: LaunchData)
}
