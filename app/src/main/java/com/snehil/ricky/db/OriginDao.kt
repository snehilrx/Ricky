package com.snehil.ricky.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.snehil.ricky.db.model.OriginEntity

@Dao
interface OriginDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(origin: OriginEntity)

    @Query("SELECT * FROM OriginEntity")
    suspend fun getAllOrigins(): List<OriginEntity>
}