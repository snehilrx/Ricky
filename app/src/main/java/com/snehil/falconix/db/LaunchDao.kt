package com.snehil.falconix.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.snehil.falconix.api.model.LaunchData

@Dao
interface LaunchDao {
    @Query("SELECT * FROM launches")
    fun getAllLaunches(): List<LaunchData>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(vararg launches: LaunchData)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(launches: List<LaunchData>)

    @Delete
    fun delete(launch: LaunchData)
}
